package pe.gob.pj.votacion.infraestructure.rest.responses;

public record ReporteEstadoVotacionResponse(
    String fechaProgramacion,
    String ponente,
    String nivelInstruccion,
    String estadoVoto,
    String tipoParte,
    String sentido,
    String fallo,
    String numeroExpediente,
    String recurrente) {

}
