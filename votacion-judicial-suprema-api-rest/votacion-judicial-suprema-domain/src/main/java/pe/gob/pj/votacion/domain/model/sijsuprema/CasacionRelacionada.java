package pe.gob.pj.votacion.domain.model.sijsuprema;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record CasacionRelacionada(

    ZonedDateTime fechaProgramacion,
    BigDecimal numeroExpedienteSala,
    String codigoLetra,
    String numeroAnioSala,
    String descripcionSentido,
    Integer codigoFallo,
    String descripcionFallo,
    String descripcionPrograma,
    String codigoDistrito,
    String codigoProvincia,
    String instancia,
    BigDecimal numeroUnico,
    Integer numeroIncidente,
    ZonedDateTime fechaIngreso,
    String codigoProgramacion,
    Integer numeroGrupo,
    Integer numeroSecuencia,
    Integer numeroConformacion,
    String flagDiscordia,
    Integer codigoSentido,
    Integer numeroVotacion) {


  public String numeroRecurso() {
    long expediente = numeroExpedienteSala != null ? numeroExpedienteSala.longValue() : 0L;
    String letra = codigoLetra != null ? codigoLetra.trim() : "";
    String anio = numeroAnioSala != null ? numeroAnioSala : "0";
    return String.format("%05d%s-%s", expediente, letra, anio);
  }

}
