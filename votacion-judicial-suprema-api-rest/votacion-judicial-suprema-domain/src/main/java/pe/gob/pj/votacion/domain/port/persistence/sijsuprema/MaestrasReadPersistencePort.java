package pe.gob.pj.votacion.domain.port.persistence.sijsuprema;

import pe.gob.pj.votacion.domain.model.sijsuprema.*;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.*;

import java.util.List;

public interface MaestrasReadPersistencePort {

    List<Fallo> listarFallos(String cuo, ListarFallosQuery query);
    List<Sentido> listarSentidos(String cuo, ListarSentidosQuery query);
    List<Colegiado> listarColegiados(String cuo, ListarColegiadosQuery query);
    List<EstadoVotacion> listarEstadosVotacion(String cuo, ListarEstadosVotacionQuery query);
    List<EstadoProyecto> listarEstadosProyecto(String cuo, ListarEstadosProyectoQuery query);

}
