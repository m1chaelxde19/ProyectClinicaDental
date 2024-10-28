package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.entidad.Paciente;
import com.appWeb.ClinicaDental.repositorio.PacienteReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteReposity pacienteReposity;


    public List<Paciente> listar() {
        return pacienteReposity.findAll();
    }

    public void savePaciente(Paciente paciente, Long idUser) {
            pacienteReposity.savePaciente(paciente.getNombre(), paciente.getApellido(), paciente.getDni(), paciente.getFechaNacimiento(), paciente.getTelefono(),
                    paciente.getEmail(),paciente.getDireccion(),idUser);
    }


}
