package com.appWeb.ClinicaDental.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.util.List;

@Entity(name="usuario")
@Getter @Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario",nullable = false)
    private Long id_usuario;

    @Column(name="nombre",nullable = false)
    private String nombre;

    @Column(name = "apellido",nullable = false)
    private String apellido;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "clave", nullable = false, length = 225)
    private String clave;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    public Usuario() {

    }

    public enum Rol {
        Secretario,
        Ondontologo,
        Administrador
    }

    public Usuario(String nombre,String apellido,String email, String clave, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.clave = clave;
        this.rol = rol;

    }

    @Column(name = "fecha_creacion", nullable = true)
    @CreationTimestamp
    private Timestamp fechaCreacion;

    @Column(name="fecha_actualizacion", nullable = true)
    @LastModifiedDate
    private Timestamp fecha_actualizacion;

    @OneToMany(mappedBy = "usuario")
    private List<Bitacora> bitacoras;

    @OneToOne(mappedBy = "usuario")
    private Odontologo odontologo;

    @OneToOne(mappedBy = "usuario")
    private Secretario secretario;

    @OneToMany(mappedBy = "usuario")
    private List<Paciente> pacientes;
}
