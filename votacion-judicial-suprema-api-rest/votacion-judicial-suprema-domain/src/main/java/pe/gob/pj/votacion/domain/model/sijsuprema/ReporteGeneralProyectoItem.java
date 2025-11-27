package pe.gob.pj.votacion.domain.model.sijsuprema;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record ReporteGeneralProyectoItem(
    ZonedDateTime fechaProgramacion,
    String ponente,
    String estadoVoto,
    String tipoParte,
    String sentido,
    String fallo,
    String anotacion,
    String responsableProyecto,
    String estadoProyecto,
    ZonedDateTime fechaEnvio,
    String magistradosPendientesValidar,
    
    Integer idProyecto,
    BigDecimal rawExpSala,
    String rawLetra,
    String rawAnioSala,
    String rawApePaterno,
    String rawApeMaterno,
    String rawNombres,
    String rawTipoPersona) {

  public ReporteGeneralProyectoItem(ZonedDateTime fechaProgramacion, String ponente,
      String estadoVoto, String tipoParte, String sentido, String fallo, String anotacion,
      String responsableProyecto, String estadoProyecto, ZonedDateTime fechaEnvio,
      Integer idProyecto, BigDecimal rawExpSala, String rawLetra, String rawAnioSala,
      String rawApePaterno, String rawApeMaterno, String rawNombres, String rawTipoPersona) {
    this(fechaProgramacion, ponente, estadoVoto, tipoParte, sentido, fallo, anotacion,
        responsableProyecto, estadoProyecto, fechaEnvio, "", idProyecto, rawExpSala, rawLetra,
        rawAnioSala, rawApePaterno, rawApeMaterno, rawNombres, rawTipoPersona);
  }

  public String numeroExpediente() {
    if (rawExpSala == null || rawAnioSala == null)
      return "";
    String expSalaFormateado = String.format("%05d", rawExpSala.intValue());
    String letraFinal = (rawLetra == null) ? "" : rawLetra.trim();
    return expSalaFormateado + letraFinal + "-" + rawAnioSala;
  }

  public String recurrente() {
    String paterno = (rawApePaterno == null) ? "" : rawApePaterno.trim();
    String materno = (rawApeMaterno == null) ? "" : rawApeMaterno.trim();
    String nombres = (rawNombres == null) ? "" : rawNombres.trim();
    String tipoPersona = (rawTipoPersona == null) ? "X" : rawTipoPersona.trim();
    StringBuilder nombreCompletoBuilder = new StringBuilder();
    nombreCompletoBuilder.append(paterno);
    if ("N".equals(tipoPersona)) {
      nombreCompletoBuilder.append(" ").append(materno).append(", ");
    } else {
      nombreCompletoBuilder.append(" ");
    }
    nombreCompletoBuilder.append(nombres);
    String nombreCompleto = nombreCompletoBuilder.toString();
    return nombreCompleto.length() > 250 ? nombreCompleto.substring(0, 250) : nombreCompleto;
  }
    
}