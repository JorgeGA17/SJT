package pe.gob.pj.votacion.usecase.sijsuprema;

import java.sql.SQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarValidacionCommand;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.ValidacionWritePersistencePort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.RegistrarProyectoUseCasePort;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RegistrarProyectoUseCaseAdapter implements RegistrarProyectoUseCasePort {

  ValidacionWritePersistencePort validacionWritePersistencePort;

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = false,
      rollbackFor = {Exception.class, SQLException.class})
  public void registrarValidacion(PeticionServicios peticion, RegistrarValidacionCommand command) {
    validacionWritePersistencePort.registrarValidacion(peticion.getCuo(), command);
  }
}
