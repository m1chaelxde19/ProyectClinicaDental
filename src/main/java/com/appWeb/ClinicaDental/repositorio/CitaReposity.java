package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.Recursos.CitaProyection;
import com.appWeb.ClinicaDental.entidad.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface CitaReposity extends JpaRepository<Cita, Long>{
    @Query(value = "CALL citasConOdontologos()",nativeQuery = true)
    List<CitaProyection> citasConOdontologos();

    @Query(value = "Call ListadoCitasReservadas()",nativeQuery = true)
    List<Object[]> ListadoCitasReservadas();

    @Transactional
    @Modifying
    @Procedure(name = "saveCita")
    void saveCita(
            @Param("p_fecha") Date p_fecha,@Param("p_hora") Time p_hora,
            @Param("p_estado") String p_estado,@Param("p_motivoCita") String p_motivo, @Param("p_comentarios") String p_comentarios,
            @Param("p_idPaciente") Long p_idPaciente,@Param("p_idHorario") Long p_idHorario,
            @Param("p_horaFin") Time p_horaFin
    );

    Cita findByIdCita(Long idCita);

    @Transactional
    @Modifying
    @Query(value = "Call actualizaCita(:p_idCita,:p_fecha,:p_hora,:p_estado,:p_motivo,:p_comentarios,:p_idPaciente,:p_idHorario,:p_horaFin)",nativeQuery = true)
    void actualizarCita(
            @Param("p_idCita") Long p_idCita,
            @Param("p_fecha") Date p_fecha,
            @Param("p_hora") Time p_hora,
            @Param("p_estado") String p_estado,
            @Param("p_motivo") String p_motivo,
            @Param("p_comentarios") String p_comentarios,
            @Param("p_idPaciente") Long p_idPaciente,
            @Param("p_idHorario") Long p_idHorario,
            @Param("p_horaFin") Time p_horaFin
    );

    @Transactional
    @Modifying
    @Query(value = "Call actualizarEstadoCita(:p_idCita,:p_estado)",nativeQuery = true)
    void actualizarEstadoCita(@Param("p_idCita") Long p_idCita,@Param("p_estado") String p_estado);

    Page<Cita> findByEstadoAndFecha(Status estado,Date fecha,Pageable pageable);
    Page<Cita> findByFecha(Date fecha,Pageable pageable);

    Page<Cita> findByEstado(Status estado, Pageable pageable);
}
