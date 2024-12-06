package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.Recursos.HorarioGet;
import com.appWeb.ClinicaDental.Recursos.Sesion;
import com.appWeb.ClinicaDental.entidad.Consultorio;
import com.appWeb.ClinicaDental.entidad.DiaSemana;
import com.appWeb.ClinicaDental.entidad.Horario;
import com.appWeb.ClinicaDental.entidad.Odontologo;
import com.appWeb.ClinicaDental.servicio.ConsultorioService;
import com.appWeb.ClinicaDental.servicio.HorarioService;
import com.appWeb.ClinicaDental.servicio.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/horario")
public class HorarioController {
    @Autowired
    private Sesion sesion;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private ConsultorioService consultorioService;

    @GetMapping
    public String horario(Model model) {
        if (sesion.isLogged()){
            List<Horario> listHorario = horarioService.listar();
            List<Odontologo> listOdontologo = odontologoService.findAll();
            List<Consultorio> listConsultorio = consultorioService.listarConsultorio();
            model.addAttribute("horarios",listHorario);
            model.addAttribute("Objthorario",new HorarioGet());
            model.addAttribute("diasSemana", DiaSemana.values());
            model.addAttribute("doctores",listOdontologo);
            model.addAttribute("consultorios",listConsultorio);
            return "Horario";
        }else {
            return "redirect:/api";
        }
    }

    @PostMapping("/insert")
    public String addHorario(@ModelAttribute HorarioGet horario, RedirectAttributes msg) {
        if (horarioService.addHorario(horario)){
            msg.addFlashAttribute("msgConfirmacion", "Horario guardado exitosamente");
        }else {
            msg.addFlashAttribute("msg", "El odontólogo o el consultorio ya están ocupados en ese horario");
        }
        return "redirect:/horario";
    }

    @PostMapping("/eliminar")
    public String eliminarHorario(@RequestParam("idHorario") Long id, RedirectAttributes msg) {
        try {
            horarioService.eliminarHorario(id);
            msg.addFlashAttribute("msgConfirmacion", "Horario eliminado exitosamente");
        }catch (Exception e){
            msg.addFlashAttribute("msg","Error al eliminar horario");
            System.out.println("Error al eliminar horario: "+e.getMessage());
        }
        return "redirect:/horario";
    }
}
