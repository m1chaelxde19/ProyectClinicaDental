package com.appWeb.ClinicaDental.entidad;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity(name = "paciente")
@Data
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente",nullable = false)
    private Long id_paciente;

    @Column(name = "nombre", length = 100,nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 100,nullable = false)
    private String apellido;

    @Column(name = "dni", length = 8,nullable = false)
    private String dni;

    @Column(name = "telefono", length = 15,nullable = false)
    private String telefono;

    @Column(name = "email", length = 100,nullable = false)
    private String email;

    @Column(name = "direccion", length = 225,nullable = false)
    private String direccion;

    @Column(name = "fecha_registro", nullable = false)
    private Timestamp fecha_registro;

    //relacion con usuario
    @OneToOne
    @JoinColumn(name = "Usuario_id",nullable = false)
    private Usuario usuario;

    //relacion con cita
    @OneToMany(mappedBy = "paciente")
    public List<Cita> citas;

    //relacion con pago
    @OneToMany(mappedBy = "paciente")
    private List<Pago> pagos;

}
