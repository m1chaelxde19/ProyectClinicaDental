package com.appWeb.ClinicaDental.entidad;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity(name = "historial_diagnostico")
@Data
public class HistorialDiagnostico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_his_clinico", nullable = false)
    private Long id_historial;

    @ManyToOne
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    @Column(name = "descripcion",columnDefinition = "TEXT",nullable = false)
    private String descripcion;

    @Column(name = "tratamiento",columnDefinition = "TEXT",nullable = false)
    private String tratamiento;

    @Column(name = "fecha_creacion")
    private Timestamp fecha_creacion;

}
