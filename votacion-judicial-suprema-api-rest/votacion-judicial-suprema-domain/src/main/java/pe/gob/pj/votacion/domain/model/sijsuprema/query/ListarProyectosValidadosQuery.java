package pe.gob.pj.votacion.domain.model.sijsuprema.query;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Accessors(fluent = true)
@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListarProyectosValidadosQuery {

  String codigoDistrito;
  String codigoProvincia;
  String codigoInstancia;
  String usuarioResponsable;
  Integer idEstadoProyecto;
  LocalDateTime fechaInicio;
  LocalDateTime fechaFin;

}
