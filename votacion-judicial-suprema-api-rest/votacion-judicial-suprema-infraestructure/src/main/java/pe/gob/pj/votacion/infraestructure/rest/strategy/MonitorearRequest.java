package pe.gob.pj.votacion.infraestructure.rest.strategy;

import java.util.Optional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.port.usecase.auditoriageneral.AuditarPeticionUseCasePort;
import pe.gob.pj.votacion.infraestructure.mappers.AuditoriaGeneralMapper;

public interface MonitorearRequest {

  AuditarPeticionUseCasePort getAuditoriaGeneralUseCasePort();
  AuditoriaGeneralMapper getAuditoriaGeneralMapper();
  ObjectMapper getObjectMaper();

  default void cargarTramaPeticion(PeticionServicios peticion, Object request) {
    try {
      peticion.setPeticionBody(getObjectMaper().writeValueAsString(request));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  default void guardarAuditoria(Optional<PeticionServicios> peticion) {
    peticion.ifPresent(p->{
      p.setFin(System.currentTimeMillis());
      getAuditoriaGeneralUseCasePort().crear(p.getCuo(),
          getAuditoriaGeneralMapper().toAuditoriaAplicativos(p));
    });
  }

}
