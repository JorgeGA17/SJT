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
import pe.gob.pj.votacion.domain.common.enums.Formatos;
import pe.gob.pj.votacion.domain.common.utils.ProjectUtils;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteEstadoVotacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteGeneralProyectoQuery;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.ConsultarProyectosUseCasePort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.GenerarReporteArchivoUseCasePort;
import pe.gob.pj.votacion.infraestructure.mappers.ProyectoMapper;
import pe.gob.pj.votacion.infraestructure.rest.requests.ReporteEstadoVotacionRequest;
import pe.gob.pj.votacion.infraestructure.rest.requests.ReporteGeneralProyectoRequest;
import pe.gob.pj.votacion.infraestructure.rest.responses.GenerarReporteEstadoVotacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.GenerarReporteGeneralProyectoResponse;
import pe.gob.pj.votacion.infraestructure.rest.strategy.GenerarHttpHeader;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportesController implements Reportes, GenerarHttpHeader {

  ProyectoMapper proyectoMapper;
  ConsultarProyectosUseCasePort consultarProyectosUseCasePort;
  GenerarReporteArchivoUseCasePort generarReporteArchivoUseCasePort;

  @Override
  public ResponseEntity<GenerarReporteEstadoVotacionResponse> generarReporteEstadoVotacion(
      PeticionServicios peticion, String formatoRespuesta, ReporteEstadoVotacionRequest request) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new GenerarReporteEstadoVotacionResponse(peticion.getCuo(),
            proyectoMapper.toReportesEstadoVotacionResponse(
                consultarProyectosUseCasePort.generarReporteEstadoVotacion(peticion,
                    ReporteEstadoVotacionQuery.builder().codigoDistrito(request.codigo_distrito())
                        .codigoProvincia(request.codigo_provincia())
                        .codigoInstancia(request.codigo_instancia())
                        .idEstadoVotacion(request.id_estado_votacion())
                        .fechaInicio(ProjectUtils.inicioDelDiaLocalDateTime(request.fecha_inicio(),
                            Formatos.FECHA_YYYY_MM_DD_GUION.getFormato()))
                        .fechaFin(ProjectUtils.finDelDiaLocalDateTime(request.fecha_fin(),
                            Formatos.FECHA_YYYY_MM_DD_GUION.getFormato()))
                        .build()))));
  }

  @Override
  public ResponseEntity<GenerarReporteGeneralProyectoResponse> generarReporteGeneralProyecto(
      PeticionServicios peticion, String formatoRespuesta, ReporteGeneralProyectoRequest request) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new GenerarReporteGeneralProyectoResponse(peticion.getCuo(),
            proyectoMapper.toReportesGeneralProyectoResponse(
                consultarProyectosUseCasePort.generarReporteGeneralProyecto(peticion,
                    ReporteGeneralProyectoQuery.builder().codigoDistrito(request.codigo_distrito())
                        .codigoProvincia(request.codigo_provincia())
                        .codigoInstancia(request.codigo_instancia())
                        .idEstadoVotacion(request.id_estado_votacion())
                        .idEstadoProyecto(request.id_estado_proyecto())
                        .fechaInicio(ProjectUtils.inicioDelDiaLocalDateTime(request.fecha_inicio(),
                            Formatos.FECHA_YYYY_MM_DD_GUION.getFormato()))
                        .fechaFin(ProjectUtils.finDelDiaLocalDateTime(request.fecha_fin(),
                            Formatos.FECHA_YYYY_MM_DD_GUION.getFormato()))
                        .build()))));
  }

  @Override
  public ResponseEntity<Resource> generarReporteEstadoVotacionDescargar(PeticionServicios peticion,
      ReporteEstadoVotacionRequest request, String tipoReporte) {

    var archivo = generarReporteArchivoUseCasePort.generarReporteEstadoVotacion(peticion,
        ReporteEstadoVotacionQuery.builder().codigoDistrito(request.codigo_distrito())
            .codigoProvincia(request.codigo_provincia()).codigoInstancia(request.codigo_instancia())
            .idEstadoVotacion(request.id_estado_votacion())
            .fechaInicio(ProjectUtils.inicioDelDiaLocalDateTime(request.fecha_inicio(),
                Formatos.FECHA_YYYY_MM_DD_GUION.getFormato()))
            .fechaFin(ProjectUtils.finDelDiaLocalDateTime(request.fecha_fin(),
                Formatos.FECHA_YYYY_MM_DD_GUION.getFormato()))
            .build(),
        tipoReporte);

    return ResponseEntity.ok().contentType(MediaType.parseMediaType(archivo.contentType()))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + archivo.nombreArchivo() + "\"")
        .contentLength(archivo.contentLength()).body(archivo.resource());
  }

  @Override
  public ResponseEntity<Resource> generarReporteGeneralProyectoDescargar(PeticionServicios peticion,
      ReporteGeneralProyectoRequest request, String tipoReporte) {

    var archivo = generarReporteArchivoUseCasePort.generarReporteGeneralProyecto(peticion,
        ReporteGeneralProyectoQuery.builder().codigoDistrito(request.codigo_distrito())
            .codigoProvincia(request.codigo_provincia()).codigoInstancia(request.codigo_instancia())
            .idEstadoVotacion(request.id_estado_votacion())
            .idEstadoProyecto(request.id_estado_proyecto())
            .fechaInicio(ProjectUtils.inicioDelDiaLocalDateTime(request.fecha_inicio(),
                Formatos.FECHA_YYYY_MM_DD_GUION.getFormato()))
            .fechaFin(ProjectUtils.finDelDiaLocalDateTime(request.fecha_fin(),
                Formatos.FECHA_YYYY_MM_DD_GUION.getFormato()))
            .build(),
        tipoReporte);

    return ResponseEntity.ok().contentType(MediaType.parseMediaType(archivo.contentType()))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + archivo.nombreArchivo() + "\"")
        .contentLength(archivo.contentLength()).body(archivo.resource());
  }

}
