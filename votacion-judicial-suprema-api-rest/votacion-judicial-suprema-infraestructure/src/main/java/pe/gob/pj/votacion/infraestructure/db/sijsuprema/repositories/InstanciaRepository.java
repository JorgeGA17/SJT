package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.InstanciaEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.InstanciaEntityPk;

public interface InstanciaRepository extends JpaRepository<InstanciaEntity, InstanciaEntityPk> {

}
