package com.appWeb.ClinicaDental.Recursos;

import com.appWeb.ClinicaDental.entidad.MotivoCita;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter @Setter
public class CitaDTO {
    Long Id_Cita;

    Date Fecha;

    Time Hora;

    Time HoraFin;

    MotivoCita MotivoCita;

    String Paciente;
    String Doctor;

    String Comentarios;

    public CitaDTO(Long id_Cita,Date fecha, Time hora,Time horaFin,
                   MotivoCita motivoCita, String paciente, String doctor, String comentarios) {
        Id_Cita = id_Cita;
        Fecha = fecha;
        Hora = hora;
        HoraFin = horaFin;
        MotivoCita = motivoCita;
        Paciente = paciente;
        Doctor = doctor;
        Comentarios = comentarios;
    }

    public CitaDTO() {
    }

    @Override
    public String toString() {
        return "CitaDTO{" +
                "Id_Cita=" + Id_Cita +
                ", Fecha=" + Fecha +
                ", Hora=" + Hora +
                ", HoraFin=" + HoraFin +
                ", MotivoCita=" + MotivoCita +
                ", Paciente='" + Paciente + '\'' +
                ", Doctor='" + Doctor + '\'' +
                ", Comentarios='" + Comentarios + '\'' +
                '}';
    }
}
