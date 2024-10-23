package com.appWeb.ClinicaDental.entidad;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity(name = "secretario")
@Data
public class Secretario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_secretario",nullable = false)
    private Long id_secretario;

    @Column(name = "nombre", length = 100,nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 100,nullable = false)
    private String apellido;

    @Column(name = "telefono", length = 15,nullable = false)
    private String telefono;

    @Column(name = "fecha_registro", nullable = false)
    private Timestamp fecha_registro;

    @OneToOne
    @JoinColumn(name = "Usuario_id", nullable = false)
    private Usuario usuario;
}
