package pe.gob.pj.votacion.infraestructure.rest.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ValidarDatosQuery;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.RegistrarVotoUseCasePort;
import pe.gob.pj.votacion.infraestructure.mappers.VotoMapper;
import pe.gob.pj.votacion.infraestructure.rest.requests.RegistrarVotoRequest;
import pe.gob.pj.votacion.infraestructure.rest.responses.GlobalResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.RegistrarResponse;
import pe.gob.pj.votacion.infraestructure.rest.strategy.GenerarHttpHeader;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VistaCausaVotoController implements VistaCausaVoto, GenerarHttpHeader {

  RegistrarVotoUseCasePort registrarVotoUseCasePort;
  VotoMapper votoMapper;

  @Override
  public ResponseEntity<RegistrarResponse> validarDatos(PeticionServicios peticion,
      String formatoRespuesta, String codigoDistrito, String codigoProvincia,
      String codigoInstancia, String codigoProgramacion, String numeroGrupoVoto,
      String numeroSecuenciaVoto, String numeroConformacionVoto) {
    return ResponseEntity.ok().headers(getHttpHeader(formatoRespuesta))
        .body(new RegistrarResponse(peticion.getCuo(),
            votoMapper.toRegistroVotoResponse(registrarVotoUseCasePort.validarDatos(peticion,
                ValidarDatosQuery.builder().codigoDistrito(codigoDistrito)
                    .codigoProvincia(codigoProvincia).codigoInstancia(codigoInstancia)
                    .codigoProgramacion(codigoProgramacion)
                    .numeroGrupoVoto(Integer.parseInt(numeroGrupoVoto))
                    .numeroSecuenciaVoto(Integer.parseInt(numeroSecuenciaVoto))
                    .numeroConformacionVoto(Integer.parseInt(numeroConformacionVoto)).build()))));
  }

  @Override
  public ResponseEntity<GlobalResponse> registrarVoto(PeticionServicios peticion,
      String formatoRespuesta, String codigoDistrito, String codigoProvincia,
      String codigoInstancia, String codigoProgramacion, String numeroUnico, String numeroIncidente,
      RegistrarVotoRequest request) {
    registrarVotoUseCasePort.registrarVoto(peticion,
        votoMapper.toRegistrarVotoCommand(codigoDistrito, codigoProvincia, codigoInstancia,
            codigoProgramacion, numeroUnico, numeroIncidente, request));

    return ResponseEntity.ok().headers(getHttpHeader(request.getFormatoRespuesta()))
        .body(new GlobalResponse(peticion.getCuo()));
  }

  @Override
  public ResponseEntity<GlobalResponse> autoguardado(PeticionServicios peticion,
      String formatoRespuesta, String codigoDistrito, String codigoProvincia,
      String codigoInstancia, String codigoProgramacion, String numeroUnico, String numeroIncidente,
      RegistrarVotoRequest request) {
    registrarVotoUseCasePort.autoguardado(peticion,
        votoMapper.toRegistrarVotoCommand(codigoDistrito, codigoProvincia, codigoInstancia,
            codigoProgramacion, numeroUnico, numeroIncidente, request));

    return ResponseEntity.ok().headers(getHttpHeader(request.getFormatoRespuesta()))
        .body(new GlobalResponse(peticion.getCuo()));
  }


}
