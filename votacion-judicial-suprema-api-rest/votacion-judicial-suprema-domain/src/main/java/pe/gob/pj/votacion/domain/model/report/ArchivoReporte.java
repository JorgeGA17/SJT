package pe.gob.pj.votacion.domain.model.report;

import org.springframework.core.io.Resource;

public record ArchivoReporte(
    String nombreArchivo,
    String contentType,
    long contentLength,
    Resource resource) {

}
