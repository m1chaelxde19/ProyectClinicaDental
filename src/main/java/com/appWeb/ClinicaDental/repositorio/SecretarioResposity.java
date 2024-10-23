package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.Secretario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretarioResposity extends JpaRepository<Secretario, Long> {
}
