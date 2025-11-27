package pe.gob.pj.votacion.domain.port.persistence.sijsuprema;

import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarVotoCommand;

public interface JurisprudenciaWritePersistencePort {
    void registrarJurisprudencia(String cuo, RegistrarVotoCommand command);

}