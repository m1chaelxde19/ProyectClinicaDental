package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.Recursos.CitaDTO;
import com.appWeb.ClinicaDental.Recursos.CitaProyection;
import com.appWeb.ClinicaDental.entidad.*;
import com.appWeb.ClinicaDental.repositorio.CitaReposity;
import com.appWeb.ClinicaDental.repositorio.HorarioReposity;
import com.appWeb.ClinicaDental.repositorio.OdontologoReposity;
import com.appWeb.ClinicaDental.repositorio.PacienteReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;
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
    @Autowired
    private HorarioService horarioService;

    public List<Object[]> listCitasReservadas(){
        return citaReposity.ListadoCitasReservadas();
    }

    public List<CitaDTO> getCitasOdontologicas(){
        List<CitaProyection> proyections = citaReposity.citasConOdontologos();
        return proyections.stream().map(proyection-> new CitaDTO(
                proyection.getId_cita(),
                proyection.getFecha(),
                proyection.getHora(),
                proyection.getHoraFin(),
                proyection.getMotivoCita(),
                proyection.getPaciente(),
                proyection.getDoctor(),
                proyection.getComentarios()
        )).collect(Collectors.toList());
    }


    public void agregarCita(Date fecha, Time hora, MotivoCita motivoCita,
                            Status estadoCita, Long idPaciente, Long idHorario,
                            String comentarios,Time horaFin) {
        citaReposity.saveCita(fecha,hora,estadoCita.toString(),motivoCita.toString(),comentarios,idPaciente,idHorario,horaFin);
    }

    public void eliminarCita(Long idCita) {
        citaReposity.actualizarEstadoCita(idCita,Status.Realizada.toString());
    }

    public Cita getCita(Long idCita) {
        return citaReposity.findByIdCita(idCita);
    }

    public void saveCita(Long idCita,
                         Long idPaciente,
                         Time hora, Date fecha,
                         String estado, String comentarios,
                         String motivoCita, Long idHorario,Time horafin, RedirectAttributes msg) {
       try {
           citaReposity.actualizarCita(idCita,fecha,hora,estado,motivoCita,comentarios,idPaciente,idHorario,horafin);
           msg.addFlashAttribute("confirm","Cita actualizada");
       }catch (Exception e) {
           System.out.println("Error: "+e.getMessage());
           msg.addFlashAttribute("errorCita","Error al actualizar la cita");
       }

    }

    public Page<CitaDTO> listarCitasPorEstado (Status estado,int page, int size){
        return citaReposity.findByEstado(estado,PageRequest.of(page,size)).map(cita -> new CitaDTO(
                cita.getIdCita(),
                cita.getFecha(),
                cita.getHora(), cita.getHoraFin(),
                cita.getMotivo_cita(),
                cita.getPaciente().getNombre(),
                cita.getHorario().getOdontologo().getNombre().concat(" ").concat(cita.getHorario().getOdontologo().getApellido()),
                cita.getComentarios()
        ));
    }

    public Page<CitaDTO> listarCitasPorEstadoYFecha (Status estado, Date fecha,int page, int size){
        return citaReposity.findByEstadoAndFecha(estado,fecha,PageRequest.of(page,size)).map(cita -> new CitaDTO(
                cita.getIdCita(),
                cita.getFecha(),
                cita.getHora(), cita.getHoraFin(),
                cita.getMotivo_cita(),
                cita.getPaciente().getNombre(),
                cita.getHorario().getOdontologo().getNombre().concat(" ").concat(cita.getHorario().getOdontologo().getApellido()),
                cita.getComentarios()
        ));
    }

    public Page<CitaDTO> listarCitasPorFecha (Date fecha,int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return citaReposity.findByFecha(fecha, pageable).map(cita -> new CitaDTO(
                cita.getIdCita(),
                cita.getFecha(),
                cita.getHora(),
                cita.getHoraFin(),
                cita.getMotivo_cita(),
                cita.getPaciente().getNombre(),
                cita.getHorario().getOdontologo().getNombre().concat(" ").concat(cita.getHorario().getOdontologo().getApellido()),
                cita.getComentarios()
        ));
    }

    public Page<Cita> citasPage(int page, int size){
        return citaReposity.findByEstado(Status.Pendiente, PageRequest.of(page,size));
    }
}