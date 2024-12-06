package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.Recursos.PacienteDTO;
import com.appWeb.ClinicaDental.entidad.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteReposity extends JpaRepository<Paciente, Long> {
    @Query(value = "CALL guardarPaciente(:Gnombre, :Gapellido, :Gdni, :Gfecha_nacimiento, :Gtelefono, :Gemail, :Gdireccion, :GusuarId)", nativeQuery = true)
    int guardarPaciente(
            @Param("Gnombre") String Gnombre,
            @Param("Gapellido") String Gapellido,
            @Param("Gdni") String Gdni,
            @Param("Gfecha_nacimiento") Date Gfecha_nacimiento,
            @Param("Gtelefono") String Gtelefono,
            @Param("Gemail") String Gemail,
            @Param("Gdireccion") String Gdireccion,
            @Param("GusuarId") Long GusuarId
    );

    @Transactional
    @Modifying
    @Procedure(name = "updatePaciente")
    void updatePaciente(
            @Param("p_pacienteID") Long p_pacienteID, @Param("p_nombres") String p_nombres, @Param("p_apellidos") String p_apellidos,
            @Param("p_dni") String p_dni, @Param("p_fNacimiento") Date p_fNacimiento, @Param("p_telefono") String p_telefono,
            @Param("p_email") String p_email, @Param("p_direccion") String p_direccion
    );

    @Transactional
    @Modifying
    @Procedure(name = "estadoPacienteInactivo")
    void estadoPacienteInactivo(@Param("p_pacienteID") Long p_pacienteID);

    @Transactional
    @Modifying
    @Procedure(name = "estadoActivoPaciente")
    void estadoActivoPaciente(@Param("p_pacienteID") Long p_pacienteID);

    List<Paciente> findByDniOrNombreOrApellidoContainingIgnoreCase(String palabra, String palabra2, String palabra3);

    Paciente findByIdPaciente(Long idPaciente);
}