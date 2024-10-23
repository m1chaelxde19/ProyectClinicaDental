package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.Recursos.CitaDTO;
import com.appWeb.ClinicaDental.entidad.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaReposity extends JpaRepository<Cita, Long>{
    @Query("SELECT ci.fecha AS fecha, ci.hora AS hora, ci.motivo_cita AS motivoCita, o.nombre AS nombreOdontologo, o.apellido As apellidoOdontologo,ci.comentarios AS comentarios " +
            "FROM cita ci " +
            "INNER JOIN ci.horario h " +
            "INNER JOIN h.odontologo o")
    List<CitaDTO> findCitasConOdontologo();
}
