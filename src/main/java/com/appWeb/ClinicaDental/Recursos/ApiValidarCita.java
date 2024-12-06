package com.appWeb.ClinicaDental.Recursos;

import com.appWeb.ClinicaDental.entidad.*;
import com.appWeb.ClinicaDental.repositorio.HorarioReposity;
import com.appWeb.ClinicaDental.servicio.CitaService;
import com.appWeb.ClinicaDental.servicio.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/validar")
public class ApiValidarCita {
    @Autowired
    private HorarioReposity horarioReposity;

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/cita")
    public ResponseEntity<?> validarCita(@RequestBody Map<String,String> parametros) {

        Map<String,String> errores = new HashMap<>();
        Map<String,String> parametrosXde = new HashMap<>();

        mapearErrores(parametros,errores,parametrosXde);

        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(Map.of("dataValid",parametrosXde));
    }

    private void mapearErrores(Map<String,String> parametros,Map<String,String> errores,Map<String,String> parametrosXde) {

        if (parametros.containsKey("paciente") && parametros.containsKey("fecha") && parametros.containsKey("odontologo") &&
                parametros.containsKey("motivo") && parametros.containsKey("estado") && parametros.containsKey("comentarios")) {

            if (parametros.get("motivo").isEmpty()) {
                errores.put("motivo", "Este campo es obligatorio");
            }

            if (parametros.get("estado").isEmpty()) {
                errores.put("estado", "Este campo es obligatorio");
            }

            if (parametros.get("fecha").isEmpty()) {
                errores.put("fecha", "Este campo es obligatorio");
            }

            if (parametros.get("odontologo").isEmpty()) {
                errores.put("odontologo", "Este campo es obligatorio");
            }

            if (!parametros.get("comentarios").matches("^[a-zA-Z\\s]+$")) {
                    errores.put("comentarios", "Solo se permite letras y espacios");
            }

            Long id = Long.parseLong(parametros.get("paciente"));
            if (!(pacienteService.buscar(id).getEstado_paciente() == EstadoPaciente.activo)) {
                errores.put("paciente", "El paciente no se encuentra activo actualmente, debe activarlo para poder agendar una cita");
            }

            Long idOdontologo = Long.parseLong(parametros.get("odontologo"));
            Date fecha = Date.valueOf(parametros.get("fecha"));
            Time hora = Time.valueOf(LocalTime.parse(parametros.get("hora")));
            Time horaAproximada = horaAproximada(hora,parametros.get("motivo"));
            int horario = verificarHorario(idOdontologo, fecha, hora,horaAproximada);

            System.out.println("hora aproximada: "+horaAproximada);

            if (horario == 0) {
                errores.put("hora", "El horario no esta disponible o ya esta reservado");
            } else {
                parametrosXde.put("idHorario",String.valueOf(horario));
                parametrosXde.put("horaAproximada",horaAproximada.toString());
            }
        }
    }

    public Time horaAproximada(Time hora,String motivo){
        return switch (motivo) {
            case "Consulta" -> Time.valueOf(hora.toLocalTime().plusMinutes(45));
            case "Limpieza_Dental" -> Time.valueOf(hora.toLocalTime().plusMinutes(60));
            case "Tratamiento_Caries" -> Time.valueOf(hora.toLocalTime().plusMinutes(60));
            case "Extraccion_Dental" -> Time.valueOf(hora.toLocalTime().plusHours(1));
            case "Ortodoncia" -> Time.valueOf(hora.toLocalTime().plusHours(2));
            default -> throw new IllegalArgumentException("Motivo de la cita desconocida: " + motivo);
        };
    }

    private int verificarHorario(Long idOdontologo, Date fecha, Time hora,Time horaFin) {
        DayOfWeek diaSemana = fecha.toLocalDate().getDayOfWeek();
        DiaSemana diaConvertido = convertir(diaSemana);
        Time horaConvertida = Time.valueOf(hora.toLocalTime());
        return horarioReposity.findHorarioByOdontologoAndHora(idOdontologo,diaConvertido.toString(),horaConvertida,horaFin,fecha);
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
    
    @PostMapping("/citaEditValid")
    public ResponseEntity<?> validarCitaEdit(@RequestBody Map<String,String> parametros) {
        Map<String,String> errores = new HashMap<>();
        Map<String,String> parametrosXde = new HashMap<>();

        mapearErroresEdit(parametros,errores,parametrosXde);

        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(Map.of("dataValidada",parametrosXde));
    }

    private void mapearErroresEdit(Map<String, String> parametros, Map<String, String> errores, Map<String, String> parametrosXde) {
        if (parametros.containsKey("paciente") && parametros.containsKey("fecha") && parametros.containsKey("odontologo") &&
                parametros.containsKey("motivo") && parametros.containsKey("estado") && parametros.containsKey("comentarios")) {

            if (parametros.get("motivo").isEmpty()) {
                errores.put("motivo", "Este campo es obligatorio");
            }

            if (parametros.get("estado").isEmpty()) {
                errores.put("estado", "Este campo es obligatorio");
            }

            if (parametros.get("fecha").isEmpty()) {
                errores.put("fecha", "Este campo es obligatorio");
            }

            if (parametros.get("odontologo").isEmpty()) {
                errores.put("odontologo", "Este campo es obligatorio");
            }

            if (!parametros.get("comentarios").matches("^[a-zA-Z\\s]+$")) {
                errores.put("comentarios", "Solo se permite letras y espacios");
            }

            Long id = Long.parseLong(parametros.get("paciente"));
            if (!(pacienteService.buscar(id).getEstado_paciente() == EstadoPaciente.activo)) {
                errores.put("paciente", "El paciente no se encuentra activo actualmente, debe activarlo para poder agendar una cita");
            }

            Long idCita = Long.parseLong(parametros.get("idCita"));
            Long idOdontologo = Long.parseLong(parametros.get("odontologo"));
            Date fecha = Date.valueOf(parametros.get("fecha"));
            Time hora = Time.valueOf(LocalTime.parse(parametros.get("hora")));
            Time horaAproximada = horaAproximada(hora,parametros.get("motivo"));
            Long idHorario = Long.parseLong(parametros.get("horario"));
            int horarioValido = verificarHorarioEditCita(idOdontologo, fecha, hora,horaAproximada,idCita,id,idHorario);


            System.out.println("horarioValido = " + horarioValido);
            if (horarioValido == 0) {
                errores.put("horaFin", "El horario ya esta reservado");
                errores.put("horaAproximada",horaAproximada.toString());
            } else {
                parametrosXde.put("idHorario",String.valueOf(horarioValido));
                parametrosXde.put("horaAproximada",horaAproximada.toString());
            }
        }
    }

    private int verificarHorarioEditCita(Long idOdontologo, Date fecha, Time hora, Time horaAproximada,Long idCita,Long paciente,Long idHorario) {
        DayOfWeek diaSemana = fecha.toLocalDate().getDayOfWeek();
        DiaSemana diaConvertido = convertir(diaSemana);
        return horarioReposity.verificarHorarioCita(idOdontologo,diaConvertido.toString(),
                hora,horaAproximada,idCita,paciente,fecha,idHorario);
    }
}


