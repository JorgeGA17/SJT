package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteVotacionParteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteVotacionParteEntityPk;

public interface ExpedienteVotacionParteRepository
    extends JpaRepository<ExpedienteVotacionParteEntity, ExpedienteVotacionParteEntityPk> {

  @Modifying
  @Query("""
          UPDATE ExpedienteVotacionParteEntity evp
             SET evp.sentidoFallo.codigoSentido = :codigoSentido,
                 evp.cFallo = :codigoFallo,
                 evp.xAnotacion = :anotacion,
                 evp.fVotacion = :fechaVotacion,
                 evp.lDiscordia = :flagDiscordia,
                 evp.lPublicado = 'N',
                 evp.lUltimo = 'S',
                 evp.fechaAuditoria = :fechaAuditoria,
                 evp.bitacoraAuditoria = :bitacoraAuditoria,
                 evp.usuarioAuditoria = :usuarioAuditoria,
                 evp.ipAuditoria = :ipAuditoria
          WHERE evp.id.unico = :numeroUnico
             AND evp.id.nIncidente = :numeroIncidente
             AND evp.id.nSentido = :numeroSentido
             AND evp.id.nSecuenciaParte = :numeroSecuenciaParte
             AND evp.id.nVotacion = :numeroVotacion
      """)
  int updateExpedienteVotacionParte(@Param("numeroUnico") BigDecimal numeroUnico,
      @Param("numeroIncidente") Integer numeroIncidente,
      @Param("numeroSentido") Integer numeroSentido,
      @Param("numeroSecuenciaParte") Integer numeroSecuenciaParte,
      @Param("numeroVotacion") Integer numeroVotacion, @Param("codigoSentido") String codigoSentido,
      @Param("codigoFallo") Integer codigoFallo, @Param("anotacion") String anotacion,
      @Param("fechaVotacion") ZonedDateTime fechaVotacion,
      @Param("flagDiscordia") String flagDiscordia,
      @Param("fechaAuditoria") ZonedDateTime fechaAuditoria,
      @Param("bitacoraAuditoria") String bitacoraAuditoria,
      @Param("usuarioAuditoria") String usuarioAuditoria, @Param("ipAuditoria") String ipAuditoria);
}
