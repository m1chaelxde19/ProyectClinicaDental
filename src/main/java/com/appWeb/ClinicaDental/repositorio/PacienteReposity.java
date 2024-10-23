package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteReposity extends JpaRepository<Paciente, Long> {

}
