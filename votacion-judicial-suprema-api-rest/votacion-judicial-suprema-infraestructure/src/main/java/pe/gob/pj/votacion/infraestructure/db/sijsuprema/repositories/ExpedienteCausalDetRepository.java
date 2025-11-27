package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteCausalDetEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteCausalDetEntityPk;

import java.math.BigDecimal;
import java.util.List;

public interface ExpedienteCausalDetRepository
    extends JpaRepository<ExpedienteCausalDetEntity, ExpedienteCausalDetEntityPk> {


  @Query(value = """
      SELECT
          ecp.n_secuencia AS nSecuencia,
          0 AS nSecuenciaDet,
          'A' AS lGrupo,
          SUBSTRING(
            RTRIM(ISNULL(p.x_ape_paterno,'')) +
            CASE WHEN LTRIM(RTRIM(ISNULL(p.c_tipo_persona,'X'))) = 'N'
                   THEN ' ' + ISNULL(p.x_ape_materno,'') + ' '
                 ELSE ' '
            END +
            RTRIM(ISNULL(p.x_nombres,'')), 1, 250
          )
      FROM expediente_causal_parte ecp
      JOIN parte p ON p.n_unico = ecp.n_unico
                   AND p.n_incidente = ecp.n_incidente
                   AND p.n_secuencia = ecp.n_secuencia_parte
      JOIN tipo_parte tp ON tp.l_tipo_parte = p.l_tipo_parte
                        AND tp.c_especialidad = p.c_especialidad
      WHERE ecp.n_unico = :nUnico
        AND ecp.n_incidente = :nIncidente
        AND ecp.c_programacion = :cProgramacion
        AND ecp.l_activo = 'S'
        AND EXISTS (
            SELECT 1
            FROM expediente_causal_det ecdX
            WHERE ecdX.n_unico = ecp.n_unico
              AND ecdX.n_incidente = ecp.n_incidente
              AND ecdX.c_programacion = ecp.c_programacion
              AND ecdX.n_secuencia = ecp.n_secuencia
              AND ecdX.l_activo = 'S'
        )
      UNION ALL
      SELECT
          D.n_secuencia AS nSecuencia,
          D.n_secuencia_det AS nSecuenciaDet,
          'B' AS lGrupo,
          CASE
            WHEN RIGHT(RTRIM(D.base_desc), 1) <> ':' AND LEN(LTRIM(RTRIM(D.base_desc))) > 0
              THEN LTRIM(RTRIM(D.base_desc)) + ' :'
            ELSE LTRIM(RTRIM(D.base_desc))
          END
      FROM (
        SELECT
          ecd.n_secuencia,
          ecd.n_secuencia_det,
          ( crm.x_descripcion
              + CASE WHEN ISNULL(LEN(RTRIM(LTRIM(ecd.x_articulo))), 0) = 0
                       THEN ''
                     ELSE '. '
                END
              + ISNULL(ecd.x_articulo, '')
          ) AS base_desc
        FROM expediente_causal_det ecd
        JOIN causal_recurso_maestro crm ON crm.c_causal_recurso = ecd.c_causal_recurso
        WHERE ecd.n_unico = :nUnico
          AND ecd.n_incidente = :nIncidente
          AND ecd.c_programacion = :cProgramacion
          AND ecd.l_activo = 'S'
      ) D
      UNION ALL
      SELECT
          ecd.n_secuencia AS nSecuencia,
          ecd.n_secuencia_det AS nSecuenciaDet,
          'C' AS lGrupo,
          ecd.x_observacion
      FROM expediente_causal_det ecd
      WHERE ecd.n_unico = :nUnico
        AND ecd.n_incidente = :nIncidente
        AND ecd.c_programacion = :cProgramacion
        AND ecd.l_activo = 'S'
      ORDER BY nSecuencia, nSecuenciaDet, lGrupo
      """, nativeQuery = true)
  List<Object[]> findCausalesByProgramacion(@Param("cProgramacion") String cProgramacion,
      @Param("nUnico") BigDecimal nUnico, @Param("nIncidente") Integer nIncidente);

}
