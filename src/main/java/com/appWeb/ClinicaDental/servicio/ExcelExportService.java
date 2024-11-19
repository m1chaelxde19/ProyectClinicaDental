package com.appWeb.ClinicaDental.servicio;

import com.appWeb.ClinicaDental.Recursos.ExportService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ExcelExportService implements ExportService {
    @Override
    public void exportarCitas(List<Object[]> citas, String ruta, RedirectAttributes redirectAttributes) {
        String userHome = System.getProperty("user.home");
        Path destino = Paths.get(userHome, "Downloads", ruta + ".xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte de citas");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"DNI_PACIENTE", "Nombre Paciente", "Apellido Paciente", "Paciente Teléfono",
                    "Fecha cita", "Hora cita", "Estado cita", "Día cita",
                    "Nombre odontólogo", "Apellido Odontólogo", "Consultorio", "Dirección consultorio"};

            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (Object[] row : citas) {
                Row dataRow = sheet.createRow(rowNum++);
                for (int i = 0; i < row.length; i++) {
                    dataRow.createCell(i).setCellValue(row[i].toString());
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream(destino.toFile())) {
                workbook.write(fileOut);
            }
            //log.info("Se ha exportado un archivo Excel en la ruta {}", destino.toAbsolutePath().toString());
            redirectAttributes.addFlashAttribute("message", "Registro de citas exportado con exito");
        } catch (Exception e) {
           // log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Error en la exportación");
            e.printStackTrace();
        }
    }

}
