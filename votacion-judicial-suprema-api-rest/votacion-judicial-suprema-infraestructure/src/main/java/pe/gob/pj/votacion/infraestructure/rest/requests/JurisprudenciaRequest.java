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
public class JurisprudenciaRequest implements Serializable {

    /**
     *
     */
    static final long serialVersionUID = 1L;

    @Length(min=2, max=2, message = "El parámetro source tiene un tamaño no valido [min=2,max=2].")
    @NotBlank(message = "El parámetro source no puede tener un valor vacío.")
    @NotNull(message = "El parámetro source no puede tener un valor nulo.")
    @JsonProperty(value = "source")
    String source;

    @Length(min=1, max=82, message = "El parámetro recurso tiene un tamaño no valido [min=1,max=82].")
    @NotBlank(message = "El parámetro recurso no puede tener un valor vacío.")
    @NotNull(message = "El parámetro recurso no puede tener un valor nulo.")
    @JsonProperty(value = "recurso")
    String recurso;

    @Length(min=36, max=36, message = "El parámetro uuid tiene un tamaño no valido [min=36,max=36].")
    @NotBlank(message = "El parámetro uuid no puede tener un valor vacío.")
    @NotNull(message = "El parámetro uuid no puede tener un valor nulo.")
    @JsonProperty(value = "uuid")
    String uuid;

    @Pattern(regexp = PatronValidacionConstants.S_N, message = "El parámetro flagActivo tiene un formato no válido [S|N].")
    @NotBlank(message = "El parámetro flagActivo no puede tener un valor vacío.")
    @NotNull(message = "El parámetro flagActivo no puede tener un valor nulo.")
    @JsonProperty(value = "flagActivo")
    String flagActivo;

    @JsonProperty(value = "id")
    Integer id;

}
