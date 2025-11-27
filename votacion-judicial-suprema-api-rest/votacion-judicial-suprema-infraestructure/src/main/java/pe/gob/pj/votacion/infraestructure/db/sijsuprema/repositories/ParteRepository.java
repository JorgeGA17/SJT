package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.domain.model.sijsuprema.ParteProgramacion;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ParteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ParteEntityPk;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ParteRepository extends JpaRepository<ParteEntity, ParteEntityPk> {

    @Query("""
    SELECT new pe.gob.pj.votacion.domain.model.sijsuprema.ParteProgramacion(
        pa.id.nSecuencia,
        pa.tipoParte,
        SUBSTRING(
            CONCAT(
                TRIM(pa.apePaterno),
                CASE WHEN pa.tipoPersona = 'N'
                    THEN CONCAT(' ', TRIM(pa.apeMaterno), ', ')
                    ELSE ' '
                END,
                TRIM(pa.nombres)
            ), 1, 250
        ),
        CASE WHEN :lDiscordia = 'S'
            THEN CASE WHEN (pp.informeHechoDiscordia = 'S' OR pp.informeHecho = 'S')
                THEN ' - Informe de Hecho'
                    ELSE '' END
                ELSE CASE WHEN pp.informeHecho = 'S'
                    THEN ' - Informe de Hecho'
                 ELSE ''
            END
        END,
        ei.nFojasRecurso,
        tp.nOrden,
        pp.suborden,
        pa.recurrente,
        CASE WHEN tp.xAbreviatura IS NOT NULL THEN tp.xAbreviatura ELSE tp.id.lTipoParte END,
        evp.id.nSentido,
        evp.id.nSecuenciaParte,
        evp.id.nVotacion,
        evp.sentidoFallo.codigoSentido,
        evp.cFallo,
        evp.xAnotacion,
        evp.lDiscordia,
        sf.descripcionSentido,
        f.descripcion,
        evp.lPublicado
    )
    FROM ParteProgramacionEntity pp
    JOIN pp.parte pa
    JOIN pa.tipoParteEntity tp
    LEFT JOIN ExpedienteVotacionParteEntity evp
           ON evp.id.unico = pa.id.nUnico
          AND evp.id.nIncidente = pa.id.nIncidente
          AND evp.id.nSecuenciaParte = pa.id.nSecuencia
          AND evp.id.nSentido = :nSentido
          AND evp.id.nVotacion = :nVotacion
    LEFT JOIN ExpedienteImpugnacionEntity ei
           ON ei.id.nUnico = pa.id.nUnico
          AND ei.id.nIncidente = pa.id.nIncidente
          AND ei.id.nSecuencia = pa.id.nSecuencia
          AND ei.lActivo = 'S'
    LEFT JOIN evp.sentidoFallo sf
    LEFT JOIN FalloEntity f ON f.codigoFallo = evp.cFallo
    WHERE pp.id.nUnico = :nUnico
      AND pp.id.nIncidente = :nIncidente
      AND pp.id.cProgramacion = :cProgramacion
      AND pp.publicadoTablilla = 'S'
      AND pa.activo = 'S'
      AND (pa.apersonado IS NULL OR pa.apersonado = 'N')
""")
    List<ParteProgramacion> findPartesByProgramacion(
            @Param("cProgramacion") String cProgramacion, @Param("nUnico") BigDecimal nUnico,
            @Param("nIncidente") Integer nIncidente, @Param("lDiscordia") String lDiscordia,
            @Param("nSentido") Integer nSentido, @Param("nVotacion") Integer nVotacion);


    @Query("""
        SELECT p
        FROM ParteEntity p
        JOIN ExpedienteEntity e
          ON e.id.nUnico = p.id.nUnico
         AND e.id.nIncidente = p.id.nIncidente
        JOIN InstanciaExpedienteEntity i
          ON i.id.numeroUnico = e.id.nUnico
         AND i.id.numeroIncidente = e.id.nIncidente
        WHERE i.lUltimo = 'S'
          AND e.especialidadEntity.codigoEspecialidad <> 'PE'
          AND e.id.nUnico = :numeroUnico
          AND e.id.nIncidente = :numeroIncidente
          AND p.recurrente = 'S'
    """)
    Optional<ParteEntity> findParteValida(
            @Param("numeroUnico") BigDecimal numeroUnico,
            @Param("numeroIncidente") Integer numeroIncidente,
            @Param("numeroSecuenciaParte") Integer numeroSecuenciaParte
    );

}
