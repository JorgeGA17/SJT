package pe.gob.pj.votacion.infraestructure.rest.responses;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsuarioResponse {

  String codigoUsuario;
  String codigoDistrito;
  String nombreDistrito;
  String codigoProvincia;
  String documentoIdentidad;
  String apellidoPaterno;
  String apellidoMaterno;
  String nombres;
  List<InstanciaUsuarioResponse> instancias = new ArrayList<>();
  List<PerfilUsuarioResponse> perfiles = new ArrayList<>();

  String token;
}
