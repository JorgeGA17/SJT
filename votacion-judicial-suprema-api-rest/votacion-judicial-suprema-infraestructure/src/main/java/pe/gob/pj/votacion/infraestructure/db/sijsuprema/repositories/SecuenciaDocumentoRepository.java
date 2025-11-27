package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.SecuenciaDocumentoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.SecuenciaDocumentoEntityPk;

public interface SecuenciaDocumentoRepository
    extends JpaRepository<SecuenciaDocumentoEntity, SecuenciaDocumentoEntityPk> {

  @Modifying
  @Query(value = """
              UPDATE SecuenciaDocumentoEntity sd
              SET  sd.numeroSecuencia = sd.numeroSecuencia + 1
              WHERE sd.id.numeroAño = :nAno
                  AND sd.id.codigoSede = :cDistrito
                  AND sd.id.codigoOrganoJuris = 'ALL'
                  AND sd.id.codigoEspecialidad = 'AL'
                  AND sd.id.codigoTipo = 'SEC_TEXPSENTID'
      """)
  int incrementarSecuencia(@Param(value = "nAno") String nAno,
      @Param(value = "cDistrito") String cDistrito);


  @Query(value = """
              SELECT sd.numeroSecuencia
              FROM SecuenciaDocumentoEntity sd
              WHERE sd.id.numeroAño = :nAno
                  AND sd.id.codigoSede = :cDistrito
                  AND sd.id.codigoOrganoJuris = 'ALL'
                  AND sd.id.codigoEspecialidad = 'AL'
                  AND sd.id.codigoTipo = 'SEC_TEXPSENTID'
      """)
  Integer getSecuencia(@Param(value = "nAno") String nAno,
      @Param(value = "cDistrito") String cDistrito);


  @Query(value = """
      SELECT sd FROM SecuenciaDocumentoEntity sd
      WHERE sd.id.numeroAño = :anio
      AND sd.id.codigoSede = :codigoSede
      """)
  Optional<SecuenciaDocumentoEntity> findSecuenciaDocumento(@Param("anio") String anio,
      @Param("codigoSede") String codigoSede);
}
