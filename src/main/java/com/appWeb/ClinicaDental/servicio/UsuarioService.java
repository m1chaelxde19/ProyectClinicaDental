package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.Recursos.Sesion;
import com.appWeb.ClinicaDental.entidad.Usuario;
import com.appWeb.ClinicaDental.repositorio.UsuarioReposity;
import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;
@Slf4j
@Service
public class UsuarioService {
    @Autowired
    private  UsuarioReposity usuarioReposity;

    private static final Pattern VALID_INPUT_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private Usuario buscarEmail(String email) {
        return usuarioReposity.findByEmail(email);
    }

    public Usuario validarLogin(String email, String password) {
        Preconditions.checkNotNull(email,"El campo del correo no puede ser nulo");
        Preconditions.checkArgument(!email.isEmpty(), "El campo del correo no puede ser vacío");
        Preconditions.checkArgument(VALID_INPUT_PATTERN.matcher(email).matches(),"El campo del correo tiene un formato no válido");
        validarClave(password);
        Usuario usuario = buscarEmail(email);
        String pass = passCode(password);
        if (usuario == null || !pass.equals(usuario.getClave())) {
            return null;
        }
        return usuario;
    }

    public void validarClave(String clave) {
        Preconditions.checkNotNull(clave,"El campo del correo no puede ser nulo");
        Preconditions.checkArgument(!clave.isEmpty(), "El campo de la contraseña no puede ser vacío");
        Preconditions.checkArgument(!clave.contains(";") || !clave.contains("--") || !clave.contains("<") || !clave.contains(">"),
                "La contraseña tiene carácteres raros");
    }

    public void actualizarClave(String email, String clave) {
        log.info("INFO: Actualizando la contraseña del {}",email);
        try {
            validarClave(clave);
           usuarioReposity.actualizarClave(email, passCode(clave));
        }catch (Exception e){
            log.error("ERROR: Ocurrió un error al intentar actualizar la contraseña {}",e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private String passCode(String password) {
		return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
	}
}
