package pe.gob.pj.votacion.infraestructure.rest.requests;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrarValidacionRequest implements Serializable {

  static final long serialVersionUID = 1L;

  @JsonProperty(value = "formatoRespuesta")
  String formatoRespuesta;

  @JsonProperty("numeroValidado")
  Integer numeroValidado;

  @NotNull(message = "El parámetro observacion no puede ser nulo.")
  @JsonProperty("observacion")
  String observacion;

}
