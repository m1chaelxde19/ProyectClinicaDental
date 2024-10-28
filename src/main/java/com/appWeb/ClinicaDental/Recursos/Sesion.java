package com.appWeb.ClinicaDental.Recursos;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Sesion {
    private Long id_usuario;
    private String nombre;

    // Getters y setters
    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isLogged() {
        return id_usuario != null;
    }

    public void clear() {
        this.id_usuario = null;
        this.nombre = null;
    }
}
