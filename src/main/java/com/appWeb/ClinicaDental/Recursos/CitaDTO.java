package com.appWeb.ClinicaDental.Recursos;

import com.appWeb.ClinicaDental.entidad.MotivoCita;
import com.appWeb.ClinicaDental.entidad.Status;

import java.sql.Date;
import java.sql.Time;

public interface CitaDTO {
    Date getFecha();
    Time getHora();
    MotivoCita getMotivoCita();
    Status getStatus();
    String getNombreOdontologo();
    String getApellidoOdontologo();
    String getComentarios();
    Long getId_paciente();
    Long getId_odontologo();
}
