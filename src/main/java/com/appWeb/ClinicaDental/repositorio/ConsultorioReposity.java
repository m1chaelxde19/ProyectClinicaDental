package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.Consultorio;
import com.appWeb.ClinicaDental.entidad.EstadoConsultorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ConsultorioReposity extends JpaRepository<Consultorio, Long> {
    @Modifying
    @Transactional
    @Query(value = "Call guardarConsultorio(:Cnombre,:Ccapacidad,:Ctelefono,:Cdireccion,:Cestado)",nativeQuery = true)
    void saveConsultorio(
            @Param("Cnombre")String Cnombre, @Param("Ccapacidad")Integer Ccapacidad, @Param("Ctelefono")String Ctelefono,
            @Param("Cdireccion") String Cdireccion, @Param("Cestado")String Cestado);
}
