package com.appWeb.ClinicaDental;

import com.appWeb.ClinicaDental.entidad.Usuario;
import com.appWeb.ClinicaDental.servicio.CitaService;
import com.appWeb.ClinicaDental.servicio.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
	private CitaService citaService;


	@Test
	void reporte(){

	}
}
