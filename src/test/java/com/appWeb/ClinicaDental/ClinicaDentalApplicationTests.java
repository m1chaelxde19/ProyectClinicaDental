package com.appWeb.ClinicaDental;

import com.appWeb.ClinicaDental.Recursos.ApiValidarCita;
import com.appWeb.ClinicaDental.Recursos.CitaDTO;
import com.appWeb.ClinicaDental.Recursos.HorarioGet;
import com.appWeb.ClinicaDental.Recursos.PacienteDTO;
import com.appWeb.ClinicaDental.entidad.*;
import com.appWeb.ClinicaDental.repositorio.CitaReposity;
import com.appWeb.ClinicaDental.repositorio.HorarioReposity;
import com.appWeb.ClinicaDental.repositorio.PacienteReposity;
import com.appWeb.ClinicaDental.servicio.CitaService;
import com.appWeb.ClinicaDental.servicio.HorarioService;
import com.appWeb.ClinicaDental.servicio.PacienteService;
import com.appWeb.ClinicaDental.servicio.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClinicaDentalApplicationTests {
	@Autowired
	private UsuarioService usuarioService;
	@Test
	void contextLoads() {
		String correoTest = "medi@gmail.com";
		Usuario usuario = usuarioService.buscarEmail(correoTest);

		assertNotNull(usuario,"El usuario debería existir en la base de datos");
		assertEquals(correoTest, usuario.getEmail(), "El email del usuario debería coincidir.");
	}

	@Autowired
	private PacienteService pacienteService;
	@Test
	void BuscarPaciente(){
		Long id = 19L;
		boolean paciente = pacienteService.buscar(id).getEstado_paciente() == EstadoPaciente.activo;
		if (paciente) {
			System.out.println("El paciente se encuentra activo");
		}
	}

	@Autowired
	HorarioService horarioService;
	@Test
	void getHorario(){
		Time hora = Time.valueOf("20:00:00");
	//	System.out.println(ApiValidarCita.horaAproximada(hora,"Ortodoncia"));
	}

	@Autowired
	CitaService citaService;

	@Autowired
	CitaReposity citaReposity;
	@Test
	void getCita(){
		List<CitaDTO> citas = citaService.getCitasOdontologicas();
		citas.forEach(
				cita -> System.out.println(cita.getId_Cita() + " " + cita.getFecha() + " " + cita.getHora() + " " + cita.getHoraFin() + " " + cita.getMotivoCita() + " " + cita.getPaciente() + " " + cita.getDoctor() + " " + cita.getComentarios())
		);
	}
}
