package com.appWeb.ClinicaDental.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/horario")
public class HorarioController {

    @GetMapping
    public String horario() {
        return "Horario";
    }
}
