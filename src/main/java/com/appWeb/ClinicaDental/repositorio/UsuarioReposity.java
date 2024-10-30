package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.Horario;
import com.appWeb.ClinicaDental.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.Optional;

@Repository
public interface UsuarioReposity extends JpaRepository<Usuario, Long> {
   Usuario findByEmail(String email);

   @Modifying
   @Transactional
   @Query(value = "Call actualizarClave(:clave,:correo)", nativeQuery = true)
   void actualizarClave(@Param("clave") String clave, @Param("correo") String correo);
}
