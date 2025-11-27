package pe.gob.pj.votacion.infraestructure.db.sijsuprema.persistence;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import pe.gob.pj.votacion.domain.model.sijsuprema.Casacion;
import pe.gob.pj.votacion.domain.model.sijsuprema.CasacionRelacionada;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesRelacionadasQuery;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.ExpedienteReadPersistencePort;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ExpedienteRepository;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ExpedienteReadPersistenceAdapter implements ExpedienteReadPersistencePort {

  ExpedienteRepository expedienteRepository;

  @Override
  public List<Casacion> listarCasaciones(String cuo, ListarCasacionesQuery query) {
    return expedienteRepository.listarCasaciones(cuo, query);
  }

  @Override
  public List<CasacionRelacionada> listarCasacionesRelacionadas(String cuo,
      ListarCasacionesRelacionadasQuery query) {
    return expedienteRepository.listarCasacionesRelacionadas(cuo, query);
  }

}
