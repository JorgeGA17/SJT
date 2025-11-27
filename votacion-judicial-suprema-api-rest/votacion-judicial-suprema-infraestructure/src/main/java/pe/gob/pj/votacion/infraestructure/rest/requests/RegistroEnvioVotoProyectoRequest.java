package pe.gob.pj.votacion.infraestructure.rest.requests;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistroEnvioVotoProyectoRequest {

  @JsonProperty(value = "formatoRespuesta")
  String formatoRespuesta;

  @NotNull(message = "El parámetro numeroUnico es obligatorio y no se encuentra presente.")
  @JsonProperty(value = "numeroUnico")
  BigDecimal numeroUnico;

  @NotNull(message = "El parámetro numeroIncidente es obligatorio y no se encuentra presente.")
  @JsonProperty(value = "numeroIncidente")
  Integer numeroIncidente;

  @NotNull(message = "El parámetro sentido es obligatorio y no se encuentra presente.")
  @JsonProperty(value = "sentido")
  Integer sentido;

  @NotNull(message = "El parámetro votacion es obligatorio y no se encuentra presente.")
  @JsonProperty(value = "votacion")
  Integer votacion;

  @NotBlank(message = "El parámetro usuarioResponsable es obligatorio y no puede estar vacío.")
  @JsonProperty(value = "usuarioResponsable")
  String usuarioResponsable;

  @NotBlank(message = "El parámetro codigoEstado es obligatorio y no puede estar vacío.")
  @JsonProperty(value = "codigoEstado")
  String codigoEstado;

  @NotBlank(message = "El parámetro nombreDocumento es obligatorio y no puede estar vacío.")
  @JsonProperty(value = "nombreDocumento")
  String nombreDocumento;

}
