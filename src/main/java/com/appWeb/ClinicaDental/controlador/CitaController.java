package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.Recursos.CitaDTO;
import com.appWeb.ClinicaDental.Recursos.OdontologoDTO;
import com.appWeb.ClinicaDental.Recursos.Sesion;
import com.appWeb.ClinicaDental.entidad.*;
import com.appWeb.ClinicaDental.servicio.CitaService;
import com.appWeb.ClinicaDental.servicio.OdontologoService;
import com.appWeb.ClinicaDental.servicio.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cita")
public class CitaController {
    @Autowired
    private CitaService citaService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private Sesion sesion;

    @GetMapping
    public String citas(@RequestParam(defaultValue = "0") int page,Model model) {
        if (sesion.isLogged()) {
            int pageSize = 5;
            Page<Cita> paginasCitas = citaService.citasPage(page,pageSize);
            List<OdontologoDTO> odontologos = odontologoService.listarOdontologosActivos();
            model.addAttribute("motivoCitas", MotivoCita.values());
            model.addAttribute("estados",Status.values());
            model.addAttribute("citas", paginasCitas.getContent());
            model.addAttribute("currentPage",page);
            model.addAttribute("totalPages",paginasCitas.getTotalPages());
            model.addAttribute("odontologos", odontologos);
            model.addAttribute("nombre",sesion.getNombre());
            model.addAttribute("citaDTO", new Cita());
            model.addAttribute("comentar","Sin comentarios");
            return "Cita";
        }else {
            return "redirect:/api";
        }
    }

    @GetMapping("/EditCita/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerCita(@PathVariable Long id) {
        Cita cita = citaService.getCita(id);
        Map<String,String> datos = new HashMap<>();
        if  (cita != null) {
            datos.put("fecha",cita.getFecha().toString());
            datos.put("hora",cita.getHora().toString());
            datos.put("estado",cita.getEstado().toString());
            datos.put("motivo",cita.getMotivo_cita().toString());
            datos.put("comentarios",cita.getComentarios());
            datos.put("idPaciente",cita.getPaciente().getIdPaciente().toString());
            datos.put("idHorario",cita.getHorario().getId_horario().toString());
            datos.put("idCita",cita.getIdCita().toString());
            datos.put("idOdontologo",cita.getHorario().getOdontologo().getId_Odontologo().toString());
            datos.put("nombrePaciente",cita.getPaciente().getNombre());
            datos.put("horaFin",cita.getHoraFin().toString());
            return ResponseEntity.ok(Map.of("data",datos));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/saveCita")
    public String saveCita(
            @RequestParam("patientId") Long idPaciente, @RequestParam("fecha") Date fecha, @RequestParam("hora") String hora,
            @RequestParam("motivoCita") MotivoCita motivoCita, @RequestParam("estado") Status estadoCita,
            @RequestParam(value = "comentarios",required = false) String comentarios, @RequestParam("horaFin") String horaAproximada,
            @RequestParam("idHorario")Long IdHorario,RedirectAttributes redirectAttributes){
        try {
            Time horaConvertida = Time.valueOf(LocalTime.parse(hora));
            Time horaFin = Time.valueOf(LocalTime.parse(horaAproximada));
            citaService.agregarCita(fecha,horaConvertida,motivoCita,estadoCita,idPaciente,IdHorario,comentarios,horaFin);
            redirectAttributes.addFlashAttribute("confirm","Se guardó correctamente la cita");
        }catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorCita", e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "redirect:/cita";
    }
    
    @PostMapping("/delete")
    public String deleteCita(@RequestParam("DL_id_cita") Long idCita, RedirectAttributes msg){
        try{
            citaService.eliminarCita(idCita);
            msg.addFlashAttribute("confirm","Cita atendida");
        }catch (Exception e){
            msg.addFlashAttribute("errorCita","Error al eliminar cita");
        }
        return "redirect:/cita";
    }

    @PostMapping("/citaEdit")
    public String updateCita(@RequestParam("id_cita")Long idCita,@RequestParam("fecha") Date fecha,@RequestParam("hora")String hora,
                             @RequestParam("motivoCita")MotivoCita motivoCita,@RequestParam("estado")Status estadoCita,
                             @RequestParam("pacienteEdit")Long idPaciente,@RequestParam("newHorusEnd")String horaFin,
                             @RequestParam(value ="comentarios",required = false)String comentarios, @RequestParam("horarioId")Long idHorario,
                             RedirectAttributes msg){
        Time horaConvertida = Time.valueOf(LocalTime.parse(hora));
        Time horaConvertida2 = Time.valueOf(LocalTime.parse(horaFin));
        try {
            citaService.saveCita(idCita,idPaciente,horaConvertida,fecha,estadoCita.toString(),comentarios,motivoCita.toString(),
                    idHorario,horaConvertida2,msg);
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return "redirect:/cita";
    }

    @GetMapping("/citas")
    @ResponseBody
    public ResponseEntity<?> listarCitas(@RequestParam(required = false) Status estado,
                                         @RequestParam(required = false) Date fecha,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {
            Page<CitaDTO> citas;
            if (estado != null && fecha != null) {
                citas = citaService.listarCitasPorEstadoYFecha(estado,fecha,page,size);
            }else if (estado != null) {
                citas = citaService.listarCitasPorEstado(estado,page,size);
            }else if (fecha != null) {
                citas = citaService.listarCitasPorFecha(fecha,page,size);
            }else {
                return ResponseEntity.badRequest().body(Map.of("error","Debe seleccionar un estado o una fecha"));
            }
            return ResponseEntity.ok(Map.of(
                    "datos", citas.getContent(),
                    "currentPage",citas.getNumber(),
                    "totalPages",citas.getTotalPages()
            ));
    }
}
