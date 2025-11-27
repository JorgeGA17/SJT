package pe.gob.pj.votacion.domain.port.usecase.sijsuprema;

import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.Registro;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarValidacionCommand;

public interface RegistrarProyectoUseCasePort {
  void registrarValidacion(PeticionServicios peticion, RegistrarValidacionCommand command);
}
