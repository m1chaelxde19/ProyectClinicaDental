package com.appWeb.ClinicaDental.entidad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "paciente")
@Getter @Setter
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente",nullable = false)
    private Long idPaciente;

    @Column(name = "nombre", length = 100,nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "Campo nombre: Solo se permite letras y espacios")
    private String nombre;

    @Column(name = "apellido", length = 100,nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "Campo Apellido: Solo se permite letras y espacios")
    private String apellido;

    @Column(name = "dni", length = 8,nullable = false,unique = true)
    @Pattern(regexp = "[0-9]{8}",message = "Campo DNI: Solo se ingrese valones numéricos")
    private String dni;

    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    @Pattern(regexp = "[0-9]{9}",message = "Campo Telefono: El número de teléfono debe tener 9 dígitos y no debe contener letras")
    @Column(name = "telefono", length = 15,nullable = false)
    private String telefono;

    @Column(name = "email", length = 100,nullable = false)
    private String email;

    @Column(name = "direccion", length = 225,nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,!?()-]+$",message = "Campo Direccion: Solo se permite letras y espacios")
    private String direccion;

    @Column(name = "fecha_registro", nullable = false)
    private Timestamp fecha_registro;


    //relacion con usuario
    @ManyToOne
    @JoinColumn(name = "Usuario_id",nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name="estado_paciente",nullable = false)
    EstadoPaciente estado_paciente = EstadoPaciente.activo;

    //relacion con cita
    @OneToMany(mappedBy = "paciente")
    public List<Cita> citas;

    //relacion con pago
    @OneToMany(mappedBy = "paciente")
    private List<Pago> pagos;

    public Paciente() {
    }

    public Paciente(String nombre, String apellido, String dni, Date fechaNacimiento, String telefono,
                    String email, String direccion, Timestamp fecha_registro, EstadoPaciente estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.fecha_registro = fecha_registro;
        this.estado_paciente = estado;
    }

    public Paciente(String nombre, String apellido, String dni, Date fechaNacimiento, String telefono,
                    String email, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id_paciente=" + idPaciente +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha_registro=" + fecha_registro +
                ", usuario=" + usuario.getId_usuario() +
                ", estado=" + estado_paciente +
                '}';
    }
}
