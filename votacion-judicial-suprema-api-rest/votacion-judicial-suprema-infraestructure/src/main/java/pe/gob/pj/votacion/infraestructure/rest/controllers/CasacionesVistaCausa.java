package pe.gob.pj.votacion.infraestructure.rest.controllers;

import org.springframework.core.io.Resource;
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
import pe.gob.pj.votacion.infraestructure.rest.responses.*;

@RestController
@RequestMapping(value = "casaciones", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@Tag(name = "Programacion Instancia",
        description = "Muestra los datos relacionados a programacion instancia vocal")
public interface CasacionesVistaCausa {


    @GetMapping(value = "vistas-causa/{codigo_programacion}/magistrados")
    @Operation(summary = "Lista Magistrados Programación",
            operationId = "listarMagistradosProgramacion",
            description = "Permite listar magistrados de programación")
    @ApiResponse(responseCode = "200", description = "Peticion exitosa",
            content = @Content(
                    schema = @Schema(implementation = ListarMagistradosProgramacionResponse.class)))
    @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403",
            description = "El cliente no esta autorizado para esta operación",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ListarMagistradosProgramacionResponse> listarMagistradosProgramacion(
            @Parameter(hidden = true) @RequestAttribute(
                    name = ProjectConstants.PETICION) PeticionServicios peticion,
            @RequestParam(name = "formato_respuesta", required = false,
                    defaultValue = "JSON") String formatoRespuesta,
            @NotNull(message = "El parámetro codigo_programacion no puede ser nulo.")
            @PathVariable(name = "codigo_programacion") String codigoProgramacion,
            @NotNull(message = "El parámetro numero_grupo no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_grupo solo números.")
            @RequestParam(name = "numero_grupo") String numeroGrupo,
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_secuencia solo números.")
            @NotNull(message = "El parámetro numero_secuencia no puede ser nulo.")
            @RequestParam(name = "numero_secuencia") String numeroSecuencia,
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_conformacion solo números.")
            @NotNull(message = "El parámetro numero_conformacion no puede ser nulo.")
            @RequestParam(name = "numero_conformacion") String numeroConformacion);

    @GetMapping(
        value = "{codigo_distrito}/{codigo_provincia}/{codigo_instancia}/{numero_unico}/{numero_incidente}/vistas-causa/{codigo_programacion}/impedidos")
    @Operation(summary = "Lista Impedidos Programación",
        operationId = "listarImpedidosProgramacion",
        description = "Permite listar impedidos de programación")
    @ApiResponse(responseCode = "200", description = "Peticion exitosa",
        content = @Content(
            schema = @Schema(implementation = ListarImpedidosProgramacionResponse.class)))
    @ApiResponse(responseCode = "401",
        description = "El cliente no se autentico de manera correcta",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403",
        description = "El cliente no esta autorizado para esta operación",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ListarImpedidosProgramacionResponse> listarImpedidosProgramacion(
            @Parameter(hidden = true) @RequestAttribute(
                    name = ProjectConstants.PETICION) PeticionServicios peticion,
            @RequestParam(name = "formato_respuesta", required = false,
                    defaultValue = "JSON") String formatoRespuesta,
            @NotNull(message = "El parámetro codigo_distrito no puede ser nulo.")
            @PathVariable(name = "codigo_distrito") String codigoDistrito,
            @NotNull(message = "El parámetro codigo_provincia no puede ser nulo.")
            @PathVariable(name = "codigo_provincia") String codigoProvincia,
            @NotNull(message = "El parámetro codigo_instancia no puede ser nulo.")
            @PathVariable(name = "codigo_instancia") String codigoInstancia,
            @NotNull(message = "El parámetro codigo_programacion no puede ser nulo.")
            @PathVariable(name = "codigo_programacion") String codigoProgramacion,
            @NotNull(message = "El parámetro numero_unico no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_unico solo números.")
            @PathVariable(name = "numero_unico") String numeroUnico,
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_incidente solo números.")
            @NotNull(message = "El parámetro numero_incidente no puede ser nulo.")
            @PathVariable(name = "numero_incidente") String numeroIncidente,
            @Pattern(regexp = PatronValidacionConstants.FECHA_YYYY_MM_DD_HH_MM_SS_SSS,
                    message = "El parámetro fecha_ingreso sólo permite el formato yyyy-MM-dd HH:mm:ss.sss")
            @NotNull(message = "El parámetro fecha_ingreso no puede ser nulo.")
            @RequestParam(name = "fecha_ingreso") String fechaIngreso);


    @GetMapping(value = "{numero_unico}/{numero_incidente}/vistas-causa/{codigo_programacion}/materias")
    @Operation(summary = "Lista Materias Programación",
            operationId = "listarMateriasProgramacion",
            description = "Permite listar materias de programación")
    @ApiResponse(responseCode = "200", description = "Peticion exitosa",
            content = @Content(
                    schema = @Schema(implementation = ListarMateriasProgramacionResponse.class)))
    @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403",
            description = "El cliente no esta autorizado para esta operación",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ListarMateriasProgramacionResponse> listarMateriasProgramacion(
            @Parameter(hidden = true) @RequestAttribute(
                    name = ProjectConstants.PETICION) PeticionServicios peticion,
            @RequestParam(name = "formato_respuesta", required = false,
                    defaultValue = "JSON") String formatoRespuesta,
            @NotNull(message = "El parámetro codigo_programacion no puede ser nulo.")
            @PathVariable(name = "codigo_programacion") String codigoProgramacion,
            @NotNull(message = "El parámetro numero_unico no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_unico solo números.")
            @PathVariable(name = "numero_unico") String numeroUnico,
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_incidente solo números.")
            @NotNull(message = "El parámetro numero_incidente no puede ser nulo.")
            @PathVariable(name = "numero_incidente") String numeroIncidente);


    @GetMapping(value = "{numero_unico}/{numero_incidente}/vistas-causa/{codigo_programacion}/causales")
    @Operation(summary = "Lista Causales Programación",
            operationId = "listarCausalesProgramacion",
            description = "Permite listar causales de programación")
    @ApiResponse(responseCode = "200", description = "Peticion exitosa",
            content = @Content(
                    schema = @Schema(implementation = ListarCausalesProgramacionResponse.class)))
    @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403",
            description = "El cliente no esta autorizado para esta operación",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ListarCausalesProgramacionResponse> listarCausalesProgramacion(
            @Parameter(hidden = true) @RequestAttribute(
                    name = ProjectConstants.PETICION) PeticionServicios peticion,
            @RequestParam(name = "formato_respuesta", required = false,
                    defaultValue = "JSON") String formatoRespuesta,
            @NotNull(message = "El parámetro codigo_programacion no puede ser nulo.")
            @PathVariable(name = "codigo_programacion") String codigoProgramacion,
            @NotNull(message = "El parámetro numero_unico no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_unico solo números.")
            @PathVariable(name = "numero_unico") String numeroUnico,
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_incidente solo números.")
            @NotNull(message = "El parámetro numero_incidente no puede ser nulo.")
            @PathVariable(name = "numero_incidente") String numeroIncidente);


    @GetMapping(value = "{numero_unico}/{numero_incidente}/vistas-causa/{codigo_programacion}/partes-recurrentes")
    @Operation(summary = "Lista Partes-Recurrentes Programación",
            operationId = "listarPartesProgramacion",
            description = "Permite listar partes y recurrentes de programación")
    @ApiResponse(responseCode = "200", description = "Peticion exitosa",
            content = @Content(
                    schema = @Schema(implementation = ListarPartesProgramacionResponse.class)))
    @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403",
            description = "El cliente no esta autorizado para esta operación",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ListarPartesProgramacionResponse> listarPartesProgramacion(
            @Parameter(hidden = true) @RequestAttribute(
                    name = ProjectConstants.PETICION) PeticionServicios peticion,
            @RequestParam(name = "formato_respuesta", required = false,
                    defaultValue = "JSON") String formatoRespuesta,
            @NotNull(message = "El parámetro codigo_programacion no puede ser nulo.")
            @PathVariable(name = "codigo_programacion") String codigoProgramacion,
            @NotNull(message = "El parámetro numero_unico no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_unico solo números.")
            @PathVariable(name = "numero_unico") String numeroUnico,
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_incidente solo números.")
            @NotNull(message = "El parámetro numero_incidente no puede ser nulo.")
            @PathVariable(name = "numero_incidente") String numeroIncidente,
            @Pattern(regexp = PatronValidacionConstants.S_N,
                    message = "El parámetro flag_discordia solo permite valores S o N.")
            @NotNull(message = "El parámetro flag_discordia no puede ser nulo.")
            @RequestParam(name = "flag_discordia") String flagDiscordia,
            @NotNull(message = "El parámetro numero_sentido no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_sentido solo números.")
            @RequestParam(name = "numero_sentido") String numeroSentido,
            @NotNull(message = "El parámetro numero_votacion no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_votacion solo números.")
            @RequestParam(name = "numero_votacion") String numeroVotacion);


    @GetMapping(value = "{numero_unico}/{numero_incidente}/vistas-causa/{codigo_programacion}/magistrados-discordia")
    @Operation(summary = "Lista Magistrados-Discordia Programación",
            operationId = "ListarMagistradosDiscordiaProgramacion",
            description = "Permite listar magistrados en discordia de programación")
    @ApiResponse(responseCode = "200", description = "Peticion exitosa",
            content = @Content(
                    schema = @Schema(implementation = ListarMagistradosDiscordiaProgramacionResponse.class)))
    @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403",
            description = "El cliente no esta autorizado para esta operación",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ListarMagistradosDiscordiaProgramacionResponse> listarMagistradosDiscordiaProgramacion(
            @Parameter(hidden = true) @RequestAttribute(
                    name = ProjectConstants.PETICION) PeticionServicios peticion,
            @RequestParam(name = "formato_respuesta", required = false,
                    defaultValue = "JSON") String formatoRespuesta,
            @NotNull(message = "El parámetro codigo_programacion no puede ser nulo.")
            @PathVariable(name = "codigo_programacion") String codigoProgramacion,
            @NotNull(message = "El parámetro numero_unico no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_unico solo números.")
            @PathVariable(name = "numero_unico") String numeroUnico,
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_incidente solo números.")
            @NotNull(message = "El parámetro numero_incidente no puede ser nulo.")
            @PathVariable(name = "numero_incidente") String numeroIncidente,
            @NotNull(message = "El parámetro numero_sentido no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_sentido solo números.")
            @RequestParam(name = "numero_sentido") String numeroSentido,
            @NotNull(message = "El parámetro numero_votacion no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_votacion solo números.")
            @RequestParam(name = "numero_votacion") String numeroVotacion,
            @NotNull(message = "El parámetro numero_secuencia_parte no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_secuencia_parte solo números.")
            @RequestParam(name = "numero_secuencia_parte") String numeroSecuenciaParte,
            @NotNull(message = "El parámetro codigo_vocal no puede ser nulo.")
            @RequestParam(name = "codigo_vocal_ponente") String codigoVocalPonente);


    @GetMapping(value = "{numero_unico}/{numero_incidente}/vistas-causa/apuntes")
    @Operation(summary = "Apuntes Programación",
            operationId = "ObtenerApuntesProgramacion",
            description = "Permite obtener apuntes de programación")
    @ApiResponse(responseCode = "200", description = "Peticion exitosa",
            content = @Content(
                    schema = @Schema(implementation = ObtenerApuntesProgramacionResponse.class)))
    @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403",
            description = "El cliente no esta autorizado para esta operación",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ObtenerApuntesProgramacionResponse> obtenerApuntesProgramacion(
            @Parameter(hidden = true) @RequestAttribute(
                    name = ProjectConstants.PETICION) PeticionServicios peticion,
            @RequestParam(name = "formato_respuesta", required = false,
                    defaultValue = "JSON") String formatoRespuesta,
            @NotNull(message = "El parámetro numero_unico no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_unico solo números.")
            @PathVariable(name = "numero_unico") String numeroUnico,
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_incidente solo números.")
            @NotNull(message = "El parámetro numero_incidente no puede ser nulo.")
            @PathVariable(name = "numero_incidente") String numeroIncidente,
            @NotNull(message = "El parámetro numero_sentido no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_sentido solo números.")
            @RequestParam(name = "numero_sentido") String numeroSentido,
            @NotNull(message = "El parámetro codigo_vocal no puede ser nulo.")
            @RequestParam(name = "codigo_vocal_usuario") String codigoVocalUsuario);


    @GetMapping(value = "{numero_unico}/{numero_incidente}/vistas-causa/{codigo_programacion}/documentos")
    @Operation(summary = "Documentos Programación",
            operationId = "ListarDocumentosProgramacion",
            description = "Permite listar documentos de programación")
    @ApiResponse(responseCode = "200", description = "Peticion exitosa",
            content = @Content(
                    schema = @Schema(implementation = ListarDocumentosProgramacionResponse.class)))
    @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403",
            description = "El cliente no esta autorizado para esta operación",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ListarDocumentosProgramacionResponse>listarDocumentosProgramacion(
            @Parameter(hidden = true) @RequestAttribute(
                    name = ProjectConstants.PETICION) PeticionServicios peticion,
            @RequestParam(name = "formato_respuesta", required = false,
                    defaultValue = "JSON") String formatoRespuesta,
            @NotNull(message = "El parámetro codigo_programacion no puede ser nulo.")
            @PathVariable(name = "codigo_programacion") String codigoProgramacion,
            @NotNull(message = "El parámetro numero_unico no puede ser nulo.")
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_unico solo números.")
            @PathVariable(name = "numero_unico") String numeroUnico,
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_incidente solo números.")
            @NotNull(message = "El parámetro numero_incidente no puede ser nulo.")
            @PathVariable(name = "numero_incidente") String numeroIncidente);


  @GetMapping(value = "{numero_unico}/{numero_incidente}/vistas-causa/documentos-digitales")
  @Operation(summary = "Documentos Digitales Programación",
      operationId = "ListarDocumentosDigitalesProgramacion",
      description = "Permite listar documentos digitales de programación")
  @ApiResponse(responseCode = "200", description = "Peticion exitosa",
      content = @Content(
          schema = @Schema(implementation = ListarDocumentosDigitalesProgramacionResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<ListarDocumentosDigitalesProgramacionResponse>listarDocumentosDigitalesProgramacion(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @NotNull(message = "El parámetro numero_unico no puede ser nulo.")
      @Pattern(regexp = PatronValidacionConstants.NUMBER,
          message = "El parámetro numero_unico solo números.")
      @PathVariable(name = "numero_unico") String numeroUnico,
      @Pattern(regexp = PatronValidacionConstants.NUMBER,
          message = "El parámetro numero_incidente solo números.")
      @NotNull(message = "El parámetro numero_incidente no puede ser nulo.")
      @PathVariable(name = "numero_incidente") String numeroIncidente);


  @GetMapping(value = "{numero_unico}/{numero_incidente}/vistas-causa/{numero_documento}/visualizar-documento",  produces = {"application/pdf"})
  @Operation(summary = "Obtener documento digital de FTP",
      operationId = "visualizarDocumentoDigital",
      description = "Permite obtener documento de FTP del numero de documento enviado")
  @ApiResponse(responseCode = "200", description = "Busqueda exitosa",
      content = @Content(schema = @Schema(implementation = Resource.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<Resource>visualizarDocumentoDigital(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @NotNull(message = "El parámetro numero_documento no puede ser nulo.")
      @Pattern(regexp = PatronValidacionConstants.NUMBER,
          message = "El parámetro numero_documento solo números.")
      @PathVariable(name = "numero_documento") String numeroDocumento,
      @NotNull(message = "El parámetro numero_unico no puede ser nulo.")
      @Pattern(regexp = PatronValidacionConstants.NUMBER,
          message = "El parámetro numero_unico solo números.")
      @PathVariable(name = "numero_unico") String numeroUnico,
      @Pattern(regexp = PatronValidacionConstants.NUMBER,
          message = "El parámetro numero_incidente solo números.")
      @NotNull(message = "El parámetro numero_incidente no puede ser nulo.")
      @PathVariable(name = "numero_incidente") String numeroIncidente);

}
