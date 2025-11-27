package pe.gob.pj.votacion.infraestructure.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.pj.votacion.domain.common.utils.ProjectConstants;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.infraestructure.rest.responses.BuscarJurisprudenciaResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ErrorResponse;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@Tag(name = "Jurisprudencia", description = "API para manejar jurisprudencias")
public interface Jurisprudencia {

  @GetMapping(value = "jurisprudencias")
  @Operation(summary = "Buscar jurisprudencia", operationId = "buscarJurisprudencia",
      description = "Permite buscar jurisprudencia por filtros")
  @ApiResponse(responseCode = "200", description = "Listado exitoso",
      content = @Content(schema = @Schema(implementation = BuscarJurisprudenciaResponse.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<BuscarJurisprudenciaResponse> buscarJurisprudencia(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "formato_respuesta", required = false,
          defaultValue = "JSON") String formatoRespuesta,
      @RequestParam(name = "fuente", required = true) String fuente,
      @RequestParam(name = "numero_expediente", required = true) String numeroExpediente);


  @GetMapping(value = "jurisprudencias:descargar", produces = {"application/pdf"})
  @Operation(summary = "Obtener documento de alfresco", operationId = "visualizarPdf",
      description = "Permite obtener documento de alfresco del uuid enviado")
  @ApiResponse(responseCode = "200", description = "Busqueda exitosa",
      content = @Content(schema = @Schema(implementation = Resource.class)))
  @ApiResponse(responseCode = "400", description = "Solicitud inválida",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "401", description = "El cliente no se autentico de manera correcta",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(responseCode = "403",
      description = "El cliente no esta autorizado para esta operación",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<Resource> visualizarPdf(
      @Parameter(hidden = true) @RequestAttribute(
          name = ProjectConstants.PETICION) PeticionServicios peticion,
      @RequestParam(name = "origen_documento", required = true) String origen,
      @RequestParam(name = "uuid_documento", required = true) String uuid);

}
