package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.Recursos.CitaDTO;
import com.appWeb.ClinicaDental.Recursos.CitaProyection;
import com.appWeb.ClinicaDental.entidad.*;
import com.appWeb.ClinicaDental.repositorio.CitaReposity;
import com.appWeb.ClinicaDental.repositorio.HorarioReposity;
import com.appWeb.ClinicaDental.repositorio.OdontologoReposity;
import com.appWeb.ClinicaDental.repositorio.PacienteReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaService {
    @Autowired
    private CitaReposity citaReposity;

    @Autowired
    private PacienteReposity pacienteReposity;

    @Autowired
    private OdontologoReposity odontologoReposity;

    @Autowired
    private HorarioReposity horarioReposity;

    public List<Object[]> listCitasReservadas(){
        return citaReposity.ListadoCitasReservadas();
    }

    public List<CitaDTO> getCitasOdontologicas(){
        List<CitaProyection> proyections = citaReposity.citasConOdontologos();
        System.out.println("cita: xde: "+proyections.get(1).getId_cita());
        return proyections.stream().map(proyection-> new CitaDTO(
                proyection.getId_cita(), proyection.getFecha(),proyection.getHora(),proyection.getMotivoCita(),
                proyection.getNombre(), proyection.getApellido(), proyection.getComentarios()
        )).collect(Collectors.toList());
    }

    public void agregarCita(Date fecha, Time hora, MotivoCita motivoCita,
                            Status estadoCita, Long idPaciente, Long Idodont,
                            String comentarios) {
        DayOfWeek diaSemana = fecha.toLocalDate().getDayOfWeek();
        DiaSemana diaConvertido = convertir(diaSemana);
        Optional<Horario> horarioOp = horarioReposity.findHorarioByOdontologoAndHora(Idodont,diaConvertido.name(),hora);
        if(horarioOp.isPresent()) {
            Cita citasa = new Cita();
            citasa.setFecha(fecha);
            citasa.setHora(hora);
            citasa.setMotivo_cita(motivoCita);
            citasa.setEstado(estadoCita);
            citasa.setComentarios(comentarios);
            Paciente paciente = pacienteReposity.findById(idPaciente)
                .orElseThrow(()->new RuntimeException("Paciente no encontrado"));
            citasa.setPaciente(paciente);
            citasa.setHorario(horarioOp.get());
            citaReposity.save(citasa);
        }else{
      //      log.warn("WARN: Horario no encontrado");
            throw new IllegalArgumentException("Horario no disponible");
        }
    }

    private DiaSemana convertir(DayOfWeek diaSemana) {
        return switch (diaSemana) {
            case MONDAY -> DiaSemana.Lunes;
            case TUESDAY -> DiaSemana.Martes;
            case WEDNESDAY -> DiaSemana.Miercoles;
            case THURSDAY -> DiaSemana.Jueves;
            case FRIDAY -> DiaSemana.Viernes;
            case SATURDAY -> DiaSemana.Sabado;
            case SUNDAY -> DiaSemana.Domingo;
            default -> throw new IllegalArgumentException("Día de la semana no válido: " + diaSemana);
        };
    }

    public void eliminarCita(Long idCita) {
        citaReposity.deleteById(idCita);
    }

    public Cita getUsuario(Long id){
        return citaReposity.findById(id).get();
    }

    public void saveCita(Long idCita,
                         Long idPaciente,
                         Long idOdontolog,
                         Time hora, Date fecha,
                         String estado, String comentarios,
                         String motivoCita, RedirectAttributes msg) {
       try {
           DayOfWeek diaSemana = fecha.toLocalDate().getDayOfWeek();
           DiaSemana diaConvertido = convertir(diaSemana);
           Cita horario = citaReposity.findById(idCita).get();
           Optional<Horario> horarioOp = horarioReposity.findHorarioByOdontologoAndHora(idOdontolog,diaConvertido.name(),hora);

           if(horarioOp.isPresent()) {
               citaReposity.actualizaCita(idCita,fecha,hora,estado,motivoCita,comentarios,idPaciente,horario.getHorario().getId_horario());
               msg.addFlashAttribute("confirm","Cita actualizada");
           }else {
               msg.addFlashAttribute("errorCita","Horario no disponible");
           }
       }catch (Exception e) {
           System.out.println("Error: "+e.getMessage());
           msg.addFlashAttribute("errorCita","Error al actualizar la cita");
       }

    }
}