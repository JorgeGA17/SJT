package pe.gob.pj.votacion.domain.model.sijsuprema;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

public record Casacion(
        String codigoEstado,
        String nombreEstado,
        String codigoProgramacion,
        String codigoDitrito,
        String codigoProvincia,
        String codigoInstancia,
        String nombreInstancia,
        Integer numeroProgramacion,
        Integer numeroGrupo,
        Integer numeroSecuencia,
        Integer numeroConformacion,
        BigDecimal numeroUnico,
        Integer numeroIncidente,
        ZonedDateTime fechaIngreso,
        ZonedDateTime fechaProgramacion,
        BigDecimal numeroExpedienteSala,
        String codigoLetra,
        String numeroAnioSala,
        String codigoMotivoIngreso,
        String nombreMotivoIngreso,
        String codigoProcedencia,
        String nombreProcedencia,
        Integer numeroOrden,
        ZonedDateTime fechaEstado,
        String codigoArea,
        Integer numeroSentido,
        Integer numeroVotacion,
        
        String vocalPonente,
        String descripcionTipoProgramaAudienciaOrgano,
        String abreviaturaTipoProgramaAudienciaOrgano,
        String descripcionProceso,
        String descripcionActoProcesalJuzgado,
        Integer numeroFojasJuzgado,
        String descripcionActoProcesalSala,
        Integer numeroFojasSala,
        Integer codigoFalloSala,
        String descripcionFalloSala,
        Integer codigoFalloJuzgado,
        String descripcionFalloJuzgado) {
  
  public String numeroRecurso() {
    long expediente = numeroExpedienteSala != null ? numeroExpedienteSala.longValue() : 0L;
    String letra = codigoLetra != null ? codigoLetra.trim() : "";
    String anio = numeroAnioSala != null ? numeroAnioSala : "0";
    return String.format("%05d%s-%s", expediente, letra, anio);
  }

  public String descripcionActoProcesalJuzgadoAdicional() {
    String desc = Optional.ofNullable(descripcionActoProcesalJuzgado).map(String::trim).orElse("");
    String fojas = Optional.ofNullable(numeroFojasJuzgado).map(Object::toString).orElse("0");
    return desc + " FJS. " + fojas;
  }

  public String descripcionActoProcesalSalaAdicional() {
    String desc = Optional.ofNullable(descripcionActoProcesalSala).map(String::trim).orElse("");
    String fojas = Optional.ofNullable(numeroFojasSala).map(Object::toString).orElse("0");
    return desc + " FJS. " + fojas;
  }

}
