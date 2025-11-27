package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteSentidoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteSentidoEntityPk;

public interface ExpedienteSentidoRepository extends JpaRepository<ExpedienteSentidoEntity, ExpedienteSentidoEntityPk> {

    @Query(value = """
            SELECT es
            FROM ExpedienteSentidoEntity es
            WHERE es.id.numeroUnico = :numeroUnico
                AND es.id.numeroIncidente = :numeroIncidente
                AND es.distrito = :codigoDistrito
                AND es.provincia = :codigoProvincia
                AND es.instancia = :codigoInstancia
                AND es.fechaIngreso = :fechaIngreso
                AND es.conformacionGrupoEntity.id.codigoProgramacion = :codigoProgramacion
                AND es.conformacionGrupoEntity.id.numeroGrupo = :numeroGrupoVoto
                AND es.conformacionGrupoEntity.id.numeroSecuencia = :numeroSecuenciaVoto
                AND es.conformacionGrupoEntity.id.numeroConformacion = :numeroConformacionVoto
    """)
    Optional<ExpedienteSentidoEntity> findExpedienteSentido(@Param("numeroUnico") BigDecimal numeroUnico,
                                                            @Param("numeroIncidente") Integer numeroIncidente,
                                                            @Param("codigoDistrito") String codigoDistrito,
                                                            @Param("codigoProvincia") String codigoProvincia,
                                                            @Param("codigoInstancia") String codigoInstancia,
                                                            @Param("fechaIngreso") ZonedDateTime fechaIngreso,
                                                            @Param("codigoProgramacion") String codigoProgramacion,
                                                            @Param("numeroGrupoVoto") Integer numeroGrupoVoto,
                                                            @Param("numeroSecuenciaVoto") Integer numeroSecuenciaVoto,
                                                            @Param("numeroConformacionVoto") Integer numeroConformacionVoto);

    List<ExpedienteSentidoEntity> findByIdNumeroUnicoAndIdNumeroIncidente(BigDecimal unico, Integer incidente);

    Optional<ExpedienteSentidoEntity> findByIdNumeroUnicoAndIdNumeroIncidenteAndIdNumeroSentido(
            BigDecimal unico, Integer incidente, Integer sentido);

    @Modifying
    @Query(value = """
            UPDATE ExpedienteSentidoEntity es
            SET es.ultimo = 'N',
            es.fechaAuditoria = :fAud,
            es.bitacoraAuditoria = :bAud,
            es.usuarioAuditoria = :cAudUid
            WHERE es.id.numeroUnico = :numeroUnico
            AND es.id.numeroIncidente = :numeroIncidente
            AND es.ultimo = 'S'
    """)
    Integer actualizarUltimo(@Param("numeroUnico") BigDecimal numeroUnico,
                         @Param("numeroIncidente") Integer numeroIncidente,
                         @Param("fAud") ZonedDateTime fAud,
                         @Param("bAud") String bAud,
                         @Param("cAudUid") String cAudUid);


    @Modifying
    @Query("""
        UPDATE ExpedienteSentidoEntity es
           SET es.ultimo = 'N',
               es.fechaAuditoria = :fechaAuditoria
         WHERE es.id.numeroUnico = :numeroUnico
           AND es.id.numeroIncidente = :numeroIncidente
    """)
    int resetearUltimo(
            @Param("numeroUnico") BigDecimal numeroUnico,
            @Param("numeroIncidente") Integer numeroIncidente,
            @Param("fechaAuditoria") ZonedDateTime fechaAuditoria
    );

}
