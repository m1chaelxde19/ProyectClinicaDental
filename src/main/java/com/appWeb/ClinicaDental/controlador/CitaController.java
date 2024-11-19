package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.Recursos.CitaDTO;
import com.appWeb.ClinicaDental.Recursos.Sesion;
import com.appWeb.ClinicaDental.entidad.*;
import com.appWeb.ClinicaDental.servicio.CitaService;
import com.appWeb.ClinicaDental.servicio.OdontologoService;
import com.appWeb.ClinicaDental.servicio.PacienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

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
    public String citas(Model model) {
        if (sesion.isLogged()) {
        //   log.info("INFO: Mostrando la vista de cita...");
            List<CitaDTO> citas = citaService.getCitasOdontologicas();
            List<Odontologo> odontologos = odontologoService.findAll();
            List<Paciente> pacientes = pacienteService.listar();
            model.addAttribute("motivoCitas", MotivoCita.values());
            model.addAttribute("estados",Status.values());
            model.addAttribute("citas", citas);
            model.addAttribute("odontologos", odontologos);
            model.addAttribute("nombre",sesion.getNombre());
            model.addAttribute("pacientes", pacientes);
            return "Cita";
        }else {
            return "redirect:/api";
        }
    }


    @GetMapping("/vistaEditCita")
    public String viewcitasEdit(@RequestParam("idCita")Long idCita, Model model) {
        Cita cita = citaService.getUsuario(idCita);
        Paciente paciente = pacienteService.buscar(cita.getPaciente().getId_paciente());
        Odontologo odontologo = odontologoService.findById(cita.getHorario().getOdontologo().getId_Odontologo());
        List<Odontologo> odontologos = odontologoService.findAll();
        List<Paciente> pacientes = pacienteService.listar();
        model.addAttribute("estados",Status.values());
        model.addAttribute("pacienteSeleccionado", paciente);
        model.addAttribute("odontologoSeleccionado", odontologo);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("odontologos", odontologos);
        model.addAttribute("motivoCitas", MotivoCita.values());
        model.addAttribute("idCita", idCita);
        model.addAttribute("cita", cita);
        return "editarCita";
    }


    @PostMapping("/saveCita")
    public String saveCita(
            @RequestParam("fecha") Date fecha, @RequestParam("hora") String hora, @RequestParam("motivoCita") MotivoCita motivoCita,
            @RequestParam("estado") Status estadoCita, @RequestParam("paciente") Long idPaciente, @RequestParam("odontologId") Long Idodont,
            @RequestParam(value = "comentarios",required = false) String comentarios, RedirectAttributes redirectAttributes
    ){
        try {
            Time horaConvertida = Time.valueOf(LocalTime.parse(hora));
            citaService.agregarCita(fecha, horaConvertida, motivoCita, estadoCita, idPaciente, Idodont, comentarios);
            redirectAttributes.addFlashAttribute("confirm","Se guardó correctamente la cita");
        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("errorCita",e.getMessage());
        }
        return "redirect:/cita";
    }
    
    @PostMapping("/delete")
    public String deleteCita(@RequestParam("DL_id_cita") Long idCita, RedirectAttributes msg){
        try{
            citaService.eliminarCita(idCita);
            msg.addFlashAttribute("confirm","Cita eliminada");
        }catch (Exception e){
            msg.addFlashAttribute("errorCita","Error al eliminar cita");
        }
        return "redirect:/cita";
    }

    @PostMapping("/citaEdit")
    public String updateCita(@RequestParam("id_cita")Long idCita,@RequestParam("fecha") Date fecha,@RequestParam("hora")String hora,
                             @RequestParam("motivoCita")MotivoCita motivoCita,@RequestParam("estado")Status estadoCita,
                             @RequestParam("Id_paciente")Long idPaciente,@RequestParam("odontologId")Long idOdontologo,
                             @RequestParam(value ="comentarios",required = false)String comentarios,RedirectAttributes msg){

        Time horaConvertida = Time.valueOf(LocalTime.parse(hora));
        citaService.saveCita(idCita,idPaciente,idOdontologo,horaConvertida,fecha,estadoCita.toString(),comentarios,motivoCita.toString(),msg);
        return "redirect:/cita";
    }
}
