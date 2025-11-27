package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.MovVotoDiscordiaEntity;

public interface MovVotoDiscordiaRepository extends JpaRepository<MovVotoDiscordiaEntity, Integer> {

  @Modifying
  @Query("""
          UPDATE MovVotoDiscordiaEntity m
             SET m.activo = :activo,
                 m.fechaAuditoria = :fechaAuditoria,
                 m.bitacoraAuditoria = :bitacoraAuditoria,
                 m.usuarioAuditoria = :codigoAuditoriaUsuario,
                 m.usuarioRedAuditoria = :codigoAuditoriaUsuarioRed,
                 m.pcAuditoria = :codigoAuditoriaPc,
                 m.ipAuditoria = :numeroAuditoriaIp,
                 m.macAuditoria = :codigoAuditoriaMacAddr
           WHERE m.id = :id
      """)
  int actualizarVotoDiscordiaById(@Param("activo") String activo,
      @Param("fechaAuditoria") ZonedDateTime fechaAuditoria,
      @Param("bitacoraAuditoria") String bitacoraAuditoria,
      @Param("codigoAuditoriaUsuario") String codigoAuditoriaUsuario,
      @Param("codigoAuditoriaUsuarioRed") String codigoAuditoriaUsuarioRed,
      @Param("codigoAuditoriaPc") String codigoAuditoriaPc,
      @Param("numeroAuditoriaIp") String numeroAuditoriaIp,
      @Param("codigoAuditoriaMacAddr") String codigoAuditoriaMacAddr, @Param("id") Integer id);

  @Modifying
  @Query("""
          UPDATE MovVotoDiscordiaEntity m
             SET m.activo = :activo,
                 m.fechaAuditoria = :fechaAuditoria,
                 m.bitacoraAuditoria = :bitacoraAuditoria,
                 m.usuarioAuditoria = :codigoAuditoriaUsuario,
                 m.usuarioRedAuditoria = :codigoAuditoriaUsuarioRed,
                 m.pcAuditoria = :codigoAuditoriaPc,
                 m.ipAuditoria = :numeroAuditoriaIp,
                 m.macAuditoria = :codigoAuditoriaMacAddr
           WHERE m.numeroUnico = :numeroUnico
             AND m.numeroIncidente = :numeroIncidente
             AND m.numeroSentido = :numeroSentido
             AND m.numeroSecuenciaParte = :numeroSecuenciaParte
             AND m.numeroVotacion = :numeroVotacion
             AND m.codigoUsuarioDiscordia = :codigoUsuarioDiscordia
      """)
  int actualizarVotoDiscordiaByUnico(@Param("activo") String activo,
      @Param("fechaAuditoria") ZonedDateTime fechaAuditoria,
      @Param("bitacoraAuditoria") String bitacoraAuditoria,
      @Param("codigoAuditoriaUsuario") String codigoAuditoriaUsuario,
      @Param("codigoAuditoriaUsuarioRed") String codigoAuditoriaUsuarioRed,
      @Param("codigoAuditoriaPc") String codigoAuditoriaPc,
      @Param("numeroAuditoriaIp") String numeroAuditoriaIp,
      @Param("codigoAuditoriaMacAddr") String codigoAuditoriaMacAddr,
      @Param("numeroUnico") BigDecimal numeroUnico,
      @Param("numeroIncidente") Integer numeroIncidente,
      @Param("numeroSentido") Integer numeroSentido,
      @Param("numeroSecuenciaParte") Integer numeroSecuenciaParte,
      @Param("numeroVotacion") Integer numeroVotacion,
      @Param("codigoUsuarioDiscordia") String codigoUsuarioDiscordia);

  List<MovVotoDiscordiaEntity> findByNumeroUnicoAndNumeroIncidenteAndNumeroSentidoAndNumeroVotacionAndCodigoUsuarioDiscordiaNot(
      BigDecimal numeroUnico, Integer numeroIncidente, Integer numeroSentido,
      Integer numeroVotacion, String usuarioDiscordia);


  @Query("""
    SELECT m FROM MovVotoDiscordiaEntity m
      WHERE m.numeroUnico = :numeroUnico
      AND m.numeroIncidente = :numeroIncidente
      AND m.numeroSentido = :numeroSentido
      AND m.numeroSecuenciaParte = :numeroSecuenciaParte
      AND m.numeroVotacion = :numeroVotacion
      AND m.codigoUsuarioDiscordia = :codigoUsuarioDiscordia
    """)
  Optional<MovVotoDiscordiaEntity> findByFiltros(
      @Param("numeroUnico") BigDecimal numeroUnico,
      @Param("numeroIncidente") Integer numeroIncidente,
      @Param("numeroSentido") Integer numeroSentido,
      @Param("numeroSecuenciaParte") Integer numeroSecuenciaParte,
      @Param("numeroVotacion") Integer numeroVotacion,
      @Param("codigoUsuarioDiscordia") String codigoUsuarioDiscordia
  );



}
