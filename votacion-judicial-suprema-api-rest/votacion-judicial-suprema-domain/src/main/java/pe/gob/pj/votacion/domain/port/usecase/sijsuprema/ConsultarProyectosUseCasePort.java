package pe.gob.pj.votacion.domain.port.usecase.sijsuprema;

import java.util.List;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.ProyectoVoto;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteEstadoVotacionItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteGeneralProyectoItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ContarProyectosPendientesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosPendientesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosRelacionadosQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosValidadosQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteEstadoVotacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteGeneralProyectoQuery;

public interface ConsultarProyectosUseCasePort {

  List<ProyectoVoto> listarPendientes(PeticionServicios peticion,
      ListarProyectosPendientesQuery query);

  List<ProyectoVoto> listarRelacionados(PeticionServicios peticion,
      ListarProyectosRelacionadosQuery query);

  Integer contarPendientes(PeticionServicios peticion, ContarProyectosPendientesQuery query);

  List<ProyectoVoto> listarValidados(PeticionServicios peticion,
      ListarProyectosValidadosQuery query);

  List<ReporteEstadoVotacionItem> generarReporteEstadoVotacion(PeticionServicios peticion,
      ReporteEstadoVotacionQuery query);

  List<ReporteGeneralProyectoItem> generarReporteGeneralProyecto(PeticionServicios peticion,
      ReporteGeneralProyectoQuery query);

}
