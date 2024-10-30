package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.Recursos.Sesion;
import com.appWeb.ClinicaDental.entidad.Consultorio;
import com.appWeb.ClinicaDental.entidad.EstadoConsultorio;
import com.appWeb.ClinicaDental.servicio.ConsultorioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/consultorio")
public class ConsultorioController {
    @Autowired
    private ConsultorioService consultorioService;

    @Autowired
    private Sesion sesion;

    @GetMapping
    public String vistConsultorio(Model model) {
        if (sesion.isLogged()) {
            List<Consultorio> consultorios = consultorioService.listarConsultorio();
            model.addAttribute("consultorios", consultorios);
            model.addAttribute("consultorio",new Consultorio());
            model.addAttribute("estadosConsultorio", EstadoConsultorio.values());
            return "Consultorio";
        }else {
            return "redirect:/api";
        }
    }

    @PostMapping
    public String GuardarConsultorio(@Valid @ModelAttribute Consultorio consultorio, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();

            result.getFieldErrors().forEach(error -> {
                errorMessages.add(error.getDefaultMessage());
            });
            redirectAttributes.addFlashAttribute("errores", errorMessages);
            return "redirect:/consultorio";
        }else {
            consultorioService.GuardarConsultorio(consultorio);
            redirectAttributes.addFlashAttribute("mensajeConfirmacion", "Datos guardados exitosamente");
            return "redirect:/consultorio";
        }
    }
}
