package pe.gob.pj.votacion.infraestructure.rest.advises;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.domain.exceptions.general.RolUsuarioTokenNoPermitidoException;
import pe.gob.pj.votacion.domain.exceptions.negocio.CredencialesSinCoincidenciaException;
import pe.gob.pj.votacion.domain.exceptions.negocio.OpcionesNoAsignadadException;
import pe.gob.pj.votacion.domain.exceptions.negocio.UsuarioSinPerfilAsignadoException;
import pe.gob.pj.votacion.domain.port.usecase.auditoriageneral.AuditarPeticionUseCasePort;
import pe.gob.pj.votacion.infraestructure.common.enums.TipoError;
import pe.gob.pj.votacion.infraestructure.mappers.AuditoriaGeneralMapper;
import pe.gob.pj.votacion.infraestructure.rest.responses.GlobalResponse;


@ControllerAdvice
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NegocioAdvise extends DefaultAdvise {

  public NegocioAdvise(AuditarPeticionUseCasePort auditoriaGeneralUseCasePort,
      AuditoriaGeneralMapper auditoriaGeneralMapper, ObjectMapper objectMaper) {
    super(auditoriaGeneralUseCasePort, auditoriaGeneralMapper, objectMaper);
  }

  @ExceptionHandler({OpcionesNoAsignadadException.class})
  ResponseEntity<GlobalResponse> handleOpcionesNoAsignadadException(OpcionesNoAsignadadException ex,
      WebRequest request) {
    var peticion = obtenerPeticionServicio(request, TipoError.OPCIONES_NOASIGNADAS);
    guardarAuditoria(peticion);
    return ResponseEntity.ok(handleResponse(peticion, TipoError.OPCIONES_NOASIGNADAS, ex));
  }

  @ExceptionHandler({UsuarioSinPerfilAsignadoException.class})
  ResponseEntity<GlobalResponse> handleUsuarioSinPerfilAsignadoException(
      UsuarioSinPerfilAsignadoException ex, WebRequest request) {
    var peticion = obtenerPeticionServicio(request, TipoError.PERFIL_NO_ASIGNADO);
    guardarAuditoria(peticion);
    return ResponseEntity.ok(handleResponse(peticion, TipoError.PERFIL_NO_ASIGNADO, ex));
  }

  @ExceptionHandler({CredencialesSinCoincidenciaException.class})
  ResponseEntity<GlobalResponse> handleCredencialesSinCoincidenciaException(
      CredencialesSinCoincidenciaException ex, WebRequest request) {
    var peticion = obtenerPeticionServicio(request, TipoError.CREDENCIALES_INCORRECTAS);
    guardarAuditoria(peticion);
    return ResponseEntity.ok(handleResponse(peticion, TipoError.CREDENCIALES_INCORRECTAS, ex));
  }

  @ExceptionHandler({RolUsuarioTokenNoPermitidoException.class})
  ResponseEntity<GlobalResponse> handleRolUsuarioTokenNoPermitidoException(
      RolUsuarioTokenNoPermitidoException ex, WebRequest request) {
    var peticion = obtenerPeticionServicio(request, TipoError.NUEVO_TOKEN_NO_VALIDO);
    guardarAuditoria(peticion);
    return ResponseEntity
        .ok(handleResponse(peticion, TipoError.NUEVO_TOKEN_NO_VALIDO, ex));
  }


}
