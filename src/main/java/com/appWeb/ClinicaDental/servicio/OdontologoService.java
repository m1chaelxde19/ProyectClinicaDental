package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.Recursos.OdontoProyection;
import com.appWeb.ClinicaDental.Recursos.OdontologoDTO;
import com.appWeb.ClinicaDental.entidad.Odontologo;
import com.appWeb.ClinicaDental.repositorio.OdontologoReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Odontologo findById(Long id) {
        return odontologoReposity.findById(id).get();
    }

    public List<OdontologoDTO> listarOdontologosActivos() {
        List<OdontoProyection> odontoProyections = odontologoReposity.odontologosDisponibles();
        return odontoProyections.stream().map(proyection -> new OdontologoDTO(
                proyection.getId_Odontologo(), proyection.getDni(), proyection.getNombre(), proyection.getApellido(),
                proyection.getEspecialidad(), proyection.getTelefono(), proyection.getHora_Inicio(), proyection.getHora_Fin()
        )).collect(java.util.stream.Collectors.toList());
    }
}
