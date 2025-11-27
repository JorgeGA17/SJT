package pe.gob.pj.votacion.infraestructure.rest.responses;

public record MagistradoProgramacionResponse(
    String codigoUsuarioVocal,
    String nombres,
    String iniciales,
    String ponente,
    String presidente,
    String nivelInstruccion
    ) {

}
