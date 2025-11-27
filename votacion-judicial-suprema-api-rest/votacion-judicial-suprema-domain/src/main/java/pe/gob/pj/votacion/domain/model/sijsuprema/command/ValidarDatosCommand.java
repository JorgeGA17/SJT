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
public class ValidarDatosCommand {

  String codigoDistrito;
  String codigoProvincia;
  String codigoInstancia;
  String codigoProgramacion;
  Integer numeroGrupoVoto;
  Integer numeroSecuenciaVoto;
  Integer numeroConformacionVoto;

}
