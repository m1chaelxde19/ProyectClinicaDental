package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.servicio.EnviarEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EnviarEmail enviarEmail;

    @GetMapping
    public String mostrarVistaEmail() {
        return "email";
    }

    @PostMapping("/enviarEmail")
    public String enviarEmail(@RequestParam("correo") String email, Model modelo) {
        try {
            System.out.println(email);
            enviarEmail.enviarEmail(email);
            modelo.addAttribute("mensaje", "El código de verificación ha sido enviado a tu correo");
            modelo.addAttribute("correo", email);
            return "restaContra";
        }catch (IllegalArgumentException e){
            modelo.addAttribute("correo", email);
            modelo.addAttribute("mensaje", e.getMessage());
        }
        return "email";
    }
}
