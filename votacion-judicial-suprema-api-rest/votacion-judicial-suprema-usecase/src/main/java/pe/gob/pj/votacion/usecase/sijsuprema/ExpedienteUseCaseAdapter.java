package pe.gob.pj.votacion.usecase.sijsuprema;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.Casacion;
import pe.gob.pj.votacion.domain.model.sijsuprema.CasacionRelacionada;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesRelacionadasQuery;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.ExpedienteReadPersistencePort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.ExpedienteUseCasePort;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ExpedienteUseCaseAdapter implements ExpedienteUseCasePort, Serializable {

  static final long serialVersionUID = 1L;

  ExpedienteReadPersistencePort expedienteReadPersistencePort;

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<Casacion> listarCasaciones(PeticionServicios peticion, ListarCasacionesQuery query) {
    return expedienteReadPersistencePort.listarCasaciones(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<CasacionRelacionada> listarCasacionesRelacionadas(PeticionServicios peticion,
      ListarCasacionesRelacionadasQuery query) {
    return expedienteReadPersistencePort.listarCasacionesRelacionadas(peticion.getCuo(), query);
  }

}
