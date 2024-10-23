package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoResposity extends JpaRepository<Pago,Long> {
}
