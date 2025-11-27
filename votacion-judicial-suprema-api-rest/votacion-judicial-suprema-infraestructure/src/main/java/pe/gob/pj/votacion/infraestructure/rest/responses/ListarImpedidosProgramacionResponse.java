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
public class ListarImpedidosProgramacionResponse extends GlobalResponse implements Serializable {

  static final long serialVersionUID = 1L;

  List<ImpedidoProgramacionResponse> data;

  public ListarImpedidosProgramacionResponse(String codigoOperacion, List<ImpedidoProgramacionResponse> data) {
    super(codigoOperacion);
    this.data = data;
  }

}
