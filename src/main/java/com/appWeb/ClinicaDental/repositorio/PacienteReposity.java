package com.appWeb.ClinicaDental.repositorio;

import com.appWeb.ClinicaDental.entidad.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
public interface PacienteReposity extends JpaRepository<Paciente, Long> {
    @Modifying
    @Transactional
    @Query(value = "Call guardarPaciente(:Gnombre,:Gapellido,:Gdni,:Gfechanacimiento,:Gtelefono,:Gemail,:Gdireccion,:GIduser)", nativeQuery = true)
    void savePaciente(@Param("Gnombre") String Gnombre,
                      @Param("Gapellido") String Gapellido,
                      @Param("Gdni") String Gdni, @Param("Gfechanacimiento")Date Gfechanacimiento, @Param("Gtelefono") String Gtelefono,
                      @Param("Gemail") String Gemail, @Param("Gdireccion") String Gdireccion, @Param("GIduser") Long GIduser
                      );
}
