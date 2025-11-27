package pe.gob.pj.votacion.infraestructure.rest.responses;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CasacionRelacionadaResponse(

    LocalDateTime fechaProgramacion,
    String numeroExpediente,
    String descripcionSentido,
    String descripcionFallo,
    String descripcionPrograma,
    String codigoDistrito,
    String codigoProvincia,
    String instancia,
    BigDecimal numeroUnico,
    Integer numeroIncidente,
    LocalDateTime fechaIngreso,
    String codigoProgramacion,
    Integer numeroGrupo,
    Integer numeroSecuencia,
    Integer numeroConformacion,
    String flagDiscordia,
    Integer codigoSentido,
    String codigoFallo,
    Integer numeroVotacion) {

}
