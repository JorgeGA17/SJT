package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.gob.pj.votacion.domain.model.sijsuprema.EstadoVotacion;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.EstadoMaestroEntity;

import java.util.List;

public interface EstadoMaestroRepository extends JpaRepository<EstadoMaestroEntity, String> {

    @Query("""
        SELECT new pe.gob.pj.votacion.domain.model.sijsuprema.EstadoVotacion(em.codigoEstado, em.descripcion)
        FROM EstadoMaestroEntity em
        WHERE em.codigoEstado IN ('900','FAR','495','FAJ')
          AND em.activo = 'S'
        ORDER BY em.descripcion DESC
    """)
    List<EstadoVotacion> listarEstadosVotacion();
}
