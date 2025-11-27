package pe.gob.pj.votacion.infraestructure.rest.controllers;

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
import pe.gob.pj.votacion.domain.common.utils.ProjectConstants;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.infraestructure.rest.responses.ErrorResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarEstadosProyectoResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarEstadosVotacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarFallosResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarResponsablesResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarSentidosResponse;

@RestController
@RequestMapping(value = "maestras", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@Tag(name = "Maestras", description = "API para listar maestras")
public interface Maestras {

  @GetMapping(value = "fallos", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Obtener fallos", operationId = "listarFallos",
      description = "Permite listar fallos por filtros")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = ListarFallosResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<ListarFallosResponse> listarFallos(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @RequestParam(name = "codigo_especialidad", required = true) String codigoEspecialidad,
      @RequestParam(name = "codigo_abreviatura", required = true) String codigoAbreviatura,
      @RequestParam(name = "codigo_motivo_ingreso", required = true) String codigoMotivoIngreso);


  @GetMapping(value = "sentidos", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Obtener sentidos", operationId = "listarSentidos",
      description = "Permite listar sentidos por filtros")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = ListarSentidosResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<ListarSentidosResponse> listarSentidos(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @RequestParam(name = "codigo_distrito") String codigoDistrito,
      @RequestParam(name = "codigo_provincia") String codigoProvincia,
      @RequestParam(name = "codigo_instancia") String codigoInstancia);


  @GetMapping(value = "colegiados", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Listar conformación de colegiados", operationId = "listarColegiados",
      description = "Permite listar colegiados por filtros")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = ListarResponsablesResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<ListarResponsablesResponse> listarColegiados(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @RequestParam(name = "codigo_distrito") String codigoDistrito,
      @RequestParam(name = "codigo_provincia") String codigoProvincia,
      @RequestParam(name = "codigo_instancia") String codigoInstancia);


  @GetMapping(value = "estados-votacion", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Listar estados de la votación", operationId = "listarEstadosVotacion",
      description = "Permite listar los estados de la votación")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = ListarEstadosVotacionResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<ListarEstadosVotacionResponse> listarEstadosVotacion(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta);


  @GetMapping(value = "estados-proyecto", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Listar estados del proyecto", operationId = "listarEstadosProyecto",
      description = "Permite listar los estados del proyecto")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = ListarEstadosProyectoResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<ListarEstadosProyectoResponse> listarEstadosProyecto(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta);

}
