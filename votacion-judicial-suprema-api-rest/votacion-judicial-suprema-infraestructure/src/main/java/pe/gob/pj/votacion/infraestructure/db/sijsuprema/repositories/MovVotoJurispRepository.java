package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.domain.model.sijsuprema.DocumentoProgramacion;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.MovVotoJurispEntity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public interface MovVotoJurispRepository extends JpaRepository<MovVotoJurispEntity, Integer> {

    @Query("""
    SELECT new pe.gob.pj.votacion.domain.model.sijsuprema.DocumentoProgramacion(
        mvj.xEntidad,
        mvj.xRecurso,
        mvj.xUuid,
        mvj.id
    )
    FROM MovVotoJurispEntity mvj
    WHERE mvj.nUnico = :nUnico
      AND mvj.nIncidente = :nIncidente
      AND mvj.cProgramacion = :cProgramacion
      AND mvj.lActivo = 'S'
""")
    List<DocumentoProgramacion> findDocumentosByProgramacion(
            @Param("cProgramacion") String cProgramacion, @Param("nUnico") BigDecimal nUnico, @Param("nIncidente") Integer nIncidente);

  @Modifying
  @Query("""
        UPDATE MovVotoJurispEntity m
           SET m.lActivo  = COALESCE(:lActivo, m.lActivo),
               m.fechaAuditoria     = :fAud,
               m.bitacoraAuditoria     = :bAudU,
               m.usuarioAuditoria  = :cAudUid,
               m.ipAuditoria   = :nAudIp
         WHERE m.nUnico     = :nUnico
           AND m.nIncidente = :nIncidente
           AND (
                 (m.cProgramacion = :cProgramacion)
                 OR (m.cProgramacion IS NULL AND :cProgramacion IS NULL)
               )
           AND m.xUuid = :xUuid
        """)
  int actualizarMovVotoJurisp(
      @Param("lActivo") String lActivo,
      @Param("fAud") ZonedDateTime fAud,
      @Param("bAudU") String bAudU,
      @Param("cAudUid") String cAudUid,
      @Param("nAudIp") String nAudIp,
      @Param("nUnico") BigDecimal nUnico,
      @Param("nIncidente") Integer nIncidente,
      @Param("cProgramacion") String cProgramacion,
      @Param("xUuid") String xUuid
  );
}