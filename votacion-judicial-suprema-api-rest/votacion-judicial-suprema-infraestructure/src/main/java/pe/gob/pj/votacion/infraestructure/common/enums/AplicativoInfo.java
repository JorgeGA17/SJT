package pe.gob.pj.votacion.infraestructure.common.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@RequiredArgsConstructor
public enum AplicativoInfo {
  
  NOMBRE_COMPONENTE("nombre","votacion-judicial-suprema-api-rest"),
  TIPO_COMPONENTE("tipo","api-rest"),
  CONEXTO("contexto","votacion-judicial-suprema-api-rest"),
  VERSION_ACTUAL("version","2.0.0")
  ;
  
  String propiedad;
  String nombre;
  
}
