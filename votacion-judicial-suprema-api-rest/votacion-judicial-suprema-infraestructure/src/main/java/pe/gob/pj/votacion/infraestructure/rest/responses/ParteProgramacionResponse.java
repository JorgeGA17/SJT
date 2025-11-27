package pe.gob.pj.votacion.infraestructure.rest.responses;

public record ParteProgramacionResponse(
        Integer numeroSecuencia,
        String flagTipoParte,
        String nombreParte,
        String descripcionParte,
        Integer numeroOrden,
        Integer numeroSuborden,
        String flagRecurrente,
        String abreviatura,
        Integer numeroSentido,
        Integer numeroSecuenciaParte,
        Integer numeroVotacion,
        String codigoSentido,
        Integer codigoFallo,
        String observacion,
        String flagDiscordia,
        String descripcionSentido,
        String descripcionFallo,
        String flagPublicado
) {

}
