package com.appWeb.ClinicaDental.Recursos;

import com.appWeb.ClinicaDental.entidad.MotivoCita;
import com.appWeb.ClinicaDental.entidad.Status;

import java.sql.Date;
import java.sql.Time;

public class CitaDTO {
    Long Id_Cita;
    Date Fecha;
    Time Hora;
    MotivoCita MotivoCita;
    String NombreOdontologo;
    String ApellidoOdontologo;
    String Comentarios;

    public CitaDTO(Long id_Cita,Date fecha, Time hora, MotivoCita motivoCita, String nombreOdontologo, String apellidoOdontologo, String comentarios) {
        Id_Cita = id_Cita;
        Fecha = fecha;
        Hora = hora;
        MotivoCita = motivoCita;
        NombreOdontologo = nombreOdontologo;
        ApellidoOdontologo = apellidoOdontologo;
        Comentarios = comentarios;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public Time getHora() {
        return Hora;
    }

    public void setHora(Time hora) {
        Hora = hora;
    }

    public com.appWeb.ClinicaDental.entidad.MotivoCita getMotivoCita() {
        return MotivoCita;
    }

    public void setMotivoCita(com.appWeb.ClinicaDental.entidad.MotivoCita motivoCita) {
        MotivoCita = motivoCita;
    }

    public String getNombreOdontologo() {
        return NombreOdontologo;
    }

    public void setNombreOdontologo(String nombreOdontologo) {
        NombreOdontologo = nombreOdontologo;
    }

    public String getApellidoOdontologo() {
        return ApellidoOdontologo;
    }

    public void setApellidoOdontologo(String apellidoOdontologo) {
        ApellidoOdontologo = apellidoOdontologo;
    }

    public String getComentarios() {
        return Comentarios;
    }

    public void setComentarios(String comentarios) {
        Comentarios = comentarios;
    }

    public Long getId_Cita() {
        return Id_Cita;
    }

    public void setId_Cita(Long id_Cita) {
        Id_Cita = id_Cita;
    }
}
