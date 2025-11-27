package pe.gob.pj.votacion.domain.model.sijsuprema.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Accessors(fluent = true)
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AuditoriaCommand {
  
  String ipPublica;
  String usuarioSesion;
  
}
