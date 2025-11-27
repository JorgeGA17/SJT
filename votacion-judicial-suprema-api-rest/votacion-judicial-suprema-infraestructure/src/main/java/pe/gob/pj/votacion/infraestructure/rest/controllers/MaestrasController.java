package pe.gob.pj.votacion.infraestructure.rest.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.*;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.ConsultarMaestrasUseCasePort;
import pe.gob.pj.votacion.infraestructure.mappers.MaestraMapper;
import pe.gob.pj.votacion.infraestructure.rest.responses.*;
import pe.gob.pj.votacion.infraestructure.rest.strategy.GenerarHttpHeader;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaestrasController implements Maestras, GenerarHttpHeader {

  ConsultarMaestrasUseCasePort consultarMaestrasUseCasePort;
  MaestraMapper maestraMapper;

  @Override
  public ResponseEntity<ListarFallosResponse> listarFallos(PeticionServicios peticion,
      String formatoRespuesta, String codigoEspecialidad, String codigoAbreviatura, String codigoMotivoIngreso) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarFallosResponse(peticion.getCuo(),
            maestraMapper.toFallosResponse(consultarMaestrasUseCasePort.listarFallos(peticion,
                ListarFallosQuery.builder().codigoEspecialidad(codigoEspecialidad)
                    .codigoAbreviatura(codigoAbreviatura).codigoMotivoIngreso(codigoMotivoIngreso).build()))));
  }

  @Override
  public ResponseEntity<ListarSentidosResponse> listarSentidos(PeticionServicios peticion,
      String formatoRespuesta, String codigoDistrito, String codigoProvincia,
      String codigoInstancia) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarSentidosResponse(peticion.getCuo(),
            maestraMapper.toSentidosResponse(consultarMaestrasUseCasePort.listarSentidos(peticion,
                ListarSentidosQuery.builder().codigoDistrito(codigoDistrito)
                    .codigoProvincia(codigoProvincia).codigoInstancia(codigoInstancia).build()))));
  }

  @Override
  public ResponseEntity<ListarResponsablesResponse> listarColegiados(PeticionServicios peticion,
      String formatoRespuesta, String codigoDistrito, String codigoProvincia,
      String codigoInstancia) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarResponsablesResponse(peticion.getCuo(),
            maestraMapper.toColegiadosResponse(consultarMaestrasUseCasePort.listarColegiados(
                peticion, ListarColegiadosQuery.builder().codigoDistrito(codigoDistrito)
                    .codigoProvincia(codigoProvincia).codigoInstancia(codigoInstancia).build()))));
  }

  @Override
  public ResponseEntity<ListarEstadosVotacionResponse> listarEstadosVotacion(
      PeticionServicios peticion, String formatoRespuesta) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarEstadosVotacionResponse(peticion.getCuo(),
            maestraMapper.toEstadosVotacionResponse(consultarMaestrasUseCasePort
                .listarEstadosVotacion(peticion, ListarEstadosVotacionQuery.builder().build()))));
  }

  @Override
  public ResponseEntity<ListarEstadosProyectoResponse> listarEstadosProyecto(
      PeticionServicios peticion, String formatoRespuesta) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarEstadosProyectoResponse(peticion.getCuo(),
            maestraMapper.toEstadosProyectoResponse(consultarMaestrasUseCasePort
                .listarEstadosProyecto(peticion, ListarEstadosProyectoQuery.builder().build()))));
  }
}
