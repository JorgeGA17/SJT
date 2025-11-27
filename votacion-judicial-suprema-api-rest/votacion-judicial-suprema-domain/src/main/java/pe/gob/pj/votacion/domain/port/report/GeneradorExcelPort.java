package pe.gob.pj.votacion.domain.port.report;

import java.util.List;
import pe.gob.pj.votacion.domain.model.report.ArchivoReporte;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteEstadoVotacionItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteGeneralProyectoItem;

public interface GeneradorExcelPort {

  ArchivoReporte generarExcelReporteEstadoVotacion(List<ReporteEstadoVotacionItem> datos);

  ArchivoReporte generarExcelReporteGeneralProyecto(List<ReporteGeneralProyectoItem> datos);

}
