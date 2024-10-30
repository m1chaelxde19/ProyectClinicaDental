package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.Recursos.CitaProyection;
import com.appWeb.ClinicaDental.entidad.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaReposity extends JpaRepository<Cita, Long>{
    @Query(value = "CALL citasConOdontologos()",nativeQuery = true)
    List<CitaProyection> citasConOdontologos();

    @Query(value = "Call ListadoCitasReservadas()",nativeQuery = true)
    List<Object[]> ListadoCitasReservadas();
}
