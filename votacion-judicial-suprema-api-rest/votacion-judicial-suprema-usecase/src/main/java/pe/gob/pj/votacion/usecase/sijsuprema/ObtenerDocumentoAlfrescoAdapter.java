package pe.gob.pj.votacion.usecase.sijsuprema;

import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.common.enums.OrigenAlfresco;
import pe.gob.pj.votacion.domain.exceptions.general.DescargaArchivoAlfrescoFallidoException;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.properties.AlfrescoConfigData;
import pe.gob.pj.votacion.domain.model.report.ArchivoReporte;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ObtenerDocumentoAlfrescoQuery;
import pe.gob.pj.votacion.domain.port.files.GestionArchivosAlfrescoPort;
import pe.gob.pj.votacion.domain.port.properties.AlfrescoConfigurationPort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.ObtenerDocumentoAlfrescoPort;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ObtenerDocumentoAlfrescoAdapter implements ObtenerDocumentoAlfrescoPort {

  GestionArchivosAlfrescoPort gestionArchivosAlfrescoPort;
  AlfrescoConfigurationPort alfrescoConfigurationPort;

  @Override
  public ArchivoReporte obtenerDocumento(PeticionServicios peticion,
      ObtenerDocumentoAlfrescoQuery query) {

    final AlfrescoConfigData config;
    
    if (List.of(OrigenAlfresco.TRIBUNAL_CONSTITUCIONAL.getOrigen(),
        OrigenAlfresco.TRIBUNAL_FISCAL.getOrigen()).contains(query.origen().toUpperCase())) {
      log.info("CUO [{}]: Solicitando configuración de Alfresco para 'Tribunal'.",
          peticion.getCuo());
      config = alfrescoConfigurationPort.getTribunalConfig();
    } else {
      log.info("CUO [{}]: Solicitando configuración de Alfresco para 'Jurisprudencia'.",
          peticion.getCuo());
      config = alfrescoConfigurationPort.getJurisprudenciaConfig();
    }

    log.info("CUO [{}]: Descargando documento con UUID: {}", peticion.getCuo(), query.uuid());
    GestionArchivosAlfrescoPort.AlfrescoDocument alfrescoDoc =
        gestionArchivosAlfrescoPort.descargarDocumentoPorUuid(query.uuid(), config)
            .orElseThrow(() -> new DescargaArchivoAlfrescoFallidoException(
                "Documento no encontrado con UUID: " + query.uuid()));

    Resource resource = new InputStreamResource(alfrescoDoc.inputStream());

    return new ArchivoReporte(alfrescoDoc.nombreArchivo(), alfrescoDoc.contentType(),
        alfrescoDoc.contentLength(), resource);

  }

}
