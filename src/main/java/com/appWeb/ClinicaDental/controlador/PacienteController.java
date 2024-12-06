package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.Recursos.PacienteDTO;
import com.appWeb.ClinicaDental.Recursos.Sesion;
import com.appWeb.ClinicaDental.entidad.Paciente;
import com.appWeb.ClinicaDental.servicio.PacienteService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.sql.Date;

@Controller
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private Sesion sesion;

    @GetMapping()
    public String paciente(Model model) {
        if (sesion.isLogged()) {
            List<Paciente> listPacienteCOnEdad = pacienteService.listarPacientesActivos();
            model.addAttribute("idUser", sesion.getId_usuario());
            model.addAttribute("listPacienteCOnEdad", listPacienteCOnEdad);
            model.addAttribute("nombre",sesion.getNombre());
            model.addAttribute("paciente", new Paciente());
            return "Paciente";
        }else {
            return "redirect:/api";
        }
    }


    @GetMapping("/vistaEditPaciente")
    public String viewPacienteEdit(@RequestParam("asdasaa") Long Idpaciente, Model model) {
        Paciente oldpaciente = pacienteService.buscar(Idpaciente);
        model.addAttribute("pacienteOld", oldpaciente);
        model.addAttribute("pacienteDTO", new PacienteDTO());
        return "EditPaciente";
    }

    @PostMapping("/editPaciente")
    public String editPaciente(
            @RequestParam("idPaciente") Long id, @RequestParam("nombre") String nombre, @RequestParam("lastName") String apellido,
            @RequestParam("fecha") Date fechaNacimiento, @RequestParam("dni") String Dni, @RequestParam("celular") String celular,
            @RequestParam("newDirection") String direccion, @RequestParam("correo") String email,
            RedirectAttributes model) {

        Map<String,String> errores = new HashMap<>();

        if (nombre.isEmpty() || apellido.isEmpty() || Dni.isEmpty() || celular.isEmpty() || direccion.isEmpty() || email.isEmpty()) {
            model.addFlashAttribute("errorMessages", "Todos los campos son obligatorios");
            return "redirect:/paciente/vistaEditPaciente?asdasaa="+id;
        }

        if (!pacienteService.verificationName(nombre)){errores.put("errorNombre","Solo se permite letras y espacios");}
        if (!pacienteService.verificationLastName(apellido)){errores.put("errorApellido","Solo se permite letras y espacios");}
        if (validarFecha(fechaNacimiento)){errores.put("errorFecha","La fecha de nacimiento no puede ser mayor  o igual a la fecha actual");}
        if (!pacienteService.verificationDni(Dni)){errores.put("errorDni","Solo se ingrese valones numéricos");}
        if (!pacienteService.verificationPhone(celular)){errores.put("errorCelular","El número de teléfono debe tener 9 dígitos y no debe contener letras");}
        if (!pacienteService.verificationEmail(email)){errores.put("errorEmail","Formato de correo no válido");}
        if (!pacienteService.verificationDirection(direccion)){errores.put("errorDireccion","Solo se permite letras y espacios");}

        if (!errores.isEmpty()){
            errores.forEach(model::addFlashAttribute);
            return "redirect:/paciente/vistaEditPaciente?asdasaa="+id;
        }

            try {
                pacienteService.updatePaciente(id, nombre, apellido, Dni, fechaNacimiento, celular, email, direccion,model);
            }catch (IllegalArgumentException e){
                model.addFlashAttribute("errorMessages", e.getMessage());
            }
        return "redirect:/paciente/vistaEditPaciente?asdasaa="+id;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("DL_id_paciente") Long id,RedirectAttributes msg){
        try {
            pacienteService.actualizarEstadoInactivo(id);
            msg.addFlashAttribute("mensajeConfirmacion","Paciente eliminado correctamente");
        }catch (Exception e){
            msg.addFlashAttribute("mensajeError","Hubo un error al tratar de eliminar este paciente");
        }
        return "redirect:/paciente";
    }
    @PostMapping("/pacienteValidation")
    public String formValidation(@Valid @ModelAttribute Paciente paciente, BindingResult result,RedirectAttributes model) {
        boolean fechaInvalida = validarFecha(paciente.getFechaNacimiento());
        System.out.println(fechaInvalida);
        if (result.hasErrors() || fechaInvalida) {

            List<String> errorMessages = new ArrayList<>();

            result.getFieldErrors().forEach(error -> {
                errorMessages.add(error.getDefaultMessage());
            });

            if (fechaInvalida){
                errorMessages.add("La fecha de nacimiento no puede ser mayor  o igual a la fecha actual");
            }

            model.addFlashAttribute("errorMessages", errorMessages);
        } {
          if (pacienteService.savePaciente(paciente, sesion.getId_usuario()) == 1) {
                model.addFlashAttribute("mensajeConfirmacion", "Paciente guardado correctamente");
            }else {
                model.addFlashAttribute("mensajeError", "El paciente ya existe");
            }
     }
        return "redirect:/paciente";
    }

    private boolean validarFecha(Date fecha) {
        if (fecha == null) {
            return true;
        }
        Calendar fechaNacimientoCal = Calendar.getInstance();
        fechaNacimientoCal.setTime(fecha);

        Calendar fechaActualCal = Calendar.getInstance();

        int anoNacimiento = fechaNacimientoCal.get(Calendar.YEAR);
        int anoActual = fechaActualCal.get(Calendar.YEAR);

        return anoNacimiento >= anoActual;
    }

}
