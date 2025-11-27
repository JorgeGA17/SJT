package pe.gob.pj.votacion.domain.common.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public enum EstadoVotoProyecto {
  
  PENDIENTE(1),
  POR_VALIDAR(2),
  VALIDADO(3),
  OBSERVADO(4),
  REVISADO(5)
  ;
  
  Integer identificador;
  
}
