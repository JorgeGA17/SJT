package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.PerfilEntity;

public interface PerfilRepository extends JpaRepository<PerfilEntity, String> {

}
