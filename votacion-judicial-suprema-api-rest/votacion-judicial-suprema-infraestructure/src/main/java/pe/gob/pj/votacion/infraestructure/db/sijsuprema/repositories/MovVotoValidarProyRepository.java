package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.MovVotoValidarProyEntity;

import java.util.List;
import java.util.Optional;

public interface MovVotoValidarProyRepository
    extends JpaRepository<MovVotoValidarProyEntity, Integer> {

  @Query("""
        SELECT mv
        FROM MovVotoValidarProyEntity mv
        WHERE mv.proyecto.nId = :idProyecto
        AND mv.usuarioVal = :usuarioVal
      """)
  Optional<MovVotoValidarProyEntity> findByUsuarioVal(Integer idProyecto, String usuarioVal);

  @Query("""
        SELECT mv
        FROM MovVotoValidarProyEntity mv
        WHERE mv.proyecto.nId = :idProyecto
        AND mv.activo = 'S'
        AND mv.numeroValidado = :numeroValidado
      """)
  List<MovVotoValidarProyEntity> findByValidado(@Param(value = "idProyecto") Integer idProyecto,
      @Param(value = "numeroValidado") Integer numeroValidado);

}
