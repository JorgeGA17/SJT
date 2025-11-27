package pe.gob.pj.votacion.domain.port.persistence.sijsuprema;

import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarValidacionCommand;

public interface ValidacionWritePersistencePort {
  void registrarValidacion(String cuo, RegistrarValidacionCommand command);
}
