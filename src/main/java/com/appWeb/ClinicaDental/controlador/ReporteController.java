package com.appWeb.ClinicaDental.controlador;

import com.appWeb.ClinicaDental.Recursos.ExportService;
import com.appWeb.ClinicaDental.Recursos.Sesion;
import com.appWeb.ClinicaDental.servicio.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/reporte")
public class ReporteController {
    @Autowired
    private Sesion sesion;

    @Autowired
    private CitaService citaService;

    @Autowired
    private ExportService exportService;

    @GetMapping
    public String reporte(Model model) {
        if (sesion.isLogged()) {
            model.addAttribute("nombre", sesion.getNombre());
            return "Reporte";
        }else {
            return "redirect:/api";
        }
    }

    @PostMapping("/generate")
    public String generarReporte(@Param("ruta")String ruta, RedirectAttributes redirectAttributes) {
        List<Object[]> lisCitas = citaService.listCitasReservadas();
        exportService.exportarCitas(lisCitas,ruta,redirectAttributes);
        return "redirect:/reporte";
    }
}
