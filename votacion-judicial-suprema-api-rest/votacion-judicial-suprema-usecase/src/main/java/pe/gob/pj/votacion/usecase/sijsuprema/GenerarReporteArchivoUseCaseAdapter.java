package pe.gob.pj.votacion.usecase.sijsuprema;

import java.sql.SQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.report.ArchivoReporte;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteEstadoVotacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteGeneralProyectoQuery;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.ProyectosReadPersistencePort;
import pe.gob.pj.votacion.domain.port.report.GeneradorExcelPort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.GenerarReporteArchivoUseCasePort;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GenerarReporteArchivoUseCaseAdapter implements GenerarReporteArchivoUseCasePort {

  ProyectosReadPersistencePort proyectosReadPersistencePort;
  GeneradorExcelPort generadorExcelPort;

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public ArchivoReporte generarReporteEstadoVotacion(PeticionServicios peticion,
      ReporteEstadoVotacionQuery query, String tipoReporte) {
    var data = proyectosReadPersistencePort.generarReporteEstadoVotacion(peticion.getCuo(), query);
    if ("excel".equalsIgnoreCase(tipoReporte)) {
      return generadorExcelPort.generarExcelReporteEstadoVotacion(data);
    }
    return null;
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public ArchivoReporte generarReporteGeneralProyecto(PeticionServicios peticion,
      ReporteGeneralProyectoQuery query, String tipoReporte) {
    var data = proyectosReadPersistencePort.generarReporteGeneralProyecto(peticion.getCuo(), query);
    if ("excel".equalsIgnoreCase(tipoReporte)) {
      return generadorExcelPort.generarExcelReporteGeneralProyecto(data);
    }
    return null;
  }

}
