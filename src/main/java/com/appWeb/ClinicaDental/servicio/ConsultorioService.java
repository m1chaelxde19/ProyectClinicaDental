package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.entidad.Consultorio;
import com.appWeb.ClinicaDental.repositorio.ConsultorioReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultorioService {
    @Autowired
    ConsultorioReposity consultorioReposity;

    public List<Consultorio> listarConsultorio(){
        return consultorioReposity.findAll();
    }

    public void GuardarConsultorio(Consultorio consultorio){
        consultorioReposity.saveConsultorio(consultorio.getNombre(),consultorio.getCapacidad(),consultorio.getTelefono(),
                consultorio.getDireccion(),consultorio.getEstado().name());
    }
}
