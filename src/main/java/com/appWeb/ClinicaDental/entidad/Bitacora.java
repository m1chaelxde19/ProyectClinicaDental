package com.appWeb.ClinicaDental.entidad;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity(name = "bitacora")
@Data
public class Bitacora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Bitacora")
    private Long id_Bitacora;

    @Column(name = "fecha", nullable = false)
    private Timestamp fecha;

    @Column(name = "operacion", length = 100, nullable = false)
    private String operacion;

    @Column(name = "tabla", length = 50, nullable = false)
    private String tabla;

    @Column(name = "id_registro_afectado", nullable = false)
    private String id_registro_afectado;

    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "Usuario_id", nullable = false)
    private Usuario usuario;
}
