package pe.gob.pj.votacion.domain.port.usecase.sijsuprema;

import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarEnvioProyectoCommand;

public interface RegistrarEnvioVotoProyectoUseCasePort {

  void registrarEnvioVoto(PeticionServicios peticion, RegistrarEnvioProyectoCommand command);
  
}
