package com.appWeb.ClinicaDental.Recursos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class PacienteDTO {
    private Long DTO_id;

    @NotBlank(message = "Falta el nombre")
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "Solo se permite letras y espacios")
    private String DTO_nombre;

    @NotBlank(message = "Falta el apellido")
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "Solo se permite letras y espacios")
    private String DTO_apellido;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "[0-9]{8}",message = "Solo ingrese valones numéricos")
    private String DTO_dni;

    @NotBlank(message = "La dirección es obligatoria")
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,!?()-]+$",message = "Solo se permite letras y espacios")
    private String DTO_direccion;

    @NotBlank(message = "Falta el número de teléfono")
    @Pattern(regexp = "[0-9]{9}",message = "El número de teléfono debe tener 9 dígitos y no debe contener letras")
    private String DTO_celular;

    @Email(message = "El campo del correo tiene un formato no válido")
    private String DTO_email;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private Date DTO_fechaNacimiento;

    public PacienteDTO() {
    }

    public PacienteDTO(
            Long DTO_id, String DTO_nombre, String DTO_apellido,
            String DTO_dni, String DTO_direccion,
            String DTO_celular, String DTO_email, Date DTO_fechaNacimiento
    ) {
        this.DTO_id = DTO_id;
        this.DTO_nombre = DTO_nombre;
        this.DTO_apellido = DTO_apellido;
        this.DTO_dni = DTO_dni;
        this.DTO_direccion = DTO_direccion;
        this.DTO_celular = DTO_celular;
        this.DTO_email = DTO_email;
        this.DTO_fechaNacimiento = DTO_fechaNacimiento;
    }
}
