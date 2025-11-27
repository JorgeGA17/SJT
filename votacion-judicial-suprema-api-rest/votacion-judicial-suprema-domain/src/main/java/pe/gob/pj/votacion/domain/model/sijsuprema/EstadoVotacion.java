package pe.gob.pj.votacion.domain.model.sijsuprema;

public record EstadoVotacion(String codigoEstado, String descripcion) {
    public EstadoVotacion {
        codigoEstado = codigoEstado != null ? codigoEstado.trim() : null;
        descripcion  = descripcion != null ? descripcion.trim() : null;
    }
}
