package com.appWeb.ClinicaDental.entidad;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity(name = "odontologo")
@Data
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_odontologo", nullable = false)
    private Long id_Odontologo;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;

    @Column(name = "especialidad", length = 100, nullable = false)
    private String especialidad;

    @Column(name = "telefono", length = 15, nullable = false)
    private String telefono;

    @Column(name = "fecha_creacion", nullable = false)
    private Timestamp fecha_creacion;

    @OneToOne
    @JoinColumn(name = "Usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "odontologo")
    private List<Horario> horarios;
}

