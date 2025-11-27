package pe.gob.pj.votacion.domain.model.sijsuprema;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Usuario {
  
  String codigoUsuario;
  String codigoDistrito;
  String nombreDistrito;
  String codigoProvincia;
  String documentoIdentidad;
  String apellidoPaterno;
  String apellidoMaterno;
  String nombres;
  List<Perfil> perfiles;
  List<Instancia> instancias;
  
}
