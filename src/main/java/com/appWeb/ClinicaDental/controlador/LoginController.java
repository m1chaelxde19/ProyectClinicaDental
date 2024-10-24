package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.entidad.Usuario;
import com.appWeb.ClinicaDental.servicio.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Controller
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping
    public String login() {
        return "index";
    }

    @PostMapping("/loginValidation")
    public String validarLogin(@RequestParam("correo") String correo, @RequestParam("clave") String clave, Model model, HttpSession session) {
        try {
            usuarioService.validarLogin(correo, clave,session);
            return "redirect:/Dashboard";
        }catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
}
