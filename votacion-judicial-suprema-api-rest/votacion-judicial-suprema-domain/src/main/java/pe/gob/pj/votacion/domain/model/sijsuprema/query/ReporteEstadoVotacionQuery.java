package pe.gob.pj.votacion.domain.model.sijsuprema.query;

import java.time.LocalDateTime;
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
public class ReporteEstadoVotacionQuery {

  String codigoDistrito;
  String codigoProvincia;
  String codigoInstancia;
  String idEstadoVotacion;
  LocalDateTime fechaInicio;
  LocalDateTime fechaFin;

}
