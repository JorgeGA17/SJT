package pe.gob.pj.votacion.domain.model.sijsuprema;

public record ParteProgramacion(
        Integer numeroSecuencia,
        String flagTipoParte,
        String nombreParte,
        String descripcion,
        Integer numeroFojas,
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