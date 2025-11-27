package pe.gob.pj.votacion.infraestructure.rest.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import pe.gob.pj.votacion.domain.common.utils.ProjectConstants;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.infraestructure.common.utils.PatronValidacionConstants;
import pe.gob.pj.votacion.infraestructure.rest.requests.ReporteEstadoVotacionRequest;
import pe.gob.pj.votacion.infraestructure.rest.requests.ReporteGeneralProyectoRequest;
import pe.gob.pj.votacion.infraestructure.rest.responses.ErrorResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.GenerarReporteEstadoVotacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.GenerarReporteGeneralProyectoResponse;

@RestController
@RequestMapping(value = "reportes",
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@Tag(name = "Reportes", description = "API para manejar reportes")
public interface Reportes {

  @GetMapping(value = "estado-votacion:data")
  @Operation(summary = "Listar estados de votación", operationId = "generarReporteEstadoVotacion",
      description = "Permite listar estado de votación")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = GenerarReporteEstadoVotacionResponse.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<GenerarReporteEstadoVotacionResponse> generarReporteEstadoVotacion(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @Valid ReporteEstadoVotacionRequest request);
  
  @GetMapping(value = "general:data")
  @Operation(summary = "Listar detalle de situacion de proyectos",
      operationId = "generarReporteGeneralProyecto",
      description = "Permite listar detalle de situacion de proyectos")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(
          schema = @Schema(implementation = GenerarReporteGeneralProyectoResponse.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<GenerarReporteGeneralProyectoResponse> generarReporteGeneralProyecto(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @Valid ReporteGeneralProyectoRequest request);
  
  @GetMapping(value = "estado-votacion:descargar",
      produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
          "application/pdf"})
  @Operation(summary = "Descargar reporte estados de votación",
      operationId = "generarReporteEstadoVotacionDescargar",
      description = "Permite descargar reporte estado de votación")
  @ApiResponse(responseCode = "200", description = "Reporte exitoso",
      content = @Content(schema = @Schema(implementation = Resource.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<Resource> generarReporteEstadoVotacionDescargar(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @Valid ReporteEstadoVotacionRequest request,
      @Pattern(regexp = PatronValidacionConstants.TIPO_REPORTE,
      message = "El parámetro tipo_reporte debe ser 'excel' o 'pdf' (sin importar mayúsculas o minúsculas)") 
      @RequestParam(name = "tipo_reporte", required = false) String tipoReporte);
  
  @GetMapping(value = "general:descargar",
      produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
          "application/pdf"})
  @Operation(summary = "Descargar reporte detalle de situacion de proyectos",
      operationId = "generarReporteGeneralProyectoDescargar",
      description = "Permite descargar reporte detalle de situacion de proyectos")
  @ApiResponse(responseCode = "200", description = "Reporte exitoso",
      content = @Content(schema = @Schema(implementation = Resource.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<Resource> generarReporteGeneralProyectoDescargar(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @Valid ReporteGeneralProyectoRequest request,
      @Pattern(regexp = PatronValidacionConstants.TIPO_REPORTE,
      message = "El parámetro tipo_reporte debe ser 'excel' o 'pdf' (sin importar mayúsculas o minúsculas)") 
      @RequestParam(name = "tipo_reporte", required = false) String tipoReporte);
  
}
