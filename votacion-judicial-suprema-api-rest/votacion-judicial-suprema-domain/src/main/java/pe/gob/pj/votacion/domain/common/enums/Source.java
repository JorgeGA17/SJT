package pe.gob.pj.votacion.domain.common.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Getter
@RequiredArgsConstructor
public enum Source {

  PODER_JUDICIAL("PJ"), TRIBUNAL_CONSTITUCIONAL("TC"), TRIBUNAL_FISCAL("TF");

  String entidad;

}