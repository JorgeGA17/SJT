package pe.gob.pj.votacion.infraestructure.db.tribunal.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.domain.model.tribunal.Jurisprudencia;
import pe.gob.pj.votacion.infraestructure.db.tribunal.entities.MovDocumentosDigitalesEntity;

public interface MovDocumentoDigitalRepository
    extends JpaRepository<MovDocumentosDigitalesEntity, Integer> {

  @Query("""
      SELECT new pe.gob.pj.votacion.domain.model.tribunal.Jurisprudencia(dd.xExpediente, dd.xUuidAlfresco, "TC" )
      FROM MovDocumentosDigitalesEntity dd
      INNER JOIN MaeEntidadEntity me ON dd.maeEntidad.nEntidad = me.nEntidad
      WHERE dd.activo = 'S'
      AND me.nEntidad = :idEntidad
      AND dd.xExpediente LIKE :numeroExpediente
      """)
  List<Jurisprudencia> findByExpediente(@Param(value = "idEntidad") Integer idEntidad,
      @Param(value = "numeroExpediente") String numeroExpediente);

}
