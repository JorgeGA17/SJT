package pe.gob.pj.votacion.infraestructure.rest.controllers;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import pe.gob.pj.votacion.domain.common.utils.ProjectConstants;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.infraestructure.common.utils.PatronValidacionConstants;
import pe.gob.pj.votacion.infraestructure.rest.requests.RegistrarValidacionRequest;
import pe.gob.pj.votacion.infraestructure.rest.requests.RegistroEnvioVotoProyectoRequest;
import pe.gob.pj.votacion.infraestructure.rest.responses.ContarProyectosResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ErrorResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.GlobalResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ListarProyectosResponse;

@RestController
@RequestMapping(value = "proyectos",
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@Tag(name = "Proyectos", description = "API para manejar proyectos")
public interface Proyectos {

  @GetMapping
  @Operation(summary = "Listar proyectos pendientes", operationId = "listarPendientes",
      description = "Permite listar proyectos pendientes por filtros")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = ListarProyectosResponse.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<ListarProyectosResponse> listarPendientes(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @RequestParam(name = "codigo_distrito", required = true) String codigoDistrito,
      @RequestParam(name = "codigo_provincia", required = true) String codigoProvincia,
      @RequestParam(name = "codigo_instancia", required = true) String codigoInstancia,
      @Length(min = 3, max = 15,
          message = "El parámetro usuario_responsable tiene un tamaño no valido [max=15].") @RequestParam(
              name = "usuario_responsable", required = true) String usuarioResponsable,
      @RequestParam(name = "id_estado", required = true) Integer idEstado,
      @NotNull(message = "El parámetro fecha no puede ser nulo.") @Pattern(
          regexp = PatronValidacionConstants.FECHA_YYYY_MM_DD,
          message = "El parámetro fecha_inicio solo permite fecha en formato YYYY-MM-DD.") @RequestParam(
              name = "fecha_inicio") String fechaInicio,
      @NotNull(message = "El parámetro fecha no puede ser nulo.") @Pattern(
          regexp = PatronValidacionConstants.FECHA_YYYY_MM_DD,
          message = "El parámetro fecha solo permite fecha_fin en formato YYYY-MM-DD.") @RequestParam(
              name = "fecha_fin") String fechaFin);


  @GetMapping(value = "relacionados", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Listar proyectos relacionados", operationId = "listarRelacionados",
      description = "Permite listar proyectos relacionados por filtros")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = ListarProyectosResponse.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<ListarProyectosResponse> listarRelacionados(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @NotNull(message = "El parámetro numero_unico no puede ser nulo.") @Pattern(
          regexp = PatronValidacionConstants.NUMBER,
          message = "El parámetro numero_unico solo números.") @RequestParam(
              name = "numero_unico") String numeroUnico,
      @Pattern(regexp = PatronValidacionConstants.NUMBER,
          message = "El parámetro numero_incidente solo números.") @NotNull(
              message = "El parámetro numero_incidente no puede ser nulo.") @RequestParam(
                  name = "numero_incidente") String numeroIncidente,
      @NotNull(message = "El parámetro numero_sentido no puede ser nulo.") @Pattern(
          regexp = PatronValidacionConstants.NUMBER,
          message = "El parámetro numero_sentido solo números.") @RequestParam(
              name = "numero_sentido") String numeroSentido,
      @NotNull(message = "El parámetro numero_votacion no puede ser nulo.") @Pattern(
          regexp = PatronValidacionConstants.NUMBER,
          message = "El parámetro numero_votacion solo números.") @RequestParam(
              name = "numero_votacion") String numeroVotacion,
      @Length(min = 3, max = 15,
          message = "El parámetro usuarioResponsable tiene un tamaño no valido [max=15].") @RequestParam(
              name = "usuario_responsable", required = true) String usuarioResponsable);


  @GetMapping(value = "pendientes", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Contar proyectos pendientes", operationId = "contarPendientes",
      description = "Permite contar proyectos pendientes por filtros")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = ContarProyectosResponse.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<ContarProyectosResponse> contarPendientes(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @Length(min = 3, max = 15,
          message = "El parámetro usuario_responsable tiene un tamaño no valido [max=15].") @RequestParam(
              name = "usuario_responsable", required = true) String usuarioResponsable,
      @RequestParam(name = "id_estado", required = true) Integer idEstado);

  @GetMapping(value = "validados")
  @Operation(summary = "Listar proyectos validados", operationId = "listarValidados",
      description = "Permite listar proyectos validados por filtros")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = ListarProyectosResponse.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<ListarProyectosResponse> listarValidados(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @RequestParam(name = "codigo_distrito", required = true) String codigoDistrito,
      @RequestParam(name = "codigo_provincia", required = true) String codigoProvincia,
      @RequestParam(name = "codigo_instancia", required = true) String codigoInstancia,
      @Length(min = 3, max = 15,
          message = "El parámetro usuario_responsable tiene un tamaño no valido [max=15].") @RequestParam(
              name = "usuario_responsable", required = true) String usuarioResponsable,
      @RequestParam(name = "id_estado", required = true) Integer idEstado,
      @NotNull(message = "El parámetro fecha no puede ser nulo.") @Pattern(
          regexp = PatronValidacionConstants.FECHA_YYYY_MM_DD,
          message = "El parámetro fecha_inicio solo permite fecha en formato YYYY-MM-DD.") @RequestParam(
              name = "fecha_inicio") String fechaInicio,
      @NotNull(message = "El parámetro fecha no puede ser nulo.") @Pattern(
          regexp = PatronValidacionConstants.FECHA_YYYY_MM_DD,
          message = "El parámetro fecha solo permite fecha_fin en formato YYYY-MM-DD.") @RequestParam(
              name = "fecha_fin") String fechaFin);

  @PostMapping(value = "{id_proyecto}/validacion/{codigo_usuario}")
  @Operation(summary = "Registrar validación", operationId = "registrarValidacion",
      description = "Permite registrar validaciones")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = GlobalResponse.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<GlobalResponse> registrarValidacion(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @NotNull(message = "El parámetro id_proyecto no puede ser nulo.") 
      @Pattern(regexp = PatronValidacionConstants.NUMBER,message = "El parámetro id_proyecto solo números.") 
      @PathVariable(name = "id_proyecto") String idProyecto,
      @NotNull(message = "El parámetro codigo_usuario no puede ser nulo.") 
      @PathVariable(name = "codigo_usuario") String codigoUsuario,
      @Valid @RequestBody RegistrarValidacionRequest request);

  
  @PostMapping(value = "{id_proyecto}/voto")
  @Operation(summary = "Registrar voto proyecto", operationId = "registrarEnvio",
      description = "Permite regisrar envio voto de proyecto")
  @ApiResponse(responseCode = "200", description = "Registrar envio voto",
      content = @Content(schema = @Schema(implementation = GlobalResponse.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<GlobalResponse> registrarEnvioVoto(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @NotNull(message = "El parámetro id_proyecto no puede ser nulo.") 
      @Pattern(regexp = PatronValidacionConstants.NUMBER,message = "El parámetro id_proyecto solo números.") 
      @PathVariable(name = "id_proyecto") String idProyecto,
      @RequestPart("metadata") @Valid RegistroEnvioVotoProyectoRequest metadata,
      @RequestPart("file") MultipartFile file);

}
