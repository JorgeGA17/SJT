package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.gob.pj.votacion.domain.model.sijsuprema.EstadoProyecto;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.MaeVotoProyEstadoEntity;

import java.util.List;

public interface MaeVotoProyEstadoRepository
    extends JpaRepository<MaeVotoProyEstadoEntity, Integer> {

  @Query("""
          SELECT new pe.gob.pj.votacion.domain.model.sijsuprema.EstadoProyecto(
          mvpe.id, mvpe.descripcion, mvpe.activo, mvpe.icono)
          FROM MaeVotoProyEstadoEntity mvpe
      """)
  List<EstadoProyecto> listarEstadosProyecto();
}
