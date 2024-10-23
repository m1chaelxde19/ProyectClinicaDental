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

    public List<Paciente> findAll() {
        return pacienteReposity.findAll();
    }
}
