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
    @Query(value = "Call horarioDisponible(:odontologoId,:diaSemana,:hora)", nativeQuery = true)
    Optional<Horario> findHorarioByOdontologoAndHora(@Param("odontologoId") Long odontologoId,
                                                     @Param("diaSemana") String diaSemana,
                                                     @Param("hora") Time hora);
}
