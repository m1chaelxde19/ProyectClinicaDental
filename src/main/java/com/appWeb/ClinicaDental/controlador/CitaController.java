package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.Recursos.CitaDTO;
import com.appWeb.ClinicaDental.entidad.*;
import com.appWeb.ClinicaDental.servicio.CitaService;
import com.appWeb.ClinicaDental.servicio.OdontologoService;
import com.appWeb.ClinicaDental.servicio.PacienteService;
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

    @GetMapping
    public String citas(Model model){
        List<CitaDTO> citas = citaService.obtenerCitasConOdontologo();
        List<Odontologo> odontologos = odontologoService.findAll();
        List<Paciente> pacientes = pacienteService.findAll();
        model.addAttribute("motivoCitas", MotivoCita.values());
        model.addAttribute("estados",Status.values());
        model.addAttribute("citas", citas);
        model.addAttribute("odontologos", odontologos);
        model.addAttribute("pacientes", pacientes);
        return "Cita";
    }

    @PostMapping("/saveCita")
    public String saveCita(
            @RequestParam("fecha") Date fecha, @RequestParam("hora") String hora, @RequestParam("motivoCita") MotivoCita motivoCita,
            @RequestParam("estado") Status estadoCita, @RequestParam("paciente") Long idPaciente, @RequestParam("odontologId") Long Idodont,
            @RequestParam(value = "comentarios",required = false) String comentarios, RedirectAttributes redirectAttributes
    ){
        try {
            Time horaConvertida = Time.valueOf(LocalTime.parse(hora));
            System.out.println("odontologo: "+Idodont);
            System.out.println("hora: " + hora);
            System.out.println("fecha: " +fecha);
            citaService.agregarCita(fecha, horaConvertida, motivoCita, estadoCita, idPaciente, Idodont, comentarios);
            redirectAttributes.addFlashAttribute("confirm","Se guardó correctamente la cita");
        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("errorCita",e.getMessage());
        }
        return "redirect:/cita";
    }
}
