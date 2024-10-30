package com.appWeb.ClinicaDental.Recursos;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface ExportService {
    void exportarCitas(List<Object[]> citas, String ruta, RedirectAttributes redirectAttributes);
}
