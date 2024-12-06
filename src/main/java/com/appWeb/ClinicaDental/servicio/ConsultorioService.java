package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.entidad.Consultorio;
import com.appWeb.ClinicaDental.entidad.EstadoConsultorio;
import com.appWeb.ClinicaDental.repositorio.ConsultorioReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultorioService {
    @Autowired
    ConsultorioReposity consultorioReposity;

    public List<Consultorio> listarConsultorio(){
        return consultorioReposity.findByEstado(EstadoConsultorio.Disponible);
    }

    public void GuardarConsultorio(Consultorio consultorio){
        consultorioReposity.saveConsultorio(consultorio.getNombre(),consultorio.getCapacidad(),consultorio.getTelefono(),
                consultorio.getDireccion(),consultorio.getEstado().name());
    }

    public Consultorio getConsultorio(Long id){
        return consultorioReposity.findById(id).get();
    }
}
