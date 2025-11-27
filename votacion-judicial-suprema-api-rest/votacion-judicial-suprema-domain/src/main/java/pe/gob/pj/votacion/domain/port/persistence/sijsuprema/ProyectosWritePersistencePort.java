package pe.gob.pj.votacion.domain.port.persistence.sijsuprema;

import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarEnvioProyectoCommand;

public interface ProyectosWritePersistencePort {

  void registrarEnvioVoto(PeticionServicios peticion, RegistrarEnvioProyectoCommand command);
  
}
