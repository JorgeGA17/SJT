package pe.gob.pj.votacion.infraestructure.db.auditoriageneral.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.pj.votacion.infraestructure.db.auditoriageneral.entities.MovAuditoriaAplicativosEntity;

public interface MovAuditoriaAplicativosRespository
    extends JpaRepository<MovAuditoriaAplicativosEntity, Integer> {

}
