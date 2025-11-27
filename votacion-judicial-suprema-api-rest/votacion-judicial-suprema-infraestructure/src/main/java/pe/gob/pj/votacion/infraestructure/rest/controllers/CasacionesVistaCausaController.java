package pe.gob.pj.votacion.infraestructure.rest.controllers;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import org.springframework.core.io.InputStreamResource;
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
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCausalesProgramacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarDocumentosDigitalesProgramacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarDocumentosProgramacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarImpedidosProgramacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarMagistradosDiscordiaProgramacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarMagistradosProgramacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarMateriasProgramacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarPartesProgramacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ObtenerApuntesProgramacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.VisualizarDocumentoDigitalQuery;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.ConsultarProgramacionUseCasePort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.FTPUseCasePort;
import pe.gob.pj.votacion.infraestructure.mappers.ProgramacionMapper;
import pe.gob.pj.votacion.infraestructure.rest.responses.DocumentoDigitalProgramacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarCausalesProgramacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarDocumentosDigitalesProgramacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarDocumentosProgramacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarImpedidosProgramacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarMagistradosDiscordiaProgramacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarMagistradosProgramacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarMateriasProgramacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarPartesProgramacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ObtenerApuntesProgramacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.strategy.GenerarHttpHeader;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CasacionesVistaCausaController implements CasacionesVistaCausa, GenerarHttpHeader {

  ConsultarProgramacionUseCasePort consultarProgramacionUseCasePort;
  FTPUseCasePort ftpUseCasePort;
  ProgramacionMapper programacionMapper;

  @Override
  public ResponseEntity<ListarMagistradosProgramacionResponse> listarMagistradosProgramacion(
      PeticionServicios peticion, String formatoRespuesta, String codigoProgramacion,
      String numeroGrupo, String numeroSecuencia, String numeroConformacion) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarMagistradosProgramacionResponse(peticion.getCuo(),
            programacionMapper.toMagistradosProgramacionResponse(
                consultarProgramacionUseCasePort.listarMagistradosProgramacion(peticion,
                    ListarMagistradosProgramacionQuery.builder()
                        .codigoProgramacion(codigoProgramacion)
                        .numeroGrupo(Integer.parseInt(numeroGrupo))
                        .numeroSecuencia(Integer.parseInt(numeroSecuencia))
                        .numeroConformacion(Integer.parseInt(numeroConformacion)).build()))));
  }

  @Override
  public ResponseEntity<ListarImpedidosProgramacionResponse> listarImpedidosProgramacion(
      PeticionServicios peticion, String formatoRespuesta, String codigoDistrito,
      String codigoProvincia, String codigoInstancia, String codigoProgramacion, String numeroUnico,
      String numeroIncidente, String fechaIngreso) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarImpedidosProgramacionResponse(peticion.getCuo(),
            programacionMapper.toImpedidosProgramacionResponse(
                consultarProgramacionUseCasePort.listarImpedimentosProgramacion(peticion,
                    ListarImpedidosProgramacionQuery.builder()
                        .codigoProgramacion(codigoProgramacion).codigoDistrito(codigoDistrito)
                        .codigoProvincia(codigoProvincia).codigoInstancia(codigoInstancia)
                        .numeroUnico(new BigDecimal(numeroUnico))
                        .numeroIncidente(Integer.parseInt(numeroIncidente))
                        .fechaIngreso(fechaIngreso).build()))));
  }

  @Override
  public ResponseEntity<ListarMateriasProgramacionResponse> listarMateriasProgramacion(
      PeticionServicios peticion, String formatoRespuesta, String codigoProgramacion,
      String numeroUnico, String numeroIncidente) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarMateriasProgramacionResponse(peticion.getCuo(),
            programacionMapper.toMateriasProgramacionResponse(
                consultarProgramacionUseCasePort.listarMateriasProgramacion(peticion,
                    ListarMateriasProgramacionQuery.builder().codigoProgramacion(codigoProgramacion)
                        .numeroUnico(new BigDecimal(numeroUnico))
                        .numeroIncidente(Integer.parseInt(numeroIncidente)).build()))));
  }

  @Override
  public ResponseEntity<ListarCausalesProgramacionResponse> listarCausalesProgramacion(
      PeticionServicios peticion, String formatoRespuesta, String codigoProgramacion,
      String numeroUnico, String numeroIncidente) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarCausalesProgramacionResponse(peticion.getCuo(),
            programacionMapper.toCausalesProgramacionResponse(
                consultarProgramacionUseCasePort.listarCausalesProgramacion(peticion,
                    ListarCausalesProgramacionQuery.builder().codigoProgramacion(codigoProgramacion)
                        .numeroUnico(new BigDecimal(numeroUnico))
                        .numeroIncidente(Integer.parseInt(numeroIncidente)).build()))));
  }

  @Override
  public ResponseEntity<ListarPartesProgramacionResponse> listarPartesProgramacion(
      PeticionServicios peticion, String formatoRespuesta, String codigoProgramacion,
      String numeroUnico, String numeroIncidente, String flagDiscordia, String numeroSentido,
      String numeroVotacion) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarPartesProgramacionResponse(peticion.getCuo(),
            programacionMapper.toPartesProgramacionResponse(
                consultarProgramacionUseCasePort.listarPartesProgramacion(peticion,
                    ListarPartesProgramacionQuery.builder().codigoProgramacion(codigoProgramacion)
                        .numeroUnico(new BigDecimal(numeroUnico))
                        .numeroIncidente(Integer.parseInt(numeroIncidente))
                        .flagDiscordia(flagDiscordia).numeroSentido(Integer.parseInt(numeroSentido))
                        .numeroVotacion(Integer.parseInt(numeroVotacion)).build()))));
  }

  @Override
  public ResponseEntity<ListarMagistradosDiscordiaProgramacionResponse> listarMagistradosDiscordiaProgramacion(
      PeticionServicios peticion, String formatoRespuesta, String codigoProgramacion,
      String numeroUnico, String numeroIncidente, String numeroSentido, String numeroVotacion,
      String numeroSecuenciaParte, String codigoVocalPonente) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarMagistradosDiscordiaProgramacionResponse(peticion.getCuo(),
            programacionMapper.toMagistradosDiscordiaProgramacionResponse(
                consultarProgramacionUseCasePort.listarMagistradosDiscordiaProgramacion(peticion,
                    ListarMagistradosDiscordiaProgramacionQuery.builder()
                        .codigoProgramacion(codigoProgramacion)
                        .numeroUnico(new BigDecimal(numeroUnico))
                        .numeroIncidente(Integer.parseInt(numeroIncidente))
                        .numeroSentido(Integer.parseInt(numeroSentido))
                        .numeroVotacion(Integer.parseInt(numeroVotacion))
                        .numeroSecuenciaParte(Integer.parseInt(numeroSecuenciaParte))
                        .codigoVocalPonente(codigoVocalPonente).build()))));
  }

  @Override
  public ResponseEntity<ObtenerApuntesProgramacionResponse> obtenerApuntesProgramacion(
      PeticionServicios peticion, String formatoRespuesta, String numeroUnico,
      String numeroIncidente, String numeroSentido, String codigoVocalUsuario) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ObtenerApuntesProgramacionResponse(peticion.getCuo(),
            programacionMapper.toApuntesProgramacionResponse(
                consultarProgramacionUseCasePort.obtenerApuntesProgramacion(peticion,
                    ObtenerApuntesProgramacionQuery.builder()
                        .numeroUnico(new BigDecimal(numeroUnico))
                        .numeroIncidente(Integer.parseInt(numeroIncidente))
                        .numeroSentido(Integer.parseInt(numeroSentido))
                        .codigoVocalUsuario(codigoVocalUsuario).build()))));
  }

  @Override
  public ResponseEntity<ListarDocumentosProgramacionResponse> listarDocumentosProgramacion(
      PeticionServicios peticion, String formatoRespuesta, String codigoProgramacion,
      String numeroUnico, String numeroIncidente) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarDocumentosProgramacionResponse(peticion.getCuo(),
            programacionMapper.toDocumentosProgramacionResponse(consultarProgramacionUseCasePort
                .listarDocumentosProgramacion(peticion, ListarDocumentosProgramacionQuery.builder()
                    .codigoProgramacion(codigoProgramacion).numeroUnico(new BigDecimal(numeroUnico))
                    .numeroIncidente(Integer.parseInt(numeroIncidente)).build()))));
  }

  @Override
  public ResponseEntity<ListarDocumentosDigitalesProgramacionResponse> listarDocumentosDigitalesProgramacion(
      PeticionServicios peticion, String formatoRespuesta, String numeroUnico,
      String numeroIncidente) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarDocumentosDigitalesProgramacionResponse(peticion.getCuo(),
            programacionMapper.toDocumentosDigitalesProgramacionResponse(
                consultarProgramacionUseCasePort.listarDocumentosDigitalesProgramacion(peticion,
                    ListarDocumentosDigitalesProgramacionQuery.builder()
                        .numeroUnico(new BigDecimal(numeroUnico))
                        .numeroIncidente(Integer.parseInt(numeroIncidente)).build()))));
  }

  @Override
  public ResponseEntity<Resource> visualizarDocumentoDigital(PeticionServicios peticion,
      String formatoRespuesta, String numeroDocumento, String numeroUnico, String numeroIncidente) {

    DocumentoDigitalProgramacionResponse rpta =
        programacionMapper.toDocumentoDigitalProgramacionResponse(
            consultarProgramacionUseCasePort.visualizarDocumentoDigital(peticion,
                VisualizarDocumentoDigitalQuery.builder().numeroUnico(new BigDecimal(numeroUnico))
                    .numeroIncidente(Integer.parseInt(numeroIncidente))
                    .numeroDocumento(Integer.parseInt(numeroDocumento)).build()));

    if (rpta == null) {
      throw new RuntimeException("No se encontro el documento digital");
    }

    byte[] contenido = ftpUseCasePort.cargarDocumentoFTP(peticion.getCuo(), rpta.ipFtp().trim(), 21,
        rpta.usuarioFtp().trim(), rpta.claveFtp().trim(), rpta.rutaArchivo().trim(),
        rpta.nombreArchivo().trim());

    return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/pdf"))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "inline; filename=\"" + rpta.nombreArchivo() + "\"")
        .body(new InputStreamResource(new ByteArrayInputStream(contenido)));
  }
}
