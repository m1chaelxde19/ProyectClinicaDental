package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.Recursos.HorarioGet;
import com.appWeb.ClinicaDental.entidad.DiaSemana;
import com.appWeb.ClinicaDental.entidad.Horario;
import com.appWeb.ClinicaDental.repositorio.HorarioReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Service
public class HorarioService {
    @Autowired
    private HorarioReposity horarioReposity;

    public List<Horario> listar() {
        return horarioReposity.findAll();
    }

    public boolean addHorario(HorarioGet horario) {
        Time horaConvertInicio = Time.valueOf(LocalTime.parse(horario.getHoraInicio()));
        Time horaConvertFin = Time.valueOf(LocalTime.parse(horario.getHoraFin()));
        return horarioReposity.guardarHorario(horario.getOdontologId(), horario.getConsultorioId(), horario.getDiaSemana().toString(),horaConvertInicio,horaConvertFin);
    }

    public void eliminarHorario(Long id) {
        horarioReposity.deleteById(id);
    }

    public Horario getHorario(Long id) {
        return horarioReposity.findById(id).get();
    }

    public void actualizaHorario(Long idHorario, DiaSemana diaSemana,String horaInicio,String horaFin,Long idOdontologo,Long idConsultorio, RedirectAttributes msg) {
        try {
            Time horaConvertInicio = Time.valueOf(LocalTime.parse(horaInicio));
            Time horaConvertFin = Time.valueOf(LocalTime.parse(horaFin));
            horarioReposity.actualizarHorario(idHorario,idOdontologo,idConsultorio,diaSemana.toString(),horaConvertInicio,horaConvertFin);
            msg.addFlashAttribute("msgConfirmacion", "Horario actualizado exitosamente");
        }catch (Exception e) {
            msg.addFlashAttribute("msg", "Error al actualizar el horario");
        }
    }

    public Horario getHorarioOdontologo(Long idOdontologo) {
        return horarioReposity.getHorario(idOdontologo);
    }
}
