package pe.gob.pj.votacion.infraestructure.rest.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesRelacionadasQuery;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.ExpedienteUseCasePort;
import pe.gob.pj.votacion.infraestructure.mappers.ExpedienteMapper;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarCasacionesResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarCasacionesRelacionadasResponse;
import pe.gob.pj.votacion.infraestructure.rest.strategy.GenerarHttpHeader;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CasacionesController implements Casaciones, GenerarHttpHeader {

  ExpedienteUseCasePort expedienteUseCasePort;
  ExpedienteMapper expedienteMapper;

  @Override
  public ResponseEntity<ListarCasacionesResponse> listarCasaciones(PeticionServicios peticion,
      String formatoRespuesta, String codigoDistrito, String codigoProvincia,
      String codigoInstancia, String fechaInicio, String fechaFin) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarCasacionesResponse(peticion.getCuo(),
            expedienteMapper.toCasacionesResponse(expedienteUseCasePort.listarCasaciones(peticion,
                ListarCasacionesQuery.builder().codigoDistrito(codigoDistrito)
                    .codigoProvincia(codigoProvincia).codigoInstancia(codigoInstancia)
                    .fechaInicio(fechaInicio).fechaFin(fechaFin).build()))));
  }

  @Override
  public ResponseEntity<ListarCasacionesRelacionadasResponse> listarCasacionesRelacionadas(
      PeticionServicios peticion, String formatoRespuesta, String codigoDistrito,
      String codigoProvincia, String codigoInstancia, String numeroUnico, String numeroIncidente) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new ListarCasacionesRelacionadasResponse(peticion.getCuo(),
            expedienteMapper.toCasacionesRelacionadasResponse(
                expedienteUseCasePort.listarCasacionesRelacionadas(peticion,
                    ListarCasacionesRelacionadasQuery.builder().codigoDistrito(codigoDistrito)
                        .codigoProvincia(codigoProvincia).codigoInstancia(codigoInstancia)
                        .numeroUnico(new BigDecimal(numeroUnico))
                        .numeroIncidente(Integer.parseInt(numeroIncidente)).build()))));
  }

}
