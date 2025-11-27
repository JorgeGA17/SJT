package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.domain.model.sijsuprema.Colegiado;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.SalaColegiadoConformacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.SalaColegiadoConformacionEntityPk;

import java.util.List;
import java.util.Optional;

public interface SalaColegiadoConformacionRepository extends JpaRepository<SalaColegiadoConformacionEntity, SalaColegiadoConformacionEntityPk> {

    @Query(value = """
          SELECT new pe.gob.pj.votacion.domain.model.sijsuprema.Colegiado(
          scc.codigoUsuario,
          ue.apellidoPaterno,
          ue.apellidoMaterno,
          ue.nombres)
          FROM SalaColegiadoConformacionEntity scc
          INNER JOIN UsuarioEntity ue ON ue.id.codigoUsuario = scc.codigoUsuario
          WHERE scc.id.distrito = :codigoDistrito
          AND scc.id.provincia = :codigoProvincia
          AND scc.id.instancia = :codigoInstancia
      """)
    List<Colegiado> listarColegiados(@Param(value = "codigoDistrito") String codigoDistrito,
                                     @Param(value = "codigoProvincia") String codigoProvincia,
                                     @Param(value = "codigoInstancia") String codigoInstancia);


    @Query(value = """
            SELECT scc
            FROM SalaColegiadoConformacionEntity scc
            WHERE scc.activo = 'S'
              AND scc.id.distrito  = :cDistrito
              AND scc.id.provincia = :cProvincia
              AND scc.id.instancia = :cInstancia
    """)
    List<SalaColegiadoConformacionEntity> listarMiembros(@Param(value = "cDistrito") String distrito,
                        @Param(value = "cProvincia") String provincia,
                        @Param(value = "cInstancia") String instancia);

    @Query(value = """
            SELECT scc
            FROM SalaColegiadoConformacionEntity scc
            WHERE scc.codigoUsuario = :cUsuario
              AND scc.activo  = :activo
              AND scc.id.distrito = :cDistrito
              AND scc.id.provincia = :cProvincia
              AND scc.id.instancia = :cInstancia
    """)
    Optional<SalaColegiadoConformacionEntity> findByUsuarioActivo(@Param(value = "cUsuario") String cUsuario,
                                                                  @Param(value = "activo") String activo,
                                                                  @Param(value = "cDistrito") String cDistrito,
                                                                  @Param(value = "cProvincia") String cProvincia,
                                                                  @Param(value = "cInstancia") String cInstancia);

}
