package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.domain.model.sijsuprema.ApuntesProgramacion;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteVotacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteVotacionEntityPk;

public interface ExpedienteVotacionRepository
    extends JpaRepository<ExpedienteVotacionEntity, ExpedienteVotacionEntityPk> {

  @Query("""
      SELECT new pe.gob.pj.votacion.domain.model.sijsuprema.ApuntesProgramacion(
          ev.apuntes
      )
      FROM ExpedienteVotacionEntity ev
      WHERE ev.id.numeroUnico = :nUnico
        AND ev.id.numeroIncidente = :nIncidente
        AND ev.id.numeroSentido = :nSentido
        AND ev.usuario = :cUsuarioVocal
      """)
  ApuntesProgramacion findApuntesByProgramacion(@Param("nUnico") BigDecimal nUnico,
      @Param("nIncidente") Integer nIncidente, @Param("nSentido") Integer nSentido,
      @Param("cUsuarioVocal") String cUsuarioVocal);


  @Query("""
              SELECT COALESCE(MAX(ev.id.numeroVotacion), 0)
              FROM ExpedienteVotacionEntity ev
              WHERE ev.id.numeroUnico = :numeroUnico
              AND ev.id.numeroIncidente = :numeroIncidente
              AND ev.id.numeroSentido = :numeroSentido
      """)
  Optional<Integer> findMaxNumeroVotacion(@Param("numeroUnico") BigDecimal numeroUnico,
      @Param("numeroIncidente") Integer numeroIncidente,
      @Param("numeroSentido") Integer numeroSentido);

  Optional<ExpedienteVotacionEntity> findByIdNumeroUnicoAndIdNumeroIncidenteAndIdNumeroSentidoAndUsuario(
      BigDecimal nUnico, Integer nIncidente, Integer nSentido, String cUsuario);

  boolean existsByIdNumeroUnicoAndIdNumeroIncidenteAndIdNumeroSentidoAndUsuario(BigDecimal nUnico,
      Integer nIncidente, Integer nSentido, String cUsuario);

  List<ExpedienteVotacionEntity> findByIdNumeroUnicoAndIdNumeroIncidenteAndIdNumeroSentidoAndUltimoAndPonenteNot(
      BigDecimal numeroUnico, Integer numeroIncidente, Integer numeroSentido, String ultimo,
      String ponente);

  List<ExpedienteVotacionEntity> findByIdNumeroUnicoAndIdNumeroIncidenteAndIdNumeroSentido(
      BigDecimal nUnico, Integer nIncidente, Integer nSentido);

  boolean existsByIdNumeroUnicoAndIdNumeroIncidenteAndIdNumeroSentidoAndUltimoAndPonenteAndUsuario(
      BigDecimal numeroUnico, Integer numeroIncidente, Integer numeroSentido, String ultimo,
      String ponente, String usuario);

  @Query(value = """
      SELECT ev.usuario
      FROM ExpedienteVotacionEntity ev
      WHERE ev.id.numeroUnico = :numeroUnico
      AND ev.id.numeroIncidente = :numeroIncidente
      AND ev.id.numeroSentido = :numeroSentido
      AND ev.ultimo = 'S'
      AND ev.ponente <> 'S'
      AND NOT EXISTS (SELECT 1
                      FROM MovVotoDiscordiaEntity md
                      WHERE md.numeroUnico = ev.id.numeroUnico
                      AND md.numeroIncidente = ev.id.numeroIncidente
                      AND md.numeroSentido = ev.id.numeroSentido
                      AND md.codigoUsuarioDiscordia = ev.usuario
                      AND md.activo = 'S')
      """)
  List<String> findUsuariosNoPonentesSinDiscordia(@Param("numeroUnico") BigDecimal numeroUnico,
      @Param("numeroIncidente") Integer nIncidente, @Param("numeroSentido") Integer nSentido);

}
