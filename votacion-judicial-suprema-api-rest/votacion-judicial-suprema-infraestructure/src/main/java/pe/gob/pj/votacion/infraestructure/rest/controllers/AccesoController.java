package pe.gob.pj.votacion.infraestructure.rest.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.IniciarSesionQuery;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.IniciarSesionUseCasePort;
import pe.gob.pj.votacion.infraestructure.common.utils.JwtUtils;
import pe.gob.pj.votacion.infraestructure.mappers.UsuarioMapper;
import pe.gob.pj.votacion.infraestructure.rest.requests.LoginRequest;
import pe.gob.pj.votacion.infraestructure.rest.responses.IniciarSesionResponse;
import pe.gob.pj.votacion.infraestructure.rest.strategy.GenerarHttpHeader;

@RestController
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccesoController implements Acceso, GenerarHttpHeader {

  IniciarSesionUseCasePort iniciarSesionUseCasePort;
  UsuarioMapper usuarioMapper;
  JwtUtils jwtUtils;

  @Override
  public ResponseEntity<IniciarSesionResponse> iniciarSesion(PeticionServicios peticion,
      @Valid LoginRequest request) {

    var usuario =
        usuarioMapper.toUsuarioResponse(iniciarSesionUseCasePort.iniciarSesion(peticion.getCuo(),
            IniciarSesionQuery.builder().usuario(request.getUsuario()).clave(request.getClave())
                .aplicaCaptcha(request.getAplicaCaptcha()).tokenCaptcha(request.getTokenCaptcha())
                .build(),
            peticion));

    usuario.setToken(jwtUtils.generarNuevoToken(peticion.getCuo(), peticion.getJwt(),
        request.getUsuario(), List.of("R0000022"),
        peticion.getIpPublica()));

    return ResponseEntity.ok().headers(getHttpHeader(request.getFormatoRespuesta()))
        .body(new IniciarSesionResponse(peticion.getCuo(), usuario));
  }


}
