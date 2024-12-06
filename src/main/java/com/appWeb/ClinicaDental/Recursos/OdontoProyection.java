package com.appWeb.ClinicaDental.Recursos;

import java.sql.Time;

public interface OdontoProyection {
    Long getId_Odontologo();
    String getDni();
    String getNombre();
    String getApellido();
    String getEspecialidad();
    String getTelefono();
    Time getHora_Inicio();
    Time getHora_Fin();
}
