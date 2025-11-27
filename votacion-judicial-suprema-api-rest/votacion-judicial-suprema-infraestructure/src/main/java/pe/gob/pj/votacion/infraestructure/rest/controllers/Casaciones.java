package pe.gob.pj.votacion.infraestructure.rest.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import pe.gob.pj.votacion.domain.common.utils.ProjectConstants;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.infraestructure.common.utils.PatronValidacionConstants;
import pe.gob.pj.votacion.infraestructure.rest.responses.ErrorResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarCasacionesRelacionadasResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarCasacionesResponse;

@RestController
@RequestMapping(value = "casaciones", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@Tag(name = "Expedientes", description = "API para manejar expedientes")
public interface Casaciones {

  @GetMapping(value = "{codigo_distrito}/{codigo_provincia}/{codigo_instancia}")
  @Operation(summary = "Obtener casaciones", operationId = "listarCasaciones",
      description = "Permite listar casaciones por filtros")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = ListarCasacionesResponse.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<ListarCasacionesResponse> listarCasaciones(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @PathVariable(name = "codigo_distrito", required = true) String codigoDistrito,
      @PathVariable(name = "codigo_provincia", required = true) String codigoProvincia,
      @PathVariable(name = "codigo_instancia", required = true) String codigoInstancia,
      @NotNull(message = "El parámetro fecha no puede ser nulo.") 
      @Pattern(regexp = PatronValidacionConstants.FECHA_YYYY_MM_DD,
          message = "El parámetro fecha_inicio solo permite fecha en formato YYYY-MM-DD.") 
      @RequestParam(name = "fecha_inicio") String fechaInicio,
      @NotNull(message = "El parámetro fecha no puede ser nulo.") 
      @Pattern(regexp = PatronValidacionConstants.FECHA_YYYY_MM_DD,
          message = "El parámetro fecha solo permite fecha_fin en formato YYYY-MM-DD.") 
      @RequestParam(name = "fecha_fin") String fechaFin);


  @GetMapping(value="{codigo_distrito}/{codigo_provincia}/{codigo_instancia}/{numero_unico}/{numero_incidente}/relacionados")
  @Operation(summary = "Obtener casaciones relacionadas", operationId = "listarCasacionesRelacionadas",
      description = "Permite listar casaciones relacionadas por filtros")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = ListarCasacionesRelacionadasResponse.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<ListarCasacionesRelacionadasResponse> listarCasacionesRelacionadas(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @NotNull(message = "El parámetro codigo_distrito no puede ser nulo.")
      @Pattern(regexp = PatronValidacionConstants.NUMBER,
        message = "El parámetro codigo_distrito solo números.")
      @PathVariable(name = "codigo_distrito", required = true) String codigoDistrito,
      @NotNull(message = "El parámetro codigo_provincia no puede ser nulo.")
      @Pattern(regexp = PatronValidacionConstants.NUMBER,
        message = "El parámetro codigo_provincia solo números.")
      @PathVariable(name = "codigo_provincia", required = true) String codigoProvincia,
      @NotNull(message = "El parámetro codigo_instancia no puede ser nulo.")
      @Pattern(regexp = PatronValidacionConstants.NUMBER,
        message = "El parámetro codigo_instancia solo números.")
      @PathVariable(name = "codigo_instancia", required = true) String codigoInstancia,
      @NotNull(message = "El parámetro numero_unico no puede ser nulo.")
      @Pattern(regexp = PatronValidacionConstants.NUMBER,
          message = "El parámetro numero_unico solo números.")
      @PathVariable(name = "numero_unico") String numeroUnico,
      @Pattern(regexp = PatronValidacionConstants.NUMBER,
          message = "El parámetro numero_incidente solo números.")
      @NotNull(message = "El parámetro numero_incidente no puede ser nulo.")
      @PathVariable(name = "numero_incidente") String numeroIncidente);

  

}
