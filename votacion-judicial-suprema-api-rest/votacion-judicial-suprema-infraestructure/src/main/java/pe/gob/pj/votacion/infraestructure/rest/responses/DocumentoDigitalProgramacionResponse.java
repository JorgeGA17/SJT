package pe.gob.pj.votacion.infraestructure.rest.responses;

public record DocumentoDigitalProgramacionResponse(
    Integer nDocumento,
    String ltipoDocumento,
    String desTipoDocumento,
    String nombreArchivo,
    String descripcion,
    String rutaArchivo,
    String ipFtp,
    String claveFtp,
    String usuarioFtp
) {

}