package com.appWeb.ClinicaDental.entidad;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "horario")
@Data
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_horario;

    @ManyToOne
    @JoinColumn(name = "Odontologo_id", nullable = false)
    private Odontologo odontologo;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana",nullable = false)
    private DiaSemana dia_semana;

    @Column(name = "hora_inicio",nullable = false)
    private Time hora_inicio;

    @Column(name = "hora_fin",nullable = false)
    private Time hora_fin;

    @Column(name = "fecha_creacion",nullable = false)
    @CreationTimestamp
    private Timestamp fecha_creacion;

    @Column(name="fecha_actualizacion", nullable = false)
    @LastModifiedDate
    private Timestamp fecha_actualizacion;

    @OneToMany(mappedBy = "horario")
    private List<Cita> citas;

    @ManyToOne
    @JoinColumn(name = "Consultorio_id",nullable = false)
    private Consultorio consultorio;
}
