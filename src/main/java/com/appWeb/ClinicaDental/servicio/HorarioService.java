package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.entidad.Horario;
import com.appWeb.ClinicaDental.repositorio.HorarioReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HorarioService {
    @Autowired
    private HorarioReposity horarioReposity;
    public List<Horario> listar() {
        return horarioReposity.findAll();
    }
}