package pe.gob.pj.votacion.domain.port.persistence.sijsuprema;

import java.util.List;
import pe.gob.pj.votacion.domain.model.sijsuprema.ProyectoVoto;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteEstadoVotacionItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteGeneralProyectoItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ContarProyectosPendientesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosPendientesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosRelacionadosQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosValidadosQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteEstadoVotacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteGeneralProyectoQuery;

public interface ProyectosReadPersistencePort {

  List<ProyectoVoto> listarPendientes(String cuo, ListarProyectosPendientesQuery query);

  List<ProyectoVoto> listarRelacionados(String cuo, ListarProyectosRelacionadosQuery query);

  Integer contarPendientes(String cuo, ContarProyectosPendientesQuery query);

  List<ProyectoVoto> listarValidados(String cuo, ListarProyectosValidadosQuery query);

  List<ReporteEstadoVotacionItem> generarReporteEstadoVotacion(String cuo,
      ReporteEstadoVotacionQuery query);

  List<ReporteGeneralProyectoItem> generarReporteGeneralProyecto(String cuo,
      ReporteGeneralProyectoQuery query);
}
