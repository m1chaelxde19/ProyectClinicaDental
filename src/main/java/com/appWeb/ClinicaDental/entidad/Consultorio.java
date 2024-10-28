package com.appWeb.ClinicaDental.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity(name = "consultorio")
@Getter @Setter @NoArgsConstructor
public class Consultorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_consultorio;

    @Column(name = "nombre",length = 100,nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "Campo nombre: Solo se permite letras y espacios")
    private String nombre;

    @Column(name = "capacidad",nullable = false)
    @Min(value = 0, message = "Campo Capacidad: No puede ser menor a 0")
    @Max(value = 20, message = "Campo Capacidad: No puede exceder de 20")
    private Integer capacidad;


    @Column(name = "direccion",length = 100,nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,!?()-]+$",message = "Campo Direccion: Solo se permite letras y espacios")
    private String direccion;

    @Column(name = "telefono",length = 15,nullable = false)
    @Pattern(regexp = "[0-9]{9}",message = "Campo Telefono: El número de teléfono debe tener 9 dígitos y no debe contener letras")
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado",nullable = false)
    private EstadoConsultorio estado;

    @Column(name = "fecha_creacion",nullable = false)
    private Timestamp fecha_creacion;

    @OneToMany(mappedBy = "consultorio")
    private List<Horario> horarios;

    public Consultorio(String nombre, Integer capacidad, String direccion, String telefono, EstadoConsultorio estado) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.estado = estado;
    }
}
