package pe.gob.pj.votacion.infraestructure.rest.responses;

public record EstadoProyectoResponse(
        Integer id,
        String descripcion,
        String activo,
        String icono) {
}