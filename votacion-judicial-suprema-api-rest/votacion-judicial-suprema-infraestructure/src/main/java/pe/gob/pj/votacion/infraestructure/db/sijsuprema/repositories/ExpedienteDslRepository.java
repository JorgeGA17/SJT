package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import pe.gob.pj.votacion.domain.model.sijsuprema.Casacion;
import pe.gob.pj.votacion.domain.model.sijsuprema.CasacionRelacionada;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesRelacionadasQuery;

import java.util.List;

public interface ExpedienteDslRepository {

  List<Casacion> listarCasaciones(String cuo, ListarCasacionesQuery query);

  List<CasacionRelacionada> listarCasacionesRelacionadas(String cuo, ListarCasacionesRelacionadasQuery query);

}
