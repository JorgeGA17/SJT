package pe.gob.pj.votacion.infraestructure.rest.responses;

public record DocumentoDigitalResponse(
    String tipoDocumento,
    String nombreArchivo,
    byte[] contenido
) {

}