package pe.gob.pj.votacion.domain.model.sijsuprema;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Instancia {
  
  String codigoInstancia;
  String nombreInstancia;
  String codigoOrganoJurisdiccional;
  String codigoDistrito;
  String codigoProvincia;
  
}
