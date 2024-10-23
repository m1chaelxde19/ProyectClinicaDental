package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.entidad.Usuario;
import com.appWeb.ClinicaDental.repositorio.UsuarioReposity;
import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UsuarioService {
    @Autowired
    private  UsuarioReposity usuarioReposity;

    private static final Pattern VALID_INPUT_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private Usuario buscarEmail(String email) {
        return usuarioReposity.findByEmail(email);
    }

    public List<Usuario> listar() {
        return usuarioReposity.findAll();
    }


    public void validarLogin(String email, String password, HttpSession session) {
        Preconditions.checkNotNull(email,"El campo del correo no puede ser nulo");
        Preconditions.checkNotNull(password,"El campo del correo no puede ser nulo");

        Preconditions.checkArgument(!email.isEmpty(), "El campo del correo no puede ser vacío");
        Preconditions.checkArgument(!password.isEmpty(), "El campo de la contraseña no puede ser vacío");

        Preconditions.checkArgument(VALID_INPUT_PATTERN.matcher(email).matches(),"El campo del correo tiene un formato no válido");

        Preconditions.checkArgument(!password.contains(";") || !password.contains("--") || !password.contains("<") || !password.contains(">"),
                "La contraseña tiene carácteres raros");

        Usuario usuario = buscarEmail(email);
        if (usuario == null || !passCode(password).equals(usuario.getClave())) {
            throw new IllegalArgumentException("El campo del correo o contraseña no son correctos");
        }else{
            session.setAttribute("usuarioLogueado", usuario);
        }
    }

    private String passCode(String password) {
		return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
	}
}
