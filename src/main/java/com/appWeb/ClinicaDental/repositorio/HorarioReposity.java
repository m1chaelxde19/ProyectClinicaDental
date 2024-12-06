package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;

@Repository
public interface HorarioReposity extends JpaRepository<Horario,Long> {
    @Query(value = "Call horarioDisponible(:odontologoId,:diaSemana,:hora,:horafin,:fecha)", nativeQuery = true)
    int findHorarioByOdontologoAndHora(@Param("odontologoId") Long odontologoId,
                                       @Param("diaSemana") String diaSemana,
                                       @Param("hora") Time hora, @Param("horafin") Time horafin,@Param("fecha")Date fecha);

    @Modifying
    @Transactional
    @Procedure(name="guardarHorario")
    boolean guardarHorario (
            @Param("p_odontologo") Long id_odontologo,
            @Param("p_consultorio") Long id_consultorio,
            @Param("p_dia") String dia,
            @Param("p_horaInicio") Time HoraInicio,
            @Param("p_horaFin") Time HoraFin);

    @Modifying
    @Transactional
    @Procedure(name = "actualizarHorario")
    void actualizarHorario(@Param("p_idHorario")Long p_idHorario,
                       @Param("p_idOdontologo") Long p_idOdontologo,
                       @Param("p_idConsultorio") Long p_idConsultorio,
                       @Param("p_dia") String p_dia,
                       @Param("p_horaInicio") Time p_horaInicio,
                       @Param("p_horaFin") Time p_horaFin);

    @Query(value = "Call getHorario(:idOdontologo)", nativeQuery = true)
    Horario getHorario(@Param("idOdontologo") Long idOdontologo);

    @Query(value = "Call verificarCitaActualizar(:p_dia,:p_idOdontologo,:p_hora,:e_horaFin,:p_cita_id,:p_paciente_id,:p_fecha,:p_idHorario)",nativeQuery = true)
    int verificarHorarioCita(
            @Param("p_idOdontologo") Long p_idOdontologo,
            @Param("p_dia") String p_dia,
            @Param("p_hora") Time p_hora,
            @Param("e_horaFin") Time e_horaFin,
            @Param("p_cita_id") Long p_cita_id,
            @Param("p_paciente_id") Long p_paciente_id,
            @Param("p_fecha") Date p_fecha,
            @Param("p_idHorario")Long p_idHorario
    );

}
