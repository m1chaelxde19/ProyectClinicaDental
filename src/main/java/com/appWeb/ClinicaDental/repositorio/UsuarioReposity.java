package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioReposity extends JpaRepository<Usuario, Long> {

   Usuario findByEmail(String email);
}
