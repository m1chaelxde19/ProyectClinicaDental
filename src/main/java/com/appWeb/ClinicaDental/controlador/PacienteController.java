package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.ClinicaDentalApplication;
import com.appWeb.ClinicaDental.Recursos.Sesion;
import com.appWeb.ClinicaDental.entidad.Paciente;
import com.appWeb.ClinicaDental.servicio.PacienteService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private Sesion sesion;

    @GetMapping()
    public String paciente(Model model) {
        if (sesion.isLogged()) {
            List<Paciente> listPacienteCOnEdad = pacienteService.listar();
            model.addAttribute("idUser", sesion.getId_usuario());
            model.addAttribute("listPacienteCOnEdad", listPacienteCOnEdad);
            model.addAttribute("paciente", new Paciente());
            return "Paciente";
        }else {
            return "redirect:/api";
        }
    }

    @PostMapping("/pacienteValidation")
    public String formValidation(@Valid @ModelAttribute Paciente paciente, BindingResult result,RedirectAttributes model) {
        boolean fechaInvalida = validarFecha(paciente.getFechaNacimiento());
        System.out.println(fechaInvalida);
        if (result.hasErrors() || fechaInvalida) {

            List<String> errorMessages = new ArrayList<>();

            result.getFieldErrors().forEach(error -> {
               //logger.warn(error.getDefaultMessage());
                errorMessages.add(error.getDefaultMessage());
            });

            if (fechaInvalida){
                errorMessages.add("La fecha de nacimiento no puede ser mayor  o igual a la fecha actual");
            }

            model.addFlashAttribute("errorMessages", errorMessages);
        }else {
            pacienteService.savePaciente(paciente, sesion.getId_usuario());
            model.addFlashAttribute("mensajeConfirmacion", "Paciente guardado correctamente");
        }
        return "redirect:/paciente";
    }

    private boolean validarFecha(Date fecha) {
        if (fecha == null) {
            return true;
        }
        Calendar fechaNacimientoCal = Calendar.getInstance();
        fechaNacimientoCal.setTime(fecha);

        Calendar fechaActualCal = Calendar.getInstance();

        int anoNacimiento = fechaNacimientoCal.get(Calendar.YEAR);
        int anoActual = fechaActualCal.get(Calendar.YEAR);

        return anoNacimiento >= anoActual;
    }

}
