package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.DiaSemana;
import com.appWeb.ClinicaDental.entidad.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface HorarioReposity extends JpaRepository<Horario,Long> {
    //@Query("SELECT h FROM horario h WHERE h.odontologo.id_Odontologo = :odontologoId AND h.dia_semana = :diaSemana AND :hora BETWEEN h.hora_inicio AND h.hora_fin")
    @Query(value = "Call horarioDisponible(:odontologoId,:diaSemana,:hora)", nativeQuery = true)
    Optional<Horario> findHorarioByOdontologoAndHora(@Param("odontologoId") Long odontologoId,
                                                     @Param("diaSemana") String diaSemana,
                                                     @Param("hora") Time hora);
}
