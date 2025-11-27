package pe.gob.pj.votacion.infraestructure.rest.responses;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ObtenerApuntesProgramacionResponse extends GlobalResponse implements Serializable {

  static final long serialVersionUID = 1L;

  ApuntesProgramacionResponse data;

  public ObtenerApuntesProgramacionResponse(String codigoOperacion,
      ApuntesProgramacionResponse data) {
    super(codigoOperacion);
    this.data = data;
  }

}
