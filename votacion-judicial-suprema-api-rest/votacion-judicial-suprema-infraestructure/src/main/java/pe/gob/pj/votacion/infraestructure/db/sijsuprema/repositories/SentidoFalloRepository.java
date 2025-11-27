package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.domain.model.sijsuprema.Sentido;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.SentidoFalloEntity;

import java.util.List;
import java.util.Optional;

public interface SentidoFalloRepository extends JpaRepository<SentidoFalloEntity, String> {

    @Query("""
        SELECT new pe.gob.pj.votacion.domain.model.sijsuprema.Sentido(sf.codigoSentido, sf.descripcionSentido)
        FROM SentidoFalloEntity sf
        WHERE sf.codigoSentido IN ('01','02','03')
        """)
    List<Sentido> listarSentidos(@Param(value = "codigoDistrito") String codigoDistrito,
                                 @Param(value = "codigoProvincia") String codigoProvincia,
                                 @Param(value = "codigoInstancia") String codigoInstancia);

    Optional<SentidoFalloEntity> findByCodigoSentido(String codigoSentido);
}
