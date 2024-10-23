package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdontologoReposity extends JpaRepository<Odontologo, Long> {
}
