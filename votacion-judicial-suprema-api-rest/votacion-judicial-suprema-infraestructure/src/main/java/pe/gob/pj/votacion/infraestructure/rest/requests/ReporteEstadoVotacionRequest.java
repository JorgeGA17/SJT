package pe.gob.pj.votacion.infraestructure.rest.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import pe.gob.pj.votacion.infraestructure.common.utils.PatronValidacionConstants;

public record ReporteEstadoVotacionRequest(
    @NotBlank 
    String codigo_distrito,
    @NotBlank 
    String codigo_provincia,
    @NotBlank 
    String codigo_instancia,
    @NotBlank 
    String id_estado_votacion,
    @NotNull(message = "El parámetro fecha_inicio no puede ser nulo.")
    @Pattern(regexp = PatronValidacionConstants.FECHA_YYYY_MM_DD,
    message = "El parámetro fecha_inicio solo permite fecha en formato YYYY-MM-DD.") 
    String fecha_inicio,
    @NotNull(message = "El parámetro fecha_fin no puede ser nulo.")
    @Pattern(regexp = PatronValidacionConstants.FECHA_YYYY_MM_DD,
    message = "El parámetro fecha_fin solo permite fecha_fin en formato YYYY-MM-DD.") 
    String fecha_fin
    ) {

}
