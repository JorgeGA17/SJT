package pe.gob.pj.votacion.infraestructure.rest.responses;

import java.time.LocalDateTime;

public record ReporteGeneralProyectoResponse(
    LocalDateTime fechaProgramacion,
    String ponente,
    String estadoVoto,
    String tipoParte,
    String sentido,
    String fallo,
    String anotacion,
    String responsableProyecto,
    String estadoProyecto,
    String fechaEnvio,
    String magistradosPendientesValidar,
    Integer idProyecto,
    String numeroExpediente,
    String recurrente
    ) {

}
