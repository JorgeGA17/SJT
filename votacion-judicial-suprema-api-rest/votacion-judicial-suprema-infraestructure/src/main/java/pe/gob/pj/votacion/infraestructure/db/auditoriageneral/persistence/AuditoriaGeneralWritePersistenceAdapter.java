package pe.gob.pj.votacion.infraestructure.db.auditoriageneral.persistence;

import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.domain.model.auditoriageneral.AuditoriaAplicativos;
import pe.gob.pj.votacion.domain.port.persistence.auditoriageneral.AuditoriaGeneralReadPersistencePort;
import pe.gob.pj.votacion.infraestructure.db.auditoriageneral.entities.MovAuditoriaAplicativosEntity;
import pe.gob.pj.votacion.infraestructure.db.auditoriageneral.repositories.MovAuditoriaAplicativosRespository;
import pe.gob.pj.votacion.infraestructure.mappers.AuditoriaGeneralMapper;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuditoriaGeneralWritePersistenceAdapter implements AuditoriaGeneralReadPersistencePort {

  AuditoriaGeneralMapper auditoriaGeneralMapper;
  MovAuditoriaAplicativosRespository movAuditoriaAplicativosRespository;

  @Override
  public void crear(AuditoriaAplicativos auditoriaAplicativos) throws Exception {
    MovAuditoriaAplicativosEntity mov =
        auditoriaGeneralMapper.toMovAuditoriaAplicativos(auditoriaAplicativos);
    movAuditoriaAplicativosRespository.save(mov);
  }

}
