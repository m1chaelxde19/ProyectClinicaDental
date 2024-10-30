package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OdontologoReposity extends JpaRepository<Odontologo, Long> {
    @Modifying
    @Transactional
    @Query(value = "Call guardarOdontologo(:dni,:nombre,:apellido,:especialidad,:telefono,:idOdontologo)",nativeQuery = true)
    void saveOdontologo(@Param("dni")String dni, @Param("nombre")String nombre, @Param("apellido")String apellido,
                        @Param("especialidad")String especialidad, @Param("telefono")String telefono, @Param("idOdontologo")Long idOdontologo);
}
