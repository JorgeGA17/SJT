package pe.gob.pj.votacion.infraestructure.rest.responses;

public record MagistradoDiscordiaProgramacionResponse(
        String codigoUsuarioVocal,
        String nombres,
        String codigoUsuario,
        String iniciales,
        String flagDiscordia,
        Integer idVotoDiscordia
) {

}