package com.appWeb.ClinicaDental.Recursos;

import com.appWeb.ClinicaDental.entidad.MotivoCita;

import java.sql.Date;
import java.sql.Time;

public interface CitaProyection {
    Long getId_cita();
    Date getFecha();
    Time getHora();
    MotivoCita getMotivoCita();
    String getNombre();
    String getApellido();
    String getComentarios();
}
