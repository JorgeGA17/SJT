package pe.gob.pj.votacion.domain.port.usecase.sijsuprema;

import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.Registro;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarVotoCommand;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ValidarDatosQuery;

public interface RegistrarVotoUseCasePort {

  Registro validarDatos(PeticionServicios peticion, ValidarDatosQuery query);

  void registrarVoto(PeticionServicios peticion, RegistrarVotoCommand command);

  void autoguardado(PeticionServicios peticion, RegistrarVotoCommand command);

}
