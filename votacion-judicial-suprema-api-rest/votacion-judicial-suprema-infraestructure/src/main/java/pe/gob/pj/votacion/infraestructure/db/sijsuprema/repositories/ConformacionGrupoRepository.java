package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ConformacionGrupoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ConformacionGrupoEntityPk;

public interface ConformacionGrupoRepository
    extends JpaRepository<ConformacionGrupoEntity, ConformacionGrupoEntityPk> {

  @Query(value = """
              SELECT cg
              FROM ConformacionGrupoEntity cg
              WHERE cg.id.codigoProgramacion = :cProgramacion
                  AND cg.id.numeroGrupo        = :nGrupo
                  AND cg.id.numeroSecuencia    = :nSecuencia
                  AND cg.id.numeroConformacion = :nConformacion
      """)
  List<ConformacionGrupoEntity> listarPonentes(@Param(value = "cProgramacion") String cProgramacion,
      @Param(value = "nGrupo") Integer nGrupo, @Param(value = "nSecuencia") Integer nSecuencia,
      @Param(value = "nConformacion") Integer nConformacion);


  @Modifying
  @Query("""
      UPDATE ConformacionGrupoEntity cg
      SET cg.lReprogramado = "N", 
      cg.fechaAuditoria  =:fAud, 
      cg.bitacoraAuditoria=:bAud, 
      cg.usuarioAuditoria =:cAudUid
      WHERE 
      cg.id.codigoProgramacion = :cProgramacion
      AND cg.id.numeroGrupo = :nGrupo
      AND cg.id.numeroSecuencia = :nSecuencia
      AND cg.id.numeroConformacion = :nConformacion
      """)
  int marcarNoReprogramado(@Param("cProgramacion") String cProgramacion,
      @Param("nGrupo") Integer nGrupo, @Param("nSecuencia") Integer nSecuencia,
      @Param("nConformacion") Integer nConformacion, @Param("fAud") ZonedDateTime fAud,
      @Param("bAud") String bAud, @Param("cAudUid") String cAudUid);
}
