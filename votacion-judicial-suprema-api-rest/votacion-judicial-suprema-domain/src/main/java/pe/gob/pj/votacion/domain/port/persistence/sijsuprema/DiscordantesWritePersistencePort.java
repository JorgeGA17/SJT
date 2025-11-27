package pe.gob.pj.votacion.domain.port.persistence.sijsuprema;

import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarVotoCommand;

public interface DiscordantesWritePersistencePort {
    void registrarDiscordantes(String cuo, RegistrarVotoCommand command);


}
