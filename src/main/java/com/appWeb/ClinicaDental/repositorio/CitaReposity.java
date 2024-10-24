package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.Recursos.CitaDTO;
import com.appWeb.ClinicaDental.entidad.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaReposity extends JpaRepository<Cita, Long>{
    @Query(value = "CALL citasConOdontologos()",nativeQuery = true)
    List<Object[]> citasConOdontologos();
}
