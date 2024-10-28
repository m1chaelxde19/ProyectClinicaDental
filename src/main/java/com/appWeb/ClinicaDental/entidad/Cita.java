package com.appWeb.ClinicaDental.entidad;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity(name = "cita")
@Data
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long id_cita;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "hora", nullable = false)
    private Time hora;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Status estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_cita", nullable = false)
    private MotivoCita motivo_cita;

    @Column(name = "comentarios", columnDefinition = "TEXT")
    private String comentarios;

    @ManyToOne
    @JoinColumn(name = "Paciente_id", nullable = false)
    private Paciente paciente;

    @OneToMany(mappedBy = "cita")
    private List<HistorialDiagnostico> historiales;

    @ManyToOne
    @JoinColumn(name = "Horario_id")
    private Horario horario;

    @OneToOne(mappedBy = "cita")
    private Pago pago;
}
