package pe.gob.pj.votacion.domain.model.sijsuprema;

import java.math.BigDecimal;

public record DocumentoDigitalProgramacion(
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