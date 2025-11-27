package pe.gob.pj.votacion.domain.port.usecase.sijsuprema;

import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.*;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.*;

import java.util.List;

public interface ConsultarMaestrasUseCasePort {


    List<Fallo> listarFallos(PeticionServicios peticion, ListarFallosQuery query);

    List<Sentido> listarSentidos(PeticionServicios peticion, ListarSentidosQuery query);

    List<Colegiado> listarColegiados(PeticionServicios peticion, ListarColegiadosQuery query);

    List<EstadoVotacion> listarEstadosVotacion(PeticionServicios peticion, ListarEstadosVotacionQuery query);

    List<EstadoProyecto> listarEstadosProyecto(PeticionServicios peticion, ListarEstadosProyectoQuery query);
}
