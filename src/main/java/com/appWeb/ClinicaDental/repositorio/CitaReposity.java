package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.Recursos.CitaProyection;
import com.appWeb.ClinicaDental.entidad.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface CitaReposity extends JpaRepository<Cita, Long>{
    @Query(value = "CALL citasConOdontologos()",nativeQuery = true)
    List<CitaProyection> citasConOdontologos();

    @Query(value = "Call ListadoCitasReservadas()",nativeQuery = true)
    List<Object[]> ListadoCitasReservadas();

    @Procedure(name = "actualizaCita")
    void actualizaCita(
            @Param("p_idCita") Long p_idCita,@Param("p_fecha") Date p_fecha,@Param("p_hora") Time p_hora,
            @Param("p_estado") String p_estado,@Param("p_motivo") String p_motivo, @Param("p_comentarios") String p_comentarios,
            @Param("p_idPaciente") Long p_idPaciente,@Param("p_idHorario") Long p_idHorario
    );
}
