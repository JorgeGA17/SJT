package pe.gob.pj.votacion.infraestructure.rest.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import pe.gob.pj.votacion.infraestructure.common.utils.PatronValidacionConstants;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscordiaRequest implements Serializable {

    /**
     *
     */
    static final long serialVersionUID = 1L;

    @Length(min=0, max=15, message = "El parámetro codigoUsuario tiene un tamaño no valido [min=0,max=15].")
    @JsonProperty(value = "codigoUsuario")
    String codigoUsuario;

    @Pattern(regexp = PatronValidacionConstants.S_N, message = "El parámetro flagActivo tiene un formato no válido [S|N].")
    @NotBlank(message = "El parámetro flagActivo no puede tener un valor vacío.")
    @NotNull(message = "El parámetro flagActivo no puede tener un valor nulo.")
    @JsonProperty(value = "flagActivo")
    String flagActivo;

    @JsonProperty(value = "id")
    Integer id;

}
