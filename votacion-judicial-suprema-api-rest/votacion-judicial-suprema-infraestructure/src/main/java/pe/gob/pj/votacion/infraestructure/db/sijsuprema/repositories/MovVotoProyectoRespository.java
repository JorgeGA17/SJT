package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.MovVotoProyectoEntity;

public interface MovVotoProyectoRespository extends JpaRepository<MovVotoProyectoEntity, Integer> {
}
