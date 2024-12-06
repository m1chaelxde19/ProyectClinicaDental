package com.appWeb.ClinicaDental.Recursos;
import com.appWeb.ClinicaDental.servicio.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class ApiSearchPaciente {
    private final PacienteService patientService;

    public ApiSearchPaciente(PacienteService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<PacienteDTO>> searchPatients(@RequestParam String query) {
        List<PacienteDTO> patients = patientService.buscarPaciente(query);
        return ResponseEntity.ok(patients);
    }
}