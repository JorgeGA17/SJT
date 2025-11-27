package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.util.List;
import java.util.Map;
import pe.gob.pj.votacion.domain.model.sijsuprema.ProyectoValidado;
import pe.gob.pj.votacion.domain.model.sijsuprema.ProyectoVoto;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteEstadoVotacionItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteGeneralProyectoItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ContarProyectosPendientesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosPendientesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosRelacionadosQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosValidadosQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteEstadoVotacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteGeneralProyectoQuery;

public interface MovVotoProyectoDslRepository {

  List<ProyectoVoto> listarPendientes(String cuo, ListarProyectosPendientesQuery query);

  List<ProyectoValidado> listarValidacionesProyecto(String cuo, List<Integer> idsProyectos);

  List<ProyectoVoto> listarRelacionados(String cuo, ListarProyectosRelacionadosQuery query);

  Integer contarPendientes(String cuo, ContarProyectosPendientesQuery query);

  List<ProyectoVoto> listarValidados(String cuo, ListarProyectosValidadosQuery query);

  List<ReporteEstadoVotacionItem> findReporteEstadoVotacion(String cuo,
      ReporteEstadoVotacionQuery query);

  List<ReporteGeneralProyectoItem> findReporteGeneralProyecto(String cuo,
      ReporteGeneralProyectoQuery query);

  Map<Integer, String> findMagistradosPendientes();

}
