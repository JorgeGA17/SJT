package pe.gob.pj.votacion.infraestructure.rest.responses;

public record CausalProgramacionResponse(
        Integer numeroSecuencia,
        Integer numeroSecuenciaDet,
        String flagGrupo,
        String detalle
) {
}