package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.Recursos.Sesion;
import com.appWeb.ClinicaDental.entidad.Odontologo;
import com.appWeb.ClinicaDental.servicio.OdontologoService;
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
@RequestMapping("/doctor")
public class OdontologoController {
    @Autowired
    private OdontologoService dontologoService;

    @Autowired
    private Sesion sesion;

    @GetMapping
    public String vistaDoctor(Model model) {
        if (sesion.isLogged()) {
            List<Odontologo> odontologos = dontologoService.findAll();
            model.addAttribute("odontologos", odontologos);
            model.addAttribute("nombre", sesion.getNombre());
            model.addAttribute("odontologo",new Odontologo());
            return "Doctor";
        }else{
            return "redirect:/api";
        }
    }

    @PostMapping
    public String guardarDoctor(@Valid @ModelAttribute Odontologo odontologo, BindingResult result, RedirectAttributes model) {
        if (result.hasErrors()){
            List<String> errorMessages = new ArrayList<>();

            result.getFieldErrors().forEach(error -> {
                errorMessages.add(error.getDefaultMessage());
            });
            model.addFlashAttribute("errores", errorMessages);
            return "redirect:/doctor";
        }else {
            dontologoService.guardar(odontologo,sesion.getId_usuario());
            model.addFlashAttribute("mensajeConfirmacion", "Datos guardados exitosamente");
            return "redirect:/doctor";
        }
    }

}
