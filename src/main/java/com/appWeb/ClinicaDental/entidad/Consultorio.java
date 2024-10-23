package com.appWeb.ClinicaDental.entidad;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity(name = "consultorio")
@Data
public class Consultorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_consultorio;

    @Column(name = "nombre",length = 100,nullable = false)
    private String nombre;

    @Column(name = "direccion",length = 100,nullable = false)
    private String direccion;

    @Column(name = "telefono",length = 15,nullable = false)
    private String telefono;

    @Column(name = "fecha_creacion",nullable = false)
    private Timestamp fecha_creacion;

    @OneToMany(mappedBy = "consultorio")
    private List<Horario> horarios;
}
