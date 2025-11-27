package pe.gob.pj.votacion.domain.model.sijsuprema.command;

import java.io.InputStream;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Accessors(fluent = true)
@AllArgsConstructor @NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrarEnvioProyectoCommand extends AuditoriaCommand {

  Integer idProyecto;
  BigDecimal numeroUnico;
  Integer numeroIncidente;
  Integer sentido;
  Integer votacion;
  String usuarioResponsable;
  String codigoEstado;
  String nombreDocumento;
  InputStream archivo;
  
  String extension;
  String uuid;

}
