package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteSentidoVotacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteSentidoVotacionEntityPk;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpedienteSentidoVotacionRepository extends JpaRepository<ExpedienteSentidoVotacionEntity, ExpedienteSentidoVotacionEntityPk> {


    @Query("""
        SELECT COALESCE(MAX(ev.id.numeroVotacion), 0)
        FROM ExpedienteSentidoVotacionEntity ev
        WHERE ev.id.numeroUnico = :numeroUnico
        AND ev.id.numeroIncidente = :numeroIncidente
        AND ev.id.numeroSentido = :numeroSentido
    """)
    Integer maxVotacion(@Param("numeroUnico") BigDecimal numeroUnico,
                        @Param("numeroIncidente") Integer numeroIncidente,
                        @Param("numeroSentido") Integer numeroSentido);


    @Query("""
        SELECT esv
        FROM ExpedienteSentidoVotacionEntity esv
        WHERE esv.id.numeroUnico = :numeroUnico
        AND esv.id.numeroIncidente = :numeroIncidente
        AND esv.id.numeroSentido = :numeroSentido
        AND esv.estado = :codigoEstado
    """)
    List<ExpedienteSentidoVotacionEntity> getExpedienteSentidoVotacion(@Param("numeroUnico") BigDecimal numeroUnico,
                                                                       @Param("numeroIncidente") Integer numeroIncidente,
                                                                       @Param("numeroSentido") Integer numeroSentido,
                                                                       @Param("codigoEstado") String codigoEstado);


    @Modifying
    @Query("""
        UPDATE ExpedienteSentidoVotacionEntity esv
        SET esv.ultimo = 'N', esv.fechaAuditoria  =:fAud, esv.bitacoraAuditoria=:bAud, esv.usuarioAuditoria =:cAudUid
        WHERE esv.id.numeroUnico = :nUnico
        AND esv.id.numeroIncidente = :nIncidente
        AND esv.id.numeroSentido = :nSentido
        AND esv.ultimo = 'S'
        """)
    int updateUltimo(@Param("nUnico") BigDecimal nUnico,
                         @Param("nIncidente") Integer nIncidente,
                         @Param("nSentido") Integer nSentido,
                         @Param("fAud") LocalDateTime fAud,
                         @Param("bAud") String bAud,
                         @Param("cAudUid") String cAudUid);
}
