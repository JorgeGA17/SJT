package pe.gob.pj.votacion.infraestructure.rest.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.report.ArchivoReporte;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.BuscarJurisprudenciaQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ObtenerDocumentoAlfrescoQuery;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.ObtenerDocumentoAlfrescoPort;
import pe.gob.pj.votacion.domain.port.usecase.tribunal.JurisprudenciaUseCasePort;
import pe.gob.pj.votacion.infraestructure.mappers.JurisprudenciaMapper;
import pe.gob.pj.votacion.infraestructure.rest.responses.BuscarJurisprudenciaResponse;
import pe.gob.pj.votacion.infraestructure.rest.strategy.GenerarHttpHeader;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JurisprudenciaController implements Jurisprudencia, GenerarHttpHeader {

  JurisprudenciaMapper jurisprudenciaMapper;
  JurisprudenciaUseCasePort jurisprudenciaUseCasePort;
  ObtenerDocumentoAlfrescoPort obtenerDocumentoAlfrescoPort;

  @Override
  public ResponseEntity<BuscarJurisprudenciaResponse> buscarJurisprudencia(
      PeticionServicios peticion, String formatoRespuesta, String fuente, String numeroExpediente) {

    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new BuscarJurisprudenciaResponse(peticion.getCuo(),
            jurisprudenciaMapper.toJurisprudenciasResponse(
                jurisprudenciaUseCasePort.buscarJurisprudencia(peticion, BuscarJurisprudenciaQuery
                    .builder().fuente(fuente).numeroExpediente(numeroExpediente).build()))));

  }

  @Override
  public ResponseEntity<Resource> visualizarPdf(PeticionServicios peticion, String origen,
      String uuid) {

    ArchivoReporte archivo = obtenerDocumentoAlfrescoPort.obtenerDocumento(peticion,
        ObtenerDocumentoAlfrescoQuery.builder().origen(origen).uuid(uuid).build());

    return ResponseEntity.ok().contentType(MediaType.parseMediaType(archivo.contentType()))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "inline; filename=\"" + archivo.nombreArchivo() + "\"")
        .body(archivo.resource());
  }
}
