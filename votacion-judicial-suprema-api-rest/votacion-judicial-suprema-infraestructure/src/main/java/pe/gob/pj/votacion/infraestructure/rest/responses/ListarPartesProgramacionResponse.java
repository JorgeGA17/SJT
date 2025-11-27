package pe.gob.pj.votacion.infraestructure.rest.responses;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListarPartesProgramacionResponse extends GlobalResponse implements Serializable {

  static final long serialVersionUID = 1L;

  List<ParteProgramacionResponse> data;

  public ListarPartesProgramacionResponse(String codigoOperacion,
      List<ParteProgramacionResponse> data) {
    super(codigoOperacion);
    this.data = data;
  }

}
