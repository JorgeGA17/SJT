package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ProgramacionInstanciaEntity;

public interface ExpedienteRepository extends JpaRepository<ProgramacionInstanciaEntity, String>, ExpedienteDslRepository {
}
