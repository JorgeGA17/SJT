package pe.gob.pj.votacion.domain.port.persistence.sijsuprema;

import pe.gob.pj.votacion.domain.model.sijsuprema.Registro;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarVotoCommand;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ValidarDatosQuery;

public interface EstadoReadPersistencePort {

  Registro validarDatos(String cuo, ValidarDatosQuery query);

  void validarEstado(String cuo, RegistrarVotoCommand command);

}
