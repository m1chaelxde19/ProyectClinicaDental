package com.appWeb.ClinicaDental.entidad;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity(name = "pago")
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pago;

    @OneToOne
    @JoinColumn(name = "cita_id",nullable = false)
    private Cita cita;


    private BigDecimal monto;
    private metodo_Pago metodoPago;
    public enum metodo_Pago{
        EFECTIVO,
        TARJETA,
        TRANSFERENCIA,
        YAPE
    }
    private Timestamp fecha_pago;

    @ManyToOne
    @JoinColumn(name = "Paciente_id",nullable = false)
    private Paciente paciente;
}
