package com.appWeb.ClinicaDental.Recursos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
@Getter @Setter
public class OdontologoDTO {
    private Long id_Odontologo;
    private String dni;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String telefono;
    private Time hora_Inicio;
    private Time hora_Fin;

    public OdontologoDTO() {
    }

    public OdontologoDTO(Long id_Odontologo, String dni, String nombre, String apellido, String especialidad, String telefono, Time horaInicio, Time horaFin) {
        this.id_Odontologo = id_Odontologo;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.hora_Inicio = horaInicio;
        this.hora_Fin = horaFin;
    }
}
