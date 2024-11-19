package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Optional;

@Repository
public interface HorarioReposity extends JpaRepository<Horario,Long> {
    @Query(value = "Call horarioDisponible(:odontologoId,:diaSemana,:hora)", nativeQuery = true)
    Optional<Horario> findHorarioByOdontologoAndHora(@Param("odontologoId") Long odontologoId,
                                                     @Param("diaSemana") String diaSemana,
                                                     @Param("hora") Time hora);


    @Procedure(name="guardarHorario")
    boolean guardarHorario (
            @Param("p_odontologo") Long id_odontologo,
            @Param("p_consultorio") Long id_consultorio,
            @Param("p_dia") String dia,
            @Param("p_horaInicio") Time HoraInicio,
            @Param("p_horaFin") Time HoraFin);


    @Procedure(name = "actualizarHorario")
    void actualizarHorario(@Param("p_idHorario")Long p_idHorario,
                       @Param("p_idOdontologo") Long p_idOdontologo,
                       @Param("p_idConsultorio") Long p_idConsultorio,
                       @Param("p_dia") String p_dia,
                       @Param("p_horaInicio") Time p_horaInicio,
                       @Param("p_horaFin") Time p_horaFin);

}
