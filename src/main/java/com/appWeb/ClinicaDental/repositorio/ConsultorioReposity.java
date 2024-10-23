package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultorioReposity extends JpaRepository<Consultorio, Long> {
}
