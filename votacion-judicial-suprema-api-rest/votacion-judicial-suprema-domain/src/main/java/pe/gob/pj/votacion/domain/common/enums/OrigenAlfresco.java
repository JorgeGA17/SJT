package pe.gob.pj.votacion.domain.common.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public enum OrigenAlfresco {
  
  PODER_JUDICIAL("PJ"),
  TRIBUNAL_CONSTITUCIONAL("TC"),
  TRIBUNAL_FISCAL("TF")
  ;
  
  String origen;
  
}
