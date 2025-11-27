package pe.gob.pj.votacion.infraestructure.rest.controllers;

import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.common.enums.Formatos;
import pe.gob.pj.votacion.domain.common.utils.ProjectUtils;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ContarProyectosPendientesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosPendientesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosRelacionadosQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosValidadosQuery;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.ConsultarProyectosUseCasePort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.RegistrarEnvioVotoProyectoUseCasePort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.RegistrarProyectoUseCasePort;
import pe.gob.pj.votacion.infraestructure.mappers.ProyectoMapper;
import pe.gob.pj.votacion.infraestructure.rest.requests.RegistrarValidacionRequest;
import pe.gob.pj.votacion.infraestructure.rest.requests.RegistroEnvioVotoProyectoRequest;
import pe.gob.pj.votacion.infraestructure.rest.responses.ContarProyectosResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.GlobalResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarProyectosResponse;
import pe.gob.pj.votacion.infraestructure.rest.strategy.GenerarHttpHeader;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProyectosController implements Proyectos, GenerarHttpHeader {

  ConsultarProyectosUseCasePort consultarProyectosUseCasePort;
  RegistrarProyectoUseCasePort registrarProyectoUseCasePort;
  ProyectoMapper proyectoMapper;

  RegistrarEnvioVotoProyectoUseCasePort registrarEnvioVotoProyectoUseCasePort;

  @Override
  public ResponseEntity<ListarProyectosResponse> listarPendientes(PeticionServicios peticion,
      String formatoRespuesta, String codigoDistrito, String codigoProvincia,
      String codigoInstancia, String usuarioResponsable, Integer idEstado, String fechaInicio,
      String fechaFin) {

    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarProyectosResponse(peticion.getCuo(),
            proyectoMapper
                .toProyectosVotoResponse(consultarProyectosUseCasePort.listarPendientes(peticion,
                    ListarProyectosPendientesQuery.builder().codigoDistrito(codigoDistrito)
                        .codigoProvincia(codigoProvincia).codigoInstancia(codigoInstancia)
                        .usuarioResponsable(usuarioResponsable).idEstadoProyecto(idEstado)
                        .fechaInicio(ProjectUtils.inicioDelDiaLocalDateTime(fechaInicio,
                            Formatos.FECHA_YYYY_MM_DD_GUION.getFormato()))
                        .fechaFin(ProjectUtils.finDelDiaLocalDateTime(fechaFin,
                            Formatos.FECHA_YYYY_MM_DD_GUION.getFormato()))
                        .build()))));
  }

  @Override
  public ResponseEntity<ListarProyectosResponse> listarRelacionados(PeticionServicios peticion,
      String formatoRespuesta, String numeroUnico, String numeroIncidente, String numeroSentido,
      String numeroVotacion, String usuarioResponsable) {

    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarProyectosResponse(peticion.getCuo(),
            proyectoMapper.toProyectosVotoResponse(consultarProyectosUseCasePort.listarRelacionados(
                peticion,
                ListarProyectosRelacionadosQuery.builder().numeroUnico(new BigDecimal(numeroUnico))
                    .numeroIndicente(Integer.parseInt(numeroIncidente))
                    .numeroSentido(Integer.parseInt(numeroSentido))
                    .numeroVotacion(Integer.parseInt(numeroVotacion))
                    .usuarioResponsable(usuarioResponsable).build()))));
  }

  @Override
  public ResponseEntity<ContarProyectosResponse> contarPendientes(PeticionServicios peticion,
      String formatoRespuesta, String usuarioResponsable, Integer idEstado) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ContarProyectosResponse(peticion.getCuo(),
            consultarProyectosUseCasePort.contarPendientes(peticion, ContarProyectosPendientesQuery
                .builder().idEstado(idEstado).usuarioResponsable(usuarioResponsable).build())));
  }

  @Override
  public ResponseEntity<ListarProyectosResponse> listarValidados(PeticionServicios peticion,
      String formatoRespuesta, String codigoDistrito, String codigoProvincia,
      String codigoInstancia, String usuarioResponsable, Integer idEstado, String fechaInicio,
      String fechaFin) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarProyectosResponse(peticion.getCuo(),
            proyectoMapper
                .toProyectosVotoResponse(consultarProyectosUseCasePort.listarValidados(peticion,
                    ListarProyectosValidadosQuery.builder().codigoDistrito(codigoDistrito)
                        .codigoProvincia(codigoProvincia).codigoInstancia(codigoInstancia)
                        .usuarioResponsable(usuarioResponsable).idEstadoProyecto(idEstado)
                        .fechaInicio(ProjectUtils.inicioDelDiaLocalDateTime(fechaInicio,
                            Formatos.FECHA_YYYY_MM_DD_GUION.getFormato()))
                        .fechaFin(ProjectUtils.finDelDiaLocalDateTime(fechaFin,
                            Formatos.FECHA_YYYY_MM_DD_GUION.getFormato()))
                        .build()))));
  }

  @Override
  public ResponseEntity<GlobalResponse> registrarValidacion(PeticionServicios peticion,
      String formatoRespuesta, String idProyecto, String codigoUsuario,
      RegistrarValidacionRequest request) {
    registrarProyectoUseCasePort.registrarValidacion(peticion,
        proyectoMapper.toRegistrarValidacionCommand(idProyecto, codigoUsuario, request));
    return ResponseEntity.ok().headers(getHttpHeader(request.getFormatoRespuesta()))
        .body(new GlobalResponse(peticion.getCuo()));
  }

  @Override
  public ResponseEntity<GlobalResponse> registrarEnvioVoto(PeticionServicios peticion,
      String idProyecto, RegistroEnvioVotoProyectoRequest metadata, MultipartFile file) {
    registrarEnvioVotoProyectoUseCasePort.registrarEnvioVoto(peticion,
        proyectoMapper.toRegistrarEnvioProyectoCommand(metadata, file, peticion, idProyecto));
    return ResponseEntity.ok().headers(getHttpHeader(metadata.getFormatoRespuesta()))
        .body(new GlobalResponse(peticion.getCuo()));
  }

}
