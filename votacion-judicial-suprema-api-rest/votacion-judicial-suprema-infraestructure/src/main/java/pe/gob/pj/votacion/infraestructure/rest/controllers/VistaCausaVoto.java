package pe.gob.pj.votacion.infraestructure.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.pj.votacion.domain.common.utils.ProjectConstants;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.infraestructure.common.utils.PatronValidacionConstants;
import pe.gob.pj.votacion.infraestructure.rest.requests.RegistrarVotoRequest;
import pe.gob.pj.votacion.infraestructure.rest.responses.ErrorResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.GlobalResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.RegistrarResponse;

@RestController
@RequestMapping(value = "vistas-causa", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@Tag(name = "VistaCausa", description = "Para tema relacionados a vista causa")
public interface VistaCausaVoto {

    @GetMapping(value = "{codigo_programacion}/validacion")
    @Operation(summary = "Validar datos previo registro",
            operationId = "validarDatos",
            description = "Permite validar datos antes del registro")
    @ApiResponse(responseCode = "200", description = "Peticion exitosa",
            content = @Content(
                    schema = @Schema(implementation = RegistrarResponse.class)))
    @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403",
            description = "El cliente no esta autorizado para esta operación",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RegistrarResponse> validarDatos(
            @Parameter(hidden = true) @RequestAttribute(
                    name = ProjectConstants.PETICION) PeticionServicios peticion,
            @RequestParam(name = "formato_respuesta", required = false,
                    defaultValue = "JSON") String formatoRespuesta,
            @NotNull(message = "El parámetro codigo_distrito no puede ser nulo.")
            @RequestParam(name = "codigo_distrito") String codigoDistrito,
            @NotNull(message = "El parámetro codigo_provincia no puede ser nulo.")
            @RequestParam(name = "codigo_provincia") String codigoProvincia,
            @NotNull(message = "El parámetro codigo_instancia no puede ser nulo.")
            @RequestParam(name = "codigo_instancia") String codigoInstancia,
            @NotNull(message = "El parámetro codigo_programacion no puede ser nulo.")
            @PathVariable(name = "codigo_programacion") String codigoProgramacion,
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_grupo_voto solo números.")
            @NotNull(message = "El parámetro numero_grupo_voto no puede ser nulo.")
            @RequestParam(name = "numero_grupo_voto") String numeroGrupoVoto,
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_secuencia_voto solo números.")
            @NotNull(message = "El parámetro numero_secuencia_voto no puede ser nulo.")
            @RequestParam(name = "numero_secuencia_voto") String numeroSecuenciaVoto,
            @Pattern(regexp = PatronValidacionConstants.NUMBER,
                    message = "El parámetro numero_conformacion_voto solo números.")
            @NotNull(message = "El parámetro numero_conformacion_voto no puede ser nulo.")
            @RequestParam(name = "numero_conformacion_voto") String numeroConformacionVoto);


    @PostMapping(value = "{codigo_programacion}/casacion/{codigo_distrito}/{codigo_provincia}/{codigo_instancia}/{numero_unico}/{numero_incidente}/votacion")
    @Operation(summary = "Registrar voto",
            operationId = "registrarVoto",
            description = "Permite registrar votacion")
    @ApiResponse(responseCode = "200", description = "Peticion exitosa",
            content = @Content(
                    schema = @Schema(implementation = GlobalResponse.class)))
    @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403",
            description = "El cliente no esta autorizado para esta operación",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<GlobalResponse> registrarVoto(
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
            @Valid @RequestBody RegistrarVotoRequest request);


  @PostMapping(value = "{codigo_programacion}/casacion/{codigo_distrito}/{codigo_provincia}/{codigo_instancia}/{numero_unico}/{numero_incidente}/automatico")
  @Operation(summary = "Autoguardado",
      operationId = "autoguardado",
      description = "Permite realizar el guardado automatico")
  @ApiResponse(responseCode = "200", description = "Peticion exitosa",
      content = @Content(
          schema = @Schema(implementation = GlobalResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<GlobalResponse> autoguardado(
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
      @Valid @RequestBody RegistrarVotoRequest request);
}
