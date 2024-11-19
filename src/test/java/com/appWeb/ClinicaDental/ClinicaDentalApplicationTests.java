package com.appWeb.ClinicaDental;

import com.appWeb.ClinicaDental.Recursos.HorarioGet;
import com.appWeb.ClinicaDental.entidad.DiaSemana;
import com.appWeb.ClinicaDental.entidad.Usuario;
import com.appWeb.ClinicaDental.repositorio.HorarioReposity;
import com.appWeb.ClinicaDental.servicio.HorarioService;
import com.appWeb.ClinicaDental.servicio.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.time.LocalTime;

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

	@Mock
	private HorarioReposity horarioReposity;

	@InjectMocks
	private HorarioService horarioService;


	@Test
	public void testAddHorario_Success() {
		// Arrange
		Long odontologoid = 2L;
		Long consultorioid2 = 2L;
		HorarioGet horario = new HorarioGet();
		horario.setOdontologId(odontologoid);
		horario.setConsultorioId(consultorioid2);
		horario.setDiaSemana(DiaSemana.Miercoles);
		horario.setHoraInicio("08:00");
		horario.setHoraFin("12:00");

		when(horarioReposity.guardarHorario(
				eq(odontologoid),
				eq(consultorioid2),
				eq("Miercoles"),
				eq(Time.valueOf(LocalTime.parse("08:00"))),
				eq(Time.valueOf(LocalTime.parse("12:00")))
		)).thenReturn(true);

		// Act
		boolean result = horarioService.addHorario(horario);

		// Assert
		assertTrue(result,"El método debe devolver true cuando se guarda el horario exitosamente");
		verify(horarioReposity, times(1)).guardarHorario(
				eq(odontologoid),
				eq(consultorioid2),
				eq("Miercoles"),
				eq(Time.valueOf(LocalTime.parse("08:00"))),
				eq(Time.valueOf(LocalTime.parse("12:00"))
				));
	}
}
