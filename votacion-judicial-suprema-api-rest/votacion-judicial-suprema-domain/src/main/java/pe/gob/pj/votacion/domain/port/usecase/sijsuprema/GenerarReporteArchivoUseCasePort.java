package pe.gob.pj.votacion.domain.port.usecase.sijsuprema;

import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.report.ArchivoReporte;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteEstadoVotacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteGeneralProyectoQuery;

public interface GenerarReporteArchivoUseCasePort {

  ArchivoReporte generarReporteEstadoVotacion(PeticionServicios peticion,
      ReporteEstadoVotacionQuery query, String tipoReporte);

  ArchivoReporte generarReporteGeneralProyecto(PeticionServicios peticion,
      ReporteGeneralProyectoQuery query, String tipoReporte);

}
