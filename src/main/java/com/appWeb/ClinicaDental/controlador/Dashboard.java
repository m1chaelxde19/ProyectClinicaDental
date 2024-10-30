package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.Recursos.Sesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Dashboard {
    @Autowired
    private Sesion sesion;

    @GetMapping("/Dashboard")
    public String dashboard(Model model) {
        if (sesion.isLogged()) {
            model.addAttribute("nombre", sesion.getNombre());
            return "Dashboard";
        }else{
            return "redirect:/api";
        }

    }
}
