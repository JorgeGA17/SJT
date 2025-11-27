package pe.gob.pj.votacion.infraestructure.rest.responses;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstanciaUsuarioResponse {
  String codigoInstancia;
  String nombreInstancia;
  String codigoOrganoJurisdiccional;
  String codigoDistrito;
  String codigoProvincia;
}
