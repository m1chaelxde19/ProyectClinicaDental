package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.Recursos.CitaDTO;
import com.appWeb.ClinicaDental.entidad.*;
import com.appWeb.ClinicaDental.repositorio.CitaReposity;
import com.appWeb.ClinicaDental.repositorio.HorarioReposity;
import com.appWeb.ClinicaDental.repositorio.OdontologoReposity;
import com.appWeb.ClinicaDental.repositorio.PacienteReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<CitaDTO> citas = new ArrayList<>();
        for (Object[] fila: citaReposity.citasConOdontologos()){
            CitaDTO citaDTO = new CitaDTO(
                    (Date) fila[0], (Time) fila[1], MotivoCita.valueOf((String) fila[2]),
                    (String) fila[3], (String) fila[4], (String) fila[5]
            );
            citas.add(citaDTO);
        }
        return citas;
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