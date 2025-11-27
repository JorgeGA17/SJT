package pe.gob.pj.votacion.domain.port.usecase.sijsuprema;

import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.Casacion;
import pe.gob.pj.votacion.domain.model.sijsuprema.CasacionRelacionada;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesRelacionadasQuery;

import java.util.List;

public interface ExpedienteUseCasePort {

  List<Casacion> listarCasaciones(PeticionServicios peticion, ListarCasacionesQuery query);

  List<CasacionRelacionada> listarCasacionesRelacionadas(PeticionServicios peticion, ListarCasacionesRelacionadasQuery query);
}
