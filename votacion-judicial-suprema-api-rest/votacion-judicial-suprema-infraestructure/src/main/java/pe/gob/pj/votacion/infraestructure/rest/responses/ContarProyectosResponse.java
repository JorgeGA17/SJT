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
public class ContarProyectosResponse extends GlobalResponse implements Serializable {

  static final long serialVersionUID = 1L;

  Integer data;

  public ContarProyectosResponse(String cuo, Integer data) {
    super(cuo);
    this.data = data;
  }

}
