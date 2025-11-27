package pe.gob.pj.votacion.domain.model.sijsuprema;

public record ProyectoValidado(
    Integer idProyecto,
    String codUsuarioValidado, 
    Integer nValidado,
    String observacion, 
    String iniciales) {

}
