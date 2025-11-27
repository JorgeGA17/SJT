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
public class BuscarJurisprudenciaResponse extends GlobalResponse implements Serializable {

  static final long serialVersionUID = 1L;

  List<JurisprudenciaResponse> data;

  public BuscarJurisprudenciaResponse(String codigoOperacion, List<JurisprudenciaResponse> data) {
    super(codigoOperacion);
    this.data = data;
  }

}
