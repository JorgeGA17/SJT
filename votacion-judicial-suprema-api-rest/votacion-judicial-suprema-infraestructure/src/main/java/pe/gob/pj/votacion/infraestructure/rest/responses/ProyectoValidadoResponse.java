package pe.gob.pj.votacion.infraestructure.rest.responses;

public record ProyectoValidadoResponse(
    String codUsuarioValidado, 
    Integer nValidado,
    String observacion, 
    String iniciales) {

}
