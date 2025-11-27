package pe.gob.pj.votacion.domain.model.sijsuprema;

import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class PerfilOpcions {
  List<Opcion> opciones;
}
