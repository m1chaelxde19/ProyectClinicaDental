package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.Recursos.Sesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/horario")
public class HorarioController {
    @Autowired
    private Sesion sesion;

    @GetMapping
    public String horario() {

        return "Horario";
    }
}
