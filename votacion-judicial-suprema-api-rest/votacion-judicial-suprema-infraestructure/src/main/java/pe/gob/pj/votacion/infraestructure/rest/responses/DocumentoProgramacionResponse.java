package pe.gob.pj.votacion.infraestructure.rest.responses;

public record DocumentoProgramacionResponse(
        String nombreEntidad,
        String nombreRecurso,
        String uuid,
        Integer idJurisprudencia
) {

}