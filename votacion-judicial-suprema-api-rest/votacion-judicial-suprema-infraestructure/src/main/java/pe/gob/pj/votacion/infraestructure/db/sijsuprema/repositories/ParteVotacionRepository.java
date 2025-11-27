package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ParteVotacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ParteVotacionEntityPk;

import java.math.BigDecimal;
import java.util.Optional;

public interface ParteVotacionRepository extends JpaRepository<ParteVotacionEntity, ParteVotacionEntityPk> {

    @Query("""
        SELECT pv
        FROM ParteVotacionEntity pv
        WHERE pv.id.unico = :numeroUnico
          AND pv.id.incidente = :numeroIncidente
          AND pv.id.secuenciaParte = :numeroSecuenciaParte
    """)
    Optional<ParteVotacionEntity> findParteVotacion(
            @Param("numeroUnico") BigDecimal numeroUnico,
            @Param("numeroIncidente") Integer numeroIncidente,
            @Param("numeroSecuenciaParte") Integer numeroSecuenciaParte
    );
}
