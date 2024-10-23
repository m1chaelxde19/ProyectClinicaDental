package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BitacoraReposity extends JpaRepository<Bitacora, Long> {
}
