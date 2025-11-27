package pe.gob.pj.votacion.infraestructure.rest.responses;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.enums.TipoError;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GlobalResponse implements Serializable {
  /**
  * 
  */
  private static final long serialVersionUID = 1L;

  String codigo;
  String descripcion;
  String codigoOperacion;
  


  public GlobalResponse(String codigoOperacion) {
    this.codigo = TipoError.OPERACION_EXITOSA.getCodigo();
    this.descripcion = TipoError.OPERACION_EXITOSA.getDescripcionUsuario();
    this.codigoOperacion = codigoOperacion;
  }

}
