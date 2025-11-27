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
public class ListarProyectosResponse extends GlobalResponse implements Serializable {

  static final long serialVersionUID = 1L;

  List<ProyectoVotoResponse> data;

  public ListarProyectosResponse(String cuo, List<ProyectoVotoResponse> data) {
    super(cuo);
    this.data = data;
  }

}
