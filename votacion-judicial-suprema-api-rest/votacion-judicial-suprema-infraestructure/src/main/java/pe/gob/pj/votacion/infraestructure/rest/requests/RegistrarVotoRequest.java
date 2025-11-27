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
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrarVotoRequest implements Serializable {

    static final long serialVersionUID = 1L;

    @JsonProperty(value = "formatoRespuesta")
    String formatoRespuesta;

    @Length(min = 4, max = 4, message = "El parámetro codigoSede tiene un tamaño no válido (min=4,max=4).")
    @NotNull(message = "El parámetro codigoSede no puede ser nulo.")
    @JsonProperty("codigoSede")
    String codigoSede;

    @Length(min = 3, max = 3, message = "El parámetro codigoEstado tiene un tamaño no válido (min=3,max=3).")
    @NotNull(message = "El parámetro codigoEstado no puede ser nulo.")
    @JsonProperty("codigoEstado")
    String codigoEstado;

    @Length(min = 2, max = 2, message = "El parámetro codigoOrganoJuris tiene un tamaño no válido (min=2,max=2).")
    @NotNull(message = "El parámetro codigoOrganoJuris no puede ser nulo.")
    @JsonProperty("codigoOrganoJuris")
    String codigoOrganoJuris;

    @NotBlank(message = "El parámetro fechaIngreso no puede ser vacío.")
    @NotNull(message = "El parámetro fechaIngreso no puede ser nulo.")
    @JsonProperty("fechaIngreso")
    String fechaIngreso;

    @NotBlank(message = "El parámetro fechaProgramacion no puede ser vacío.")
    @NotNull(message = "El parámetro fechaProgramacion no puede ser nulo.")
    @JsonProperty("fechaProgramacion")
    String fechaProgramacion;

    @NotBlank(message = "El parámetro fechaEstado no puede ser vacío.")
    @NotNull(message = "El parámetro fechaEstado no puede ser nulo.")
    @JsonProperty("fechaEstado")
    String fechaEstado;

    @NotBlank(message = "El parámetro codigoUsuarioPonente no puede ser vacío.")
    @NotNull(message = "El parámetro codigoUsuarioPonente no puede ser nulo.")
    @JsonProperty("codigoUsuarioPonente")
    String codigoUsuarioPonente;

    @NotBlank(message = "El parámetro codigoEspecialidad no puede ser vacío.")
    @NotNull(message = "El parámetro codigoEspecialidad no puede ser nulo.")
    @JsonProperty("codigoEspecialidad")
    String codigoEspecialidad;

    @JsonProperty("numeroSentido")
    Integer numeroSentido;

    @JsonProperty("numeroVotacion")
    Integer numeroVotacion;

    @NotNull(message = "El parámetro numeroGrupoVoto no puede ser nulo.")
    @JsonProperty("numeroGrupoVoto")
    Integer numeroGrupoVoto;

    @NotNull(message = "El parámetro numeroSecuenciaVoto no puede ser nulo.")
    @JsonProperty("numeroSecuenciaVoto")
    Integer numeroSecuenciaVoto;

    @NotNull(message = "El parámetro numeroConformacionVoto no puede ser nulo.")
    @JsonProperty("numeroConformacionVoto")
    Integer numeroConformacionVoto;

    @Length(min = 0, max = 2000, message = "El parámetro apuntes tiene un tamaño no válido (min=0,max=2000).")
    @JsonProperty("apuntes")
    String apuntes;

    @Pattern(regexp = PatronValidacionConstants.S_N, message = "El parámetro flagVoto tiene un formato no válido [S|N].")
    @NotBlank(message = "El parámetro flagVoto no puede ser vacío.")
    @NotNull(message = "El parámetro flagVoto no puede ser nulo.")
    @JsonProperty("flagVoto")
    String flagVoto;

    @NotBlank(message = "El parámetro codigoUsuario no puede ser vacío.")
    @NotNull(message = "El parámetro codigoUsuario no puede ser nulo.")
    @JsonProperty("codigoUsuario")
    String codigoUsuario;

    @JsonProperty("codigoArea")
    String codigoArea;

    @NotBlank(message = "El parámetro abrev no puede ser vacío.")
    @JsonProperty("abrev")
    String abrev;

    @NotBlank(message = "El parámetro codigoAudUid no puede ser vacío.")
    @NotNull(message = "El parámetro codigoAudUid no puede ser nulo.")
    @JsonProperty("codigoAudUid")
    String codigoAudUid;

    @NotBlank(message = "El parámetro numeroAudIp no puede ser vacío.")
    @JsonProperty("numeroAudIp")
    String numeroAudIp;

    @JsonProperty(value = "fallos")
    List<FalloRequest> fallos;

    @JsonProperty(value = "jurisprudencias")
    List<JurisprudenciaRequest> jurisprudencias;
}
