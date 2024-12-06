package com.appWeb.ClinicaDental.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity(name = "odontologo")
@Getter @Setter @NoArgsConstructor
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_odontologo", nullable = false)
    private Long id_Odontologo;

    @Column(name = "dni", length = 9,nullable = false)
    @Pattern(regexp = "[0-9]{8}",message = "Campo DNI: Solo se ingrese valones numéricos")

    private String dni;

    @Column(name = "nombre", length = 100, nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "Campo nombre: Solo se permite letras y espacios")
    private String nombre;

    @Column(name = "apellido", length = 100, nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "Campo Apellido: Solo se permite letras y espacios")
    private String apellido;

    @Column(name = "especialidad", length = 100, nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "Campo Especialidad: Solo se permite letras y espacios")
    private String especialidad;

    @Column(name = "telefono", length = 15, nullable = false)
    @Pattern(regexp = "[0-9]{9}",message = "Campo Telefono: El número de teléfono debe tener 9 dígitos y no debe contener letras")
    private String telefono;

    @Column(name = "fecha_creacion", nullable = false)
    private Timestamp fecha_creacion;

    @ManyToOne
    @JoinColumn(name = "Usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "odontologo")
    private List<Horario> horarios;

    public Odontologo(Long id_Odontologo, String dni, String nombre, String apellido, String especialidad, String telefono, Timestamp fecha_creacion, Usuario usuario, List<Horario> horarios) {
        this.id_Odontologo = id_Odontologo;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.fecha_creacion = fecha_creacion;
        this.usuario = usuario;
        this.horarios = horarios;
    }

    public Odontologo(String dni, String nombre, String apellido, String especialidad, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
    }

    public Odontologo(Long id_Odontologo, String nombre, String apellido) {
        this.id_Odontologo = id_Odontologo;
        this.nombre = nombre;
        this.apellido = apellido;
    }
}

