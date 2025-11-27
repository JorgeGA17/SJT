package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteVotacionParteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteVotacionParteEntityPk;

public interface ExpedienteSentidoVotacionParteRepository
    extends JpaRepository<ExpedienteVotacionParteEntity, ExpedienteVotacionParteEntityPk> {

  @Modifying
  @Query("""
          UPDATE ExpedienteVotacionParteEntity evp
             SET evp.lUltimo   = 'N',
                 evp.fechaAuditoria      = :fechaAuditoria,
                 evp.bitacoraAuditoria      = :bAud,
                 evp.usuarioAuditoria   = :usuarioAuditoria,
                 evp.ipAuditoria    = :ipAuditoria
           WHERE evp.id.unico        = :numeroUnico
             AND evp.id.nIncidente    = :numeroIncidente
             AND evp.id.nSentido      = :numeroSentido
             AND evp.id.nSecuenciaParte = :numeroSecuenciaParte
             AND evp.id.nVotacion    <> :numeroVotacion
      """)
  int resetearUltimoOtrasVotaciones(@Param("numeroUnico") BigDecimal numeroUnico,
      @Param("numeroIncidente") Integer numeroIncidente,
      @Param("numeroSentido") Integer numeroSentido,
      @Param("numeroSecuenciaParte") Integer numeroSecuenciaParte,
      @Param("numeroVotacion") Integer numeroVotacion,
      @Param("fechaAuditoria") ZonedDateTime fechaAuditoria, @Param("bAud") String bAud,
      @Param("usuarioAuditoria") String usuarioAuditoria, @Param("ipAuditoria") String ipAuditoria);

  @Query("""
          SELECT e
          FROM ExpedienteVotacionParteEntity e
          WHERE e.id.unico = :numeroUnico
            AND e.id.nIncidente = :numeroIncidente
            AND e.id.nSentido = :numeroSentido
            AND e.id.nSecuenciaParte = :numeroSecuenciaParte
            AND e.id.nVotacion = :numeroVotacion
      """)
  Optional<ExpedienteVotacionParteEntity> findExpedienteVotacionParte(BigDecimal numeroUnico,
      Integer numeroIncidente, Integer numeroSentido, Integer numeroSecuenciaParte,
      Integer numeroVotacion);
}
