package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.entidad.Odontologo;
import com.appWeb.ClinicaDental.repositorio.OdontologoReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService {
    @Autowired OdontologoReposity odontologoReposity;

    public List<Odontologo> findAll() {
        return odontologoReposity.findAll();
    }

    public void guardar(Odontologo odontologo,Long id) {
        odontologoReposity.saveOdontologo(odontologo.getDni(),odontologo.getNombre(),odontologo.getApellido(),odontologo.getEspecialidad(),odontologo.getTelefono(),id);
    }
}
