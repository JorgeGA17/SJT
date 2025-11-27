package pe.gob.pj.votacion.infraestructure.rest.requests;

import java.io.Serializable;
import java.util.List;
import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.PatronValidacionConstants;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FalloRequest implements Serializable {

  static final long serialVersionUID = 1L;

  @NotNull(message = "El parámetro numeroSecuencia no puede tener un valor nulo.")
  @JsonProperty(value = "numeroSecuencia")
  Integer numeroSecuencia;

  @Length(min = 2, max = 2,
      message = "El parámetro codigoSentido tiene un tamaño no valido [min=2,max=2].")
  @NotNull(message = "El parámetro codigoSentido no puede tener un valor nulo.")
  @JsonProperty(value = "codigoSentido")
  String codigoSentido;

  @NotNull(message = "El parámetro codigoFallo no puede tener un valor nulo.")
  @JsonProperty(value = "codigoFallo")
  Integer codigoFallo;

  @Pattern(regexp = PatronValidacionConstants.S_N,
      message = "El parámetro flagDiscordia tiene un formato no válido [S|N].")
  @NotNull(message = "El parámetro flagDiscordia no puede tener un valor nulo.")
  @JsonProperty(value = "flagDiscordia")
  String flagDiscordia;

  @Length(min = 0, max = 255,
      message = "El parámetro anotacion tiene un tamaño no valido [min=0,max=255].")
  @NotNull(message = "El parámetro anotacion no puede tener un valor nulo.")
  @JsonProperty(value = "anotacion")
  String anotacion;

  List<DiscordiaRequest> discordias;
}
