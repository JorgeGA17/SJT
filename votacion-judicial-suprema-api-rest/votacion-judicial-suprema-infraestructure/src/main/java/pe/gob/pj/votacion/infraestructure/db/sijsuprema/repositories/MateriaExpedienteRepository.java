package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.domain.model.sijsuprema.MateriaProgramacion;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.MateriaExpedienteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.MateriaExpedienteEntityPk;

import java.math.BigDecimal;
import java.util.List;

public interface MateriaExpedienteRepository
        extends JpaRepository<MateriaExpedienteEntity, MateriaExpedienteEntityPk> {

    @Query("""
         SELECT new pe.gob.pj.votacion.domain.model.sijsuprema.MateriaProgramacion(
            me.materiaMaestroEntity.cMateria,
            SUBSTRING(mm.xDescMateria, 1, 128)
         )
         FROM MateriaExpedienteEntity me
         JOIN me.materiaMaestroEntity mm
         WHERE me.id.nUnico = :nUnico
           AND me.id.nIncidente = :nIncidente
           AND me.activo = 'S'
         ORDER BY me.lPrimero DESC, me.id.nMateria ASC
      """)
    List<MateriaProgramacion> findMateriasByProgramacion(
            @Param("cProgramacion") String cProgramacion, @Param("nUnico") BigDecimal nUnico,
            @Param("nIncidente") Integer nIncidente);


}
