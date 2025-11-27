package pe.gob.pj.votacion.infraestructure.rest.responses;

import java.math.BigDecimal;
import java.util.List;

public record ProyectoVotoResponse(
    Integer idProyecto,
    String numeroRecurso,
    String fechaProgramacion,
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
    String fechaEnvio,
    String fechaIngreso,
    String codigoProgramacion,
    Integer numeroGrupo,
    Integer numeroSecuencia,
    Integer numeroConformacion,
    List<ProyectoValidadoResponse> validaciones) {

}
