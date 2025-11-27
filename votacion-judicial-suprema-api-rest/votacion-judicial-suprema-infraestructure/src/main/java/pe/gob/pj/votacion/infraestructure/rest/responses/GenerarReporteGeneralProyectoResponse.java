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
public class GenerarReporteGeneralProyectoResponse extends GlobalResponse implements Serializable {

  static final long serialVersionUID = 1L;

  List<ReporteGeneralProyectoResponse> data;

  public GenerarReporteGeneralProyectoResponse(String cuo,
      List<ReporteGeneralProyectoResponse> data) {
    super(cuo);
    this.data = data;
  }

}
