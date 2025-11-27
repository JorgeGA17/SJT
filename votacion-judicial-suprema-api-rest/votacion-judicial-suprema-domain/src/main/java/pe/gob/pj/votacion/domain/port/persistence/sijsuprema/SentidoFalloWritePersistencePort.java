package pe.gob.pj.votacion.domain.port.persistence.sijsuprema;

import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarVotoCommand;

public interface SentidoFalloWritePersistencePort {

  void registrarSentidoFallo(String cuo, RegistrarVotoCommand command);
}
