package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.Recursos.HorarioGet;
import com.appWeb.ClinicaDental.entidad.Consultorio;
import com.appWeb.ClinicaDental.entidad.DiaSemana;
import com.appWeb.ClinicaDental.entidad.Horario;
import com.appWeb.ClinicaDental.entidad.Odontologo;
import com.appWeb.ClinicaDental.servicio.ConsultorioService;
import com.appWeb.ClinicaDental.servicio.HorarioService;
import com.appWeb.ClinicaDental.servicio.OdontologoService;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/edit")
public class EditHorarioController {

    @Autowired
    HorarioService horarioService;

    @Autowired
    ConsultorioService consultorioService;

    @Autowired
    private OdontologoService odontologoService;

    @GetMapping("/visEdit")
    public String editHorario(@RequestParam Long idHorario, Model model) {
        Horario horario = horarioService.getHorario(idHorario);
        Odontologo odontologo = odontologoService.findById(horario.getOdontologo().getId_Odontologo());
        List<Odontologo> listOdontologo = odontologoService.findAll();
        Consultorio consultorio = consultorioService.getConsultorio(horario.getConsultorio().getId_consultorio());
        List<Consultorio> listConsultorio = consultorioService.listarConsultorio();

        model.addAttribute("consultorioSeleccionado", consultorio);
        model.addAttribute("horarioSelecionado", horario);
        model.addAttribute("odontologoSeleccionado", odontologo);
        model.addAttribute("listConsultorio", listConsultorio);
        model.addAttribute("listOdontologos",listOdontologo);
        model.addAttribute("diasSemana", DiaSemana.values());
        model.addAttribute("horarioGet",new HorarioGet());
        model.addAttribute("idHorario", idHorario);
        return "editarHorario";
    }

    @PostMapping()
    public String editHorario(@Param("idHorario")Long idHorario, @Param("diaSemana") DiaSemana diaSemana,@Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin,@Param("idOdontologo") Long idOdontologo,@Param("idConsultorio")Long idConsultorio,RedirectAttributes msg) {
        horarioService.actualizaHorario(idHorario,diaSemana,horaInicio,horaFin,idOdontologo,idConsultorio,msg);
        return "redirect:/horario";
    }
}
