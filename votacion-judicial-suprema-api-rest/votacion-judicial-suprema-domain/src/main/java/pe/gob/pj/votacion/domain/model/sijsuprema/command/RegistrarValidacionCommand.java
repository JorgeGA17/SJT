package pe.gob.pj.votacion.domain.model.sijsuprema.command;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Accessors(fluent = true)
@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrarValidacionCommand {

  Integer idProyecto;
  String codigoUsuario;
  Integer numeroValidado;
  String observacion;

}
