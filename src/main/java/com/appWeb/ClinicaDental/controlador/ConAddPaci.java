package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.Recursos.OdontologoDTO;
import com.appWeb.ClinicaDental.Recursos.PacienteDTO;
import com.appWeb.ClinicaDental.entidad.MotivoCita;
import com.appWeb.ClinicaDental.entidad.Status;
import com.appWeb.ClinicaDental.servicio.OdontologoService;
import com.appWeb.ClinicaDental.servicio.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/pacienteApi")
public class ConAddPaci {

    @Autowired
    PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @GetMapping("/agrePaciente")
    public String AddPaciente(Model model) {
        List<OdontologoDTO> odontologos = odontologoService.listarOdontologosActivos();
        model.addAttribute("motivoCitas", MotivoCita.values());
        model.addAttribute("estados", Status.values());
        model.addAttribute("odontologos", odontologos);
        return "addPaciente";
    }

    @PostMapping("/postPaciente")
    public String buscarPaciente(@RequestParam("palabra") String palabra, Model redirectAttributes) {
        try {
            List<PacienteDTO> paciente = pacienteService.buscarPaciente(palabra);
            System.out.println(paciente.isEmpty());

            if (pacienteService.verificationDni(palabra) || pacienteService.verificationName(palabra) || pacienteService.verificationLastName(palabra)) {
                if (!paciente.isEmpty()) {
                    redirectAttributes.addAttribute("pacientes", paciente);
                } else {
                    redirectAttributes.addAttribute("mensaje", "Paciente no encontrado");
                }
            }else {
                redirectAttributes.addAttribute("msgerror", "DNI invalido");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "addPaciente";
    }
}
