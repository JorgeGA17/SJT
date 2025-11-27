package pe.gob.pj.votacion.domain.port.persistence.sijsuprema;

import pe.gob.pj.votacion.domain.model.sijsuprema.Casacion;
import pe.gob.pj.votacion.domain.model.sijsuprema.CasacionRelacionada;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesRelacionadasQuery;

import java.util.List;

public interface ExpedienteReadPersistencePort {

  List<Casacion> listarCasaciones(String cuo, ListarCasacionesQuery query);
  List<CasacionRelacionada> listarCasacionesRelacionadas(String cuo, ListarCasacionesRelacionadasQuery query);
}
