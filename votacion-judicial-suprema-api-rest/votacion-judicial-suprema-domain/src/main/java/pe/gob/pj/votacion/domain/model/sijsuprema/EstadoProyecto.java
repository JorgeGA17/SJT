package pe.gob.pj.votacion.domain.model.sijsuprema;

public record EstadoProyecto(Integer id, String descripcion, String activo, String icono) {
    public EstadoProyecto {
        descripcion  = descripcion != null ? descripcion.trim() : null;
        activo  = activo != null ? activo.trim() : null;
        icono  = icono != null ? icono.trim() : null;
    }
}
