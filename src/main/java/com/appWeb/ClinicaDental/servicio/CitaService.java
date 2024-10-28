package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.Recursos.CitaDTO;
import com.appWeb.ClinicaDental.Recursos.CitaProyection;
import com.appWeb.ClinicaDental.entidad.*;
import com.appWeb.ClinicaDental.repositorio.CitaReposity;
import com.appWeb.ClinicaDental.repositorio.HorarioReposity;
import com.appWeb.ClinicaDental.repositorio.OdontologoReposity;
import com.appWeb.ClinicaDental.repositorio.PacienteReposity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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

    public List<CitaDTO> getCitasOdontologicas(){
        List<CitaProyection> proyections = citaReposity.citasConOdontologos();
        return proyections.stream().map(proyection-> new CitaDTO(
            proyection.getFecha(),proyection.getHora(),
                proyection.getMotivoCita(), proyection.getNombre(), proyection.getApellido(),
                proyection.getComentarios()
        )).collect(Collectors.toList());
    }

    public void agregarCita(Date fecha, Time hora, MotivoCita motivoCita,
                            Status estadoCita, Long idPaciente, Long Idodont,
                            String comentarios) {
        DayOfWeek diaSemana = fecha.toLocalDate().getDayOfWeek();
        System.out.println("DayOfWeek: "+diaSemana);
        DiaSemana diaConvertido = convertir(diaSemana);
        System.out.println("Dia convertido: "+diaConvertido);
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
            log.warn("WARN: Horario no encontrado");
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
}