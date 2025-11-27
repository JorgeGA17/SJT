package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteVocalEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteVocalEntityPk;

public interface ExpedienteVocalRepository
    extends JpaRepository<ExpedienteVocalEntity, ExpedienteVocalEntityPk> {

  @Query("""
          SELECT COALESCE(MAX(ev.id.numeroVocal), 0)
          FROM ExpedienteVocalEntity ev
          WHERE ev.id.codigoDistrito  = :distrito
          AND ev.id.codigoProvincia = :provincia
          AND ev.id.codigoInstancia = :instancia
          AND ev.id.numeroUnico = :numeroUnico
          AND ev.id.numeroIncidente = :numeroIncidente
          AND ev.id.fechaIngreso = :fechaIngreso
      """)
  Integer findMaxNumeroVocal(@Param("distrito") String distrito,
      @Param("provincia") String provincia, @Param("instancia") String instancia,
      @Param("numeroUnico") BigDecimal numeroUnico,
      @Param("numeroIncidente") Integer numeroIncidente,
      @Param("fechaIngreso") ZonedDateTime fechaIngreso);


  @Modifying
  @Query("""
      UPDATE ExpedienteVocalEntity ev
         SET ev.lUltimo = 'N',
             ev.fechaAuditoria = :fechaAud,
             ev.bitacoraAuditoria = :bitacoraAud,
             ev.usuarioAuditoria = :usuarioAud,
             ev.ipAuditoria = :ipAud
       WHERE ev.id.codigoDistrito = :distrito
         AND ev.id.codigoProvincia = :provincia
         AND ev.id.codigoInstancia = :instancia
         AND ev.id.numeroUnico = :numeroUnico
         AND ev.id.numeroIncidente = :numeroIncidente
         AND ev.id.fechaIngreso = :fechaIngreso
         AND ev.lUltimo = 'S'
         AND NOT EXISTS (
              SELECT 1
                FROM ProgramacionInstanciaVocalEntity piv
               WHERE piv.id.codigoProgramacion = :programacion
                 AND piv.activo = 'S'
                 AND piv.adicional = 'N'
                 AND piv.codigoUsuarioVocal = ev.codigoUsuario
          )
      """)
  int updateUltimo(@Param("distrito") String distrito, @Param("provincia") String provincia,
      @Param("instancia") String instancia, @Param("numeroUnico") BigDecimal numeroUnico,
      @Param("numeroIncidente") Integer numeroIncidente,
      @Param("fechaIngreso") ZonedDateTime fechaIngreso, @Param("programacion") String programacion,
      @Param("fechaAud") ZonedDateTime fechaAud, @Param("bitacoraAud") String bitacoraAud,
      @Param("usuarioAud") String usuarioAud, @Param("ipAud") String ipAud);


  @Query("""
          SELECT ev
          FROM ExpedienteVocalEntity ev
          WHERE ev.id.codigoDistrito  = :distrito
          AND ev.id.codigoProvincia = :provincia
          AND ev.id.codigoInstancia = :instancia
          AND ev.id.numeroUnico = :numeroUnico
          AND ev.id.numeroIncidente = :numeroIncidente
          AND ev.id.fechaIngreso = :fechaIngreso
      """)
  List<ExpedienteVocalEntity> findByFiltros(@Param("distrito") String distrito,
      @Param("provincia") String provincia, @Param("instancia") String instancia,
      @Param("numeroUnico") BigDecimal numeroUnico,
      @Param("numeroIncidente") Integer numeroIncidente,
      @Param("fechaIngreso") ZonedDateTime fechaIngreso);

  Optional<ExpedienteVocalEntity> findByIdNumeroUnicoAndIdNumeroIncidenteAndCodigoUsuario(
      BigDecimal nUnico, Integer nIncidente, String cUsuario);
}
