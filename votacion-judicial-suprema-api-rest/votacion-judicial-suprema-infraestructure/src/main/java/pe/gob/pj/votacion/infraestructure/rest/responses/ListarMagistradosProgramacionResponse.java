package pe.gob.pj.votacion.infraestructure.rest.responses;

import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListarMagistradosProgramacionResponse extends GlobalResponse implements Serializable {

  static final long serialVersionUID = 1L;

  List<MagistradoProgramacionResponse> data;

  public ListarMagistradosProgramacionResponse(String codigoOperacion,
      List<MagistradoProgramacionResponse> data) {
    super(codigoOperacion);
    this.data = data;
  }

}
