package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.entidad.Usuario;
import com.appWeb.ClinicaDental.repositorio.UsuarioReposity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class EnviarEmail {
    private static final String username = "segmicmeal@gmail.com";
    private static final String appPassword = "ahum brau ezsf tpzn";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;

    @Autowired
    private UsuarioReposity usuarioReposity;

    private Usuario buscarEmail(String email) {
        return usuarioReposity.findByEmail(email);
    }

    private Map<String,String> token = new HashMap<>();
    private Map<String, LocalDateTime> tiempoExpiracion = new HashMap<>();

    public void enviarEmail(String destinatario) {
        Usuario usuario = buscarEmail(destinatario);
        if (usuario == null) {
           // log.warn("Usuario no encontrado");
            throw new IllegalArgumentException("Usuario no encontrado");
        }
            try {
                String gettoken = generarTokern();
                token.put(destinatario,gettoken);
                tiempoExpiracion.put(destinatario,LocalDateTime.now().plusMinutes(5));
                SimpleEmail email = new SimpleEmail();
                email.setHostName("smtp.gmail.com");
                email.setSmtpPort(587);
                email.setAuthenticator(new DefaultAuthenticator(username, appPassword));
                email.setSSLOnConnect(true);
                email.setFrom(username);
                email.setSubject("Codigo para restablecer la contraseña");
                email.setMsg("Usa este código para restablecer tu contraseña: "+ gettoken);
                email.addTo(destinatario);
                email.send();
            } catch (EmailException e) {
               // log.error(e.getMessage());
                throw new IllegalArgumentException("Error al enviar el correo: " + e.getMessage());
            }
    }

    public boolean verificarCodigo(String email, String codigo) {
        String codigoAlmacenado = token.get(email);
        LocalDateTime expiracion = tiempoExpiracion.get(email);
        return codigoAlmacenado.equals(codigo) && expiracion.isAfter(LocalDateTime.now());
    }

    private String generarTokern(){
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
