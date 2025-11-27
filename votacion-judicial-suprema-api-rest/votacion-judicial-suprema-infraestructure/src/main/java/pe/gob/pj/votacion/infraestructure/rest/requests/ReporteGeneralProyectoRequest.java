package pe.gob.pj.votacion.infraestructure.rest.requests;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import pe.gob.pj.votacion.infraestructure.common.utils.PatronValidacionConstants;

public record ReporteGeneralProyectoRequest(
    @NotBlank 
    String codigo_distrito,
    @NotBlank 
    String codigo_provincia,
    @NotBlank 
    String codigo_instancia,
    @Length(min = 3, max = 15,
    message = "El parámetro usuario_responsable tiene un tamaño no valido [min=3|max=15].") 
    @NotBlank 
    String usuario_responsable,
    @NotBlank 
    String id_estado_votacion,
    @NotNull 
    Integer id_estado_proyecto,
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
