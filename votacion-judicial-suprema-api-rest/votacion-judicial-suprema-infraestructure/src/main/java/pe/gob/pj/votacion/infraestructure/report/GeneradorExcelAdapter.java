package pe.gob.pj.votacion.infraestructure.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.model.report.ArchivoReporte;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteEstadoVotacionItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteGeneralProyectoItem;
import pe.gob.pj.votacion.domain.port.report.GeneradorExcelPort;

@Slf4j
@Component
public class GeneradorExcelAdapter implements GeneradorExcelPort {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

  @Override
  public ArchivoReporte generarExcelReporteEstadoVotacion(List<ReporteEstadoVotacionItem> datos) {
    
    log.info("Generando reporte Excel para Estado de Votación con {} filas.", datos.size());

    String[] headersEstadoVotacion = {"Fecha Programación", "Nro. Expediente", "Ponente",
        "Nivel Instrucción", "Estado Voto", "Tipo Parte", "Recurrente", "Sentido", "Fallo"};

    try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {

      Sheet sheet = workbook.createSheet("Reporte Estado Votación");

      CellStyle headerStyle = createHeaderStyle(workbook);
      CellStyle bodyStyle = createBodyStyle(workbook);

      // Crear la fila de cabecera dinámicamente
      Row headerRow = sheet.createRow(0);
      for (int i = 0; i < headersEstadoVotacion.length; i++) {
        createCell(headerRow, i, headersEstadoVotacion[i], headerStyle);
      }

      // Llenar las filas de datos
      int rowIdx = 1;
      for (ReporteEstadoVotacionItem item : datos) {
        Row row = sheet.createRow(rowIdx++);
        crearFilaReporteEstadoVotacion(row, item, bodyStyle);
      }

      // Auto-ajustar el tamaño de todas las columnas
      for (int i = 0; i < headersEstadoVotacion.length; i++) {
        sheet.autoSizeColumn(i);
      }

      workbook.write(out);

      Resource resource = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));

      log.info("Reporte Excel para Estado de Votación generado exitosamente.");
      return new ArchivoReporte("reporte_estado_votacion.xlsx",
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", out.size(),
          resource);

    } catch (IOException e) {
      log.error("Error crítico al generar el reporte Excel de Estado de Votación.", e);
      throw new RuntimeException("Error al generar el reporte Excel", e);
    }
  }

  @Override
  public ArchivoReporte generarExcelReporteGeneralProyecto(List<ReporteGeneralProyectoItem> datos) {
    log.info("Iniciando generación de reporte Excel para General de Proyectos con {} filas.",
        datos.size());

    String[] headersGeneralProyecto = {"Fecha Programación", "Nro. Expediente", "Ponente",
        "Estado Voto", "Tipo Parte", "Recurrente", "Sentido", "Fallo", "Anotación",
        "Responsable Proyecto", "Estado Proyecto", "Fecha Envío", "Pendientes de Validar"};

    try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {

      Sheet sheet = workbook.createSheet("Reporte General Proyectos");

      CellStyle headerStyle = createHeaderStyle(workbook);
      CellStyle bodyStyle = createBodyStyle(workbook);

      Row headerRow = sheet.createRow(0);
      for (int i = 0; i < headersGeneralProyecto.length; i++) {
        createCell(headerRow, i, headersGeneralProyecto[i], headerStyle);
      }

      int rowIdx = 1;
      for (ReporteGeneralProyectoItem item : datos) {
        Row row = sheet.createRow(rowIdx++);
        crearFilaReporteGeneralProyecto(row, item, bodyStyle);
      }

      for (int i = 0; i < headersGeneralProyecto.length; i++) {
        sheet.autoSizeColumn(i);
      }

      workbook.write(out);

      Resource resource = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));

      log.info("Reporte Excel para General de Proyectos generado exitosamente.");
      return new ArchivoReporte("reporte_general_proyectos.xlsx",
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", out.size(),
          resource);
    } catch (IOException e) {
      log.error("Error crítico al generar el reporte Excel General de Proyectos.", e);
      throw new RuntimeException("Error al generar el reporte Excel", e);
    }
  }

  private void crearFilaReporteEstadoVotacion(Row row, ReporteEstadoVotacionItem item,
      CellStyle style) {
    String fechaProgramacion =
        item.fechaProgramacion() != null ? DATE_TIME_FORMATTER.format(item.fechaProgramacion())
            : "";
    createCell(row, 0, fechaProgramacion, style);
    createCell(row, 1, item.numeroExpediente(), style);
    createCell(row, 2, item.ponente(), style);
    createCell(row, 3, item.nivelInstruccion(), style);
    createCell(row, 4, item.estadoVoto(), style);
    createCell(row, 5, item.tipoParte(), style);
    createCell(row, 6, item.recurrente(), style);
    createCell(row, 7, item.sentido(), style);
    createCell(row, 8, item.fallo(), style);
  }

  private void crearFilaReporteGeneralProyecto(Row row, ReporteGeneralProyectoItem item,
      CellStyle style) {
    String fechaProgramacion =
        item.fechaProgramacion() != null ? DATE_TIME_FORMATTER.format(item.fechaProgramacion())
            : "";
    String fechaEnvio =
        item.fechaEnvio() != null ? DATE_TIME_FORMATTER.format(item.fechaEnvio()) : "";

    createCell(row, 0, fechaProgramacion, style);
    createCell(row, 1, item.numeroExpediente(), style);
    createCell(row, 2, item.ponente(), style);
    createCell(row, 3, item.estadoVoto(), style);
    createCell(row, 4, item.tipoParte(), style);
    createCell(row, 5, item.recurrente(), style);
    createCell(row, 6, item.sentido(), style);
    createCell(row, 7, item.fallo(), style);
    createCell(row, 8, item.anotacion(), style);
    createCell(row, 9, item.responsableProyecto(), style);
    createCell(row, 10, item.estadoProyecto(), style);
    createCell(row, 11, fechaEnvio, style);
    createCell(row, 12, item.magistradosPendientesValidar(), style);
  }

  private void createCell(Row row, int column, String value, CellStyle style) {
    Cell cell = row.createCell(column);
    cell.setCellValue(value != null ? value : "");
    cell.setCellStyle(style);
  }

  private CellStyle createHeaderStyle(Workbook workbook) {
    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setBold(true);
    font.setFontHeightInPoints((short) 12);
    style.setFont(font);
    style.setAlignment(HorizontalAlignment.CENTER);
    return style;
  }

  private CellStyle createBodyStyle(Workbook workbook) {
    CellStyle style = workbook.createCellStyle();
    style.setAlignment(HorizontalAlignment.LEFT);
    return style;
  }
  
}
