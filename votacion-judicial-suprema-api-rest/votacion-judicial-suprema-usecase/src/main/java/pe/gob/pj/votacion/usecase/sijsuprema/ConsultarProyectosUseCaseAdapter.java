package pe.gob.pj.votacion.usecase.sijsuprema;

import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.ProyectosReadPersistencePort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.ConsultarProyectosUseCasePort;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConsultarProyectosUseCaseAdapter implements ConsultarProyectosUseCasePort {

  ProyectosReadPersistencePort proyectosReadPersistencePort;

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<ProyectoVoto> listarPendientes(PeticionServicios peticion,
      ListarProyectosPendientesQuery query) {
    return proyectosReadPersistencePort.listarPendientes(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<ProyectoVoto> listarRelacionados(PeticionServicios peticion,
      ListarProyectosRelacionadosQuery query) {
    return proyectosReadPersistencePort.listarRelacionados(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public Integer contarPendientes(PeticionServicios peticion,
      ContarProyectosPendientesQuery query) {
    return proyectosReadPersistencePort.contarPendientes(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<ProyectoVoto> listarValidados(PeticionServicios peticion,
      ListarProyectosValidadosQuery query) {
    return proyectosReadPersistencePort.listarValidados(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<ReporteEstadoVotacionItem> generarReporteEstadoVotacion(PeticionServicios peticion,
      ReporteEstadoVotacionQuery query) {
    return proyectosReadPersistencePort.generarReporteEstadoVotacion(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<ReporteGeneralProyectoItem> generarReporteGeneralProyecto(PeticionServicios peticion,
      ReporteGeneralProyectoQuery query) {
    return proyectosReadPersistencePort.generarReporteGeneralProyecto(peticion.getCuo(), query);
  }


}
