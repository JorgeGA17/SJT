package pe.gob.pj.votacion.infraestructure.db.tribunal.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.BuscarJurisprudenciaQuery;
import pe.gob.pj.votacion.domain.model.tribunal.Jurisprudencia;
import pe.gob.pj.votacion.domain.port.persistence.tribunal.JurisprudenciaReadPersistencePort;
import pe.gob.pj.votacion.infraestructure.client.AlfrescoCmisClient;
import pe.gob.pj.votacion.infraestructure.db.tribunal.repositories.MovDocumentoDigitalRepository;
import pe.gob.pj.votacion.infraestructure.properties.AlfrescoProperty;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JurisprudenciaReadPersistenceAdapter implements JurisprudenciaReadPersistencePort {

  AlfrescoProperty alfrescoProperty;
  AlfrescoCmisClient cmisClient;
  MovDocumentoDigitalRepository movDocumentoDigitalRepository;

  @Override
  public List<Jurisprudencia> buscarJurisprudencia(String cuo, BuscarJurisprudenciaQuery query) {

    var resultado = new ArrayList<Jurisprudencia>();
    switch (query.fuente()) {
      case "PJ":
        cmisClient.conectar(alfrescoProperty.jurisprudencia().host(),
            alfrescoProperty.jurisprudencia().puerto(), 
            alfrescoProperty.jurisprudencia().usuario(),
            alfrescoProperty.jurisprudencia().clave());
        Session session = cmisClient.getSession();
        String cmisQuery =
            "SELECT pjcm:nroExpediente, pjcm:descPretensionDelito, pjcm:descTipoResolucion, "
            + "pjcm:fechaResolucion, pjcm:descOrganoJurisdiccional, pjcm:descNormaDerechoInterno, "
            + "pjcm:sumilla, pjcm:descPalabraClave, pjcm:uuidOriginal, pjcm:descRecurso, "
            + "pjcm:especialidad, pjcm:proceso, pjcm:descTerminoTesaurus, pjcm:corte, "
            + "pjcm:descJustIntercultural, pjcm:descJurispUniforme, pjcm:nroResolucion, cmis:objectId "
            + "FROM pjcm:resolucionJurisprudencia "
            + "WHERE "
                + "CONTAINS('"+ query.numeroExpediente() + "') AND " 
                + "pjcm:corte='1' "
                + "AND pjcm:publicado='S' " 
                + "ORDER BY pjcm:fechaResolucion DESC";

        log.info("{} Ejecutando consulta CMIS:\n{}", cuo, cmisQuery);

        ItemIterable<QueryResult> resultados = session.query(cmisQuery, false);

        if (!resultados.iterator().hasNext()) {
          log.warn("{} No se encontraron resultados para expediente: {}", cuo,
              query.numeroExpediente());
          return resultado;
        }

        for (QueryResult fila : resultados) {
          Jurisprudencia j = new Jurisprudencia(getValue(fila, "pjcm:nroExpediente"),
              getValue(fila, "pjcm:uuidOriginal"), 
              getValue(fila, "pjcm:descOrganoJurisdiccional"));
          resultado.add(j);
        }
        break;

      case "TC": 
        String numeroExpedienteTC = formatearExpediente(query.numeroExpediente());
        List<Jurisprudencia> tc =
            Optional.ofNullable(movDocumentoDigitalRepository.findByExpediente(1, numeroExpedienteTC + "%"))
                .orElse(Collections.emptyList());
        resultado = new ArrayList<>(tc);
        break;

      case "TF": 
        String numeroExpedienteTF = formatearExpediente(query.numeroExpediente());
        List<Jurisprudencia> tf = Optional
            .ofNullable(movDocumentoDigitalRepository.findByExpediente(2, numeroExpedienteTF + "%"))
            .orElse(Collections.emptyList());
        resultado = new ArrayList<>(tf);
        break;
    }
    return resultado;
  }

  // Utilidad segura para leer propiedades CMIS
  private String getValue(QueryResult result, String propName) {
    var prop = result.getPropertyByQueryName(propName);
    return prop != null && prop.getFirstValue() != null ? prop.getFirstValue().toString() : "";
  }

  String formatearExpediente(String numeroExpediente) {
    var parts = numeroExpediente.split("-");
    if (parts.length == 1) {
      return "%05d".formatted(Integer.parseInt(parts[0]));
    }
    return "%05d-%s".formatted(
        Integer.parseInt(parts[0]),
        String.join("-", Arrays.copyOfRange(parts, 1, parts.length))
    );
  }
}
