package com.appWeb.ClinicaDental.Recursos;

import com.appWeb.ClinicaDental.entidad.DiaSemana;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
@Getter @Setter
public class HorarioGet {
    private Long Id_Horario;
    private Long OdontologId;
    private DiaSemana DiaSemana;
    private String horaInicio;
    private String horaFin;
    private Long consultorioId;

    public HorarioGet(Long odontologId, com.appWeb.ClinicaDental.entidad.DiaSemana diaSemana, String horaInicio, String horaFin, Long consultorioId) {
        OdontologId = odontologId;
        DiaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.consultorioId = consultorioId;
    }

    public HorarioGet(Long id_Horario, Long odontologId, com.appWeb.ClinicaDental.entidad.DiaSemana diaSemana, String horaInicio, String horaFin, Long consultorioId) {
        Id_Horario = id_Horario;
        OdontologId = odontologId;
        DiaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.consultorioId = consultorioId;
    }

    public HorarioGet() {
    }
}
