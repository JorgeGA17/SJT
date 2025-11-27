package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.domain.model.sijsuprema.ImpedidoProgramacion;
import pe.gob.pj.votacion.domain.model.sijsuprema.MagistradoDiscordiaProgramacion;
import pe.gob.pj.votacion.domain.model.sijsuprema.MagistradoProgramacion;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ProgramacionInstanciaVocalEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ProgramacionInstanciaVocalEntityPk;

public interface ProgramacionInstanciaVocalRepository
        extends JpaRepository<ProgramacionInstanciaVocalEntity, ProgramacionInstanciaVocalEntityPk> {

    @Query("""
               SELECT new pe.gob.pj.votacion.domain.model.sijsuprema.MagistradoProgramacion(
                  u.id.codigoUsuario,
                  u.detalleNombre,
                  u.iniciales,
                  CASE WHEN EXISTS (
                     SELECT 1
                     FROM ConformacionGrupoEntity cg
                     WHERE cg.id.codigoProgramacion = :cProgramacion
                       AND cg.id.numeroGrupo        = :nGrupo
                       AND cg.id.numeroSecuencia    = :nSecuencia
                       AND cg.id.numeroConformacion = :nConformacion
                       AND cg.codigoUsuarioVocal = piv.codigoUsuarioVocal
                  ) THEN 'S' ELSE 'N' END,
                  CASE WHEN piv.lIndNivelInstruccion = '5' THEN 'S' ELSE 'N' END,
                  piv.lIndNivelInstruccion
               )
               FROM ProgramacionInstanciaVocalEntity piv
               INNER JOIN UsuarioEntity u ON  piv.codigoUsuarioVocal=u.id.codigoUsuario
               WHERE piv.id.codigoProgramacion = :cProgramacion
                 AND piv.activo = 'S'
                 AND piv.adicional = 'N'
               ORDER BY piv.lIndNivelInstruccion DESC
            """)
    List<MagistradoProgramacion> findMagistradosByProgramacion(
            @Param("cProgramacion") String cProgramacion, @Param("nGrupo") Integer nGrupo,
            @Param("nSecuencia") Integer nSecuencia, @Param("nConformacion") Integer nConformacion);

    @Query("""
               SELECT new pe.gob.pj.votacion.domain.model.sijsuprema.ImpedidoProgramacion(
                  u.iniciales,
                  CASE WHEN EXISTS (
                     SELECT 1
                     FROM ExpedienteVocalEntity ev
                     WHERE ev.instanciaExpediente.id.codigoDistrito = :cDistrito
                       AND ev.instanciaExpediente.id.codigoProvincia = :cProvincia
                       AND ev.instanciaExpediente.id.codigoInstancia = :cInstancia
                       AND ev.instanciaExpediente.id.numeroUnico = :nUnico
                       AND ev.instanciaExpediente.id.numeroIncidente = :nIncidente
                       AND ev.instanciaExpediente.id.fechaIngreso = :fIngreso
                       AND ev.codigoUsuario = piv.codigoUsuarioVocal
                       AND ev.impedimento = 'S'
                  ) THEN 'S' ELSE '' END
               )
               FROM ProgramacionInstanciaVocalEntity piv
               INNER JOIN UsuarioEntity u ON  piv.codigoUsuarioVocal=u.id.codigoUsuario
               WHERE piv.id.codigoProgramacion = :cProgramacion
                 AND piv.activo = 'S'
                 AND piv.adicional = 'N'
               ORDER BY piv.lIndNivelInstruccion DESC, u.id.codigoUsuario ASC
            """)
    List<ImpedidoProgramacion> findImpedidosByProgramacion(
            @Param("cProgramacion") String cProgramacion, @Param("cDistrito") String cDistrito,
            @Param("cProvincia") String cProvincia, @Param("cInstancia") String cInstancia,
            @Param("nUnico") BigDecimal nUnico, @Param("nIncidente") Integer nIncidente,
            @Param("fIngreso") ZonedDateTime fIngreso);


    @Query("""
               SELECT new pe.gob.pj.votacion.domain.model.sijsuprema.MagistradoDiscordiaProgramacion(
                  piv.codigoUsuarioVocal,
                  u.detalleNombre,
                  u.id.codigoUsuario,
                  u.iniciales,
                  CASE WHEN mvd.codigoUsuarioDiscordia IS NULL THEN 'N' ELSE mvd.activo END,
                  mvd.id
               )
               FROM ProgramacionInstanciaVocalEntity piv
               INNER JOIN UsuarioEntity u ON  piv.codigoUsuarioVocal=u.id.codigoUsuario
               LEFT JOIN MovVotoDiscordiaEntity mvd ON u.id.codigoUsuario = mvd.codigoUsuarioDiscordia
                 AND mvd.numeroUnico = :nUnico
                 AND mvd.numeroIncidente = :nIncidente
                 AND mvd.numeroSentido = :nSentido
                 AND mvd.numeroSecuenciaParte = :nSecuenciaParte
                 AND mvd.numeroVotacion = :nVotacion
               WHERE piv.id.codigoProgramacion = :cProgramacion
                 AND piv.activo = 'S'
                 AND piv.adicional = 'N'
                 AND piv.codigoUsuarioVocal <> :cVocalPonente
               ORDER BY piv.lIndNivelInstruccion DESC
            """)
    List<MagistradoDiscordiaProgramacion> findMagistradosDiscordiaByProgramacion(
            @Param("cProgramacion") String cProgramacion, @Param("nUnico") BigDecimal nUnico,
            @Param("nIncidente") Integer nIncidente, @Param("nSecuenciaParte") Integer nSecuenciaParte,
            @Param("nSentido") Integer nSentido, @Param("nVotacion") Integer nVotacion,
            @Param("cVocalPonente") String cVocalPonente);


    @Query("""
               SELECT piv
               FROM  ProgramacionInstanciaVocalEntity piv
               WHERE piv.id.codigoProgramacion = :cProgramacion
               AND piv.activo = "S"
               AND piv.adicional = "N"
               AND piv.codigoUsuarioVocal = :cUsuario
   """)
    Optional<ProgramacionInstanciaVocalEntity> findProgramacionInstanciaVocal(
            @Param("cProgramacion") String cProgramacion,
            @Param("cUsuario") String cUsuario);

    @Query("""
               SELECT piv
               FROM  ProgramacionInstanciaVocalEntity piv
               WHERE piv.codigoUsuarioVocal = :cUsuario
               AND piv.id.codigoProgramacion = :cProgramacion
               AND piv.activo = :ultimo
               AND piv.adicional = :adicional
   """)
    Optional<ProgramacionInstanciaVocalEntity> findByUsuarioProgramacion(
            @Param("cUsuario") String cUsuario,
            @Param("cProgramacion") String cProgramacion,
            @Param("ultimo") String ultimo,
            @Param("adicional") String adicional);

    @Modifying
    @Query(value = """
        INSERT INTO expediente_vocal
        ( c_distrito, c_provincia, c_instancia, n_unico, n_incidente,
          f_ingreso, n_vocal, c_usuario, n_colegiado, n_item, l_ultimo,
          l_nivel_instruccion, c_tipo_vocal, f_aud, b_aud, c_aud_uid, n_aud_ip )
        SELECT
          :cDistrito, :cProvincia, :cInstancia,
          :nUnico, :nIncidente, :fIngreso,
          :llExpVocal0 + 1 + (
              SELECT COUNT(*)
              FROM programacion_instancia_vocal piv2
              WHERE piv2.c_programacion = :cProgramacion
                AND piv2.l_activo = 'S'
                AND piv2.l_adicional = 'N'
                AND NOT EXISTS (
                      SELECT 1
                      FROM expediente_vocal evx
                      WHERE evx.c_distrito  = :cDistrito
                        AND evx.c_provincia = :cProvincia
                        AND evx.c_instancia = :cInstancia
                        AND evx.n_unico     = :nUnico
                        AND evx.n_incidente = :nIncidente
                        AND evx.f_ingreso   = :fIngreso
                        AND evx.c_usuario   = piv2.c_usuario_vocal
                )
                AND (
                      piv2.l_ind_nivel_instruccion > piv.l_ind_nivel_instruccion
                   OR (piv2.l_ind_nivel_instruccion = piv.l_ind_nivel_instruccion
                       AND piv2.c_usuario_vocal < piv.c_usuario_vocal)
                )
          ),
          piv.c_usuario_vocal,
          sc.n_colegiado,
          sc.n_item,
          'S',
          piv.l_ind_nivel_instruccion,
          sc.c_tipo_vocal,
          :fAud, :bAud, :cAudUid, :nAudIp
        FROM programacion_instancia_vocal piv
        JOIN usuario u
          ON u.c_usuario = piv.c_usuario_vocal
        LEFT JOIN sala_colegiado_conformacion sc
               ON sc.c_usuario   = piv.c_usuario_vocal
              AND sc.l_activo    = 'S'
              AND sc.c_distrito  = :cDistrito
              AND sc.c_provincia = :cProvincia
              AND sc.c_instancia = :cInstancia
        WHERE piv.c_programacion = :cProgramacion
          AND piv.l_activo = 'S'
          AND piv.l_adicional = 'N'
          AND piv.c_usuario_vocal IS NOT NULL
          AND NOT EXISTS (
                SELECT 1
                FROM expediente_vocal ev
                WHERE ev.c_distrito  = :cDistrito
                  AND ev.c_provincia = :cProvincia
                  AND ev.c_instancia = :cInstancia
                  AND ev.n_unico     = :nUnico
                  AND ev.n_incidente = :nIncidente
                  AND ev.f_ingreso   = :fIngreso
                  AND ev.c_usuario   = piv.c_usuario_vocal
          )
        """, nativeQuery = true)
    int insertarExpedienteVocal(
            @Param("cDistrito") String cDistrito,
            @Param("cProvincia") String cProvincia,
            @Param("cInstancia") String cInstancia,
            @Param("nUnico") BigDecimal nUnico,
            @Param("nIncidente") Integer nIncidente,
            @Param("fIngreso") LocalDateTime fIngreso,
            @Param("llExpVocal0") Integer llExpVocal0,
            @Param("cProgramacion") String cProgramacion,
            @Param("fAud") ZonedDateTime fAud,
            @Param("bAud") String bAud,
            @Param("cAudUid") String cAudUid,
            @Param("nAudIp") String nAudIp
    );

    @Query("""
               SELECT piv
               FROM  ProgramacionInstanciaVocalEntity piv
               WHERE piv.id.codigoProgramacion = :cProgramacion
               AND piv.activo = :activo
               AND piv.adicional = :adicional
   """)
    List<ProgramacionInstanciaVocalEntity> findByProgramacionActivos(
            @Param("cProgramacion") String cProgramacion,
            @Param("activo") String activo,
            @Param("adicional") String adicional);

}
