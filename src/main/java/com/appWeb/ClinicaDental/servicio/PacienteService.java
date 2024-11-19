package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.entidad.Paciente;
import com.appWeb.ClinicaDental.repositorio.PacienteReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Preconditions;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PacienteService {
    private final Pattern NOMBRE_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");

    @Autowired
    private PacienteReposity pacienteReposity;


    public List<Paciente> listar() {
        return pacienteReposity.findAll();
    }

    public void savePaciente(Paciente paciente, Long idUser) {
            pacienteReposity.savePaciente(paciente.getNombre(), paciente.getApellido(), paciente.getDni(), paciente.getFechaNacimiento(), paciente.getTelefono(),
                    paciente.getEmail(),paciente.getDireccion(),idUser);
    }

    public void updatePaciente(Long id, String nombre, String apellido, String Dni, Date fechaNacimiento, String celular,
                               String email, String direccion, RedirectAttributes model) {

        try {
            pacienteReposity.updatePaciente(id, nombre, apellido, Dni, fechaNacimiento, celular, email, direccion);
            model.addFlashAttribute("mensajeConfirmacion", "Paciente actualizado correctamente");
        } catch (Exception e) {
            model.addFlashAttribute("mensajeError", "Paciente actualizado correctamente");
        }
    }

    public boolean verificationName(String nombre){
        return NOMBRE_PATTERN.matcher(nombre).matches();
    }

    public boolean verificationLastName(String apellido){
        return NOMBRE_PATTERN.matcher(apellido).matches();
    }

    public boolean verificationDni(String dni){
        return dni.matches("[0-9]{8}");
    }

    public boolean verificationPhone(String celular){
        return celular.matches("[0-9]{9}");
    }

    public boolean verificationEmail(String email){
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public boolean verificationDirection(String direccion){
        return direccion.matches("^[a-zA-Z0-9\\s.,!?()-]+$");
    }

    public Paciente buscar(Long id) {
        return pacienteReposity.findById(id).get();
    }

    public void eliminarPaciente(Long id){
        pacienteReposity.deleteById(id);
    }

}
