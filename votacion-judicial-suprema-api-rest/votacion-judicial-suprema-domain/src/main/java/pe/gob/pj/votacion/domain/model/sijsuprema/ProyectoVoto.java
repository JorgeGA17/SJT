package pe.gob.pj.votacion.domain.model.sijsuprema;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public record ProyectoVoto(
    BigDecimal numeroExpedienteSala,
    String codigoLetra,
    String numeroAnioSala,
    Integer idProyecto,
    ZonedDateTime fechaProgramacion,
    String codigoEstadoVotacion,
    String descripcionEstadoVotacion,
    String flagDiscordia,
    String usuarioResponsable,
    String iniciales,
    String flagPonente,
    Integer idEstadoProyecto,
    String descripcionEstadoProyecto,
    BigDecimal numeroUnico,
    Integer numeroIncidente,
    Integer numeroSentido,
    Integer numeroVotacion,
    String uuidAlfresco,
    String extension,
    Integer numeroEnvio,
    ZonedDateTime fechaEnvio,
    ZonedDateTime fechaIngreso,
    String codigoProgramacion,
    Integer numeroGrupo,
    Integer numeroSecuencia,
    Integer numeroConformacion,
    List<ProyectoValidado> validaciones) {

  public ProyectoVoto(BigDecimal numeroExpedienteSala, String codigoLetra, String numeroAnioSala,
                      Integer idProyecto, ZonedDateTime fechaProgramacion, String codigoEstadoVotacion,
                      String descripcionEstadoVotacion, String flagDiscordia, String usuarioResponsable,
                      String iniciales, String flagPonente, Integer idEstadoProyecto,
                      String descripcionEstadoProyecto, BigDecimal numeroUnico, Integer numeroIncidente,
                      Integer numeroSentido, Integer numeroVotacion, String uuidAlfresco, String extension,
                      Integer numeroEnvio, ZonedDateTime fechaEnvio, ZonedDateTime fechaIngreso,
                      String codigoProgramacion, Integer numeroGrupo, Integer numeroSecuencia,
                      Integer numeroConformacion) {

    this(numeroExpedienteSala, codigoLetra, numeroAnioSala, idProyecto, fechaProgramacion,
        codigoEstadoVotacion, descripcionEstadoVotacion, flagDiscordia, usuarioResponsable,
        iniciales, flagPonente, idEstadoProyecto, descripcionEstadoProyecto, numeroUnico,
        numeroIncidente, numeroSentido, numeroVotacion, uuidAlfresco, extension, numeroEnvio,
        fechaEnvio, fechaIngreso, codigoProgramacion, numeroGrupo, numeroSecuencia,
        numeroConformacion, Collections.emptyList());

  }

    public String numeroRecurso() {
    long expediente = numeroExpedienteSala != null ? numeroExpedienteSala.longValue() : 0L;
    String letra = codigoLetra != null ? codigoLetra.trim() : "";
    String anio = numeroAnioSala != null ? numeroAnioSala : "0";
    return String.format("%05d%s-%s", expediente, letra, anio);
  }

  public String flagDiscordia() {
    return "FAR".equals(this.codigoEstadoVotacion) ? "S" : "N";
  }

}
