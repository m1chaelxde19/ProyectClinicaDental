package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.entidad.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Dashboard {
    @GetMapping("/Dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuario userLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (userLogueado != null) {
            model.addAttribute("usuario", userLogueado);
            return "Dashboard";
        }else{
            return "redirect:/index";
        }

    }
}
