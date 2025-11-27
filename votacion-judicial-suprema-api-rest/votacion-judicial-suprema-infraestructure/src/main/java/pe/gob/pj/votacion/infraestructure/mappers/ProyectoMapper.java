package pe.gob.pj.votacion.infraestructure.mappers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.ProyectoValidado;
import pe.gob.pj.votacion.domain.model.sijsuprema.ProyectoVoto;
import pe.gob.pj.votacion.domain.model.sijsuprema.Registro;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteEstadoVotacionItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteGeneralProyectoItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarEnvioProyectoCommand;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarValidacionCommand;
import pe.gob.pj.votacion.infraestructure.rest.requests.RegistrarValidacionRequest;
import pe.gob.pj.votacion.infraestructure.rest.requests.RegistroEnvioVotoProyectoRequest;
import pe.gob.pj.votacion.infraestructure.rest.responses.ProyectoValidadoResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ProyectoVotoResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.RegistroResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ReporteEstadoVotacionResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.ReporteGeneralProyectoResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = FechaMapperHelper.class)
public interface ProyectoMapper {

  ProyectoValidadoResponse toProyectoValidadoResponse(ProyectoValidado proyectoValidado);

  List<ProyectoValidadoResponse> toProyectosValidadosResponse(
      List<ProyectoValidado> proyectosValidado);

  @Mapping(target = "numeroRecurso", expression = "java(proyectoVoto.numeroRecurso())")
  ProyectoVotoResponse toProyectoVotoResponse(ProyectoVoto proyectoVoto);

  List<ProyectoVotoResponse> toProyectosVotoResponse(List<ProyectoVoto> proyectosVoto);

  RegistroResponse toRegistroVotoResponse(Registro registro);

  @Mapping(target = "numeroValidado", source = "request.numeroValidado")
  @Mapping(target = "observacion", source = "request.observacion")
  RegistrarValidacionCommand toRegistrarValidacionCommand(String idProyecto, String codigoUsuario,
      RegistrarValidacionRequest request);

  @Mapping(target = "idProyecto", source = "idProyecto")
  @Mapping(target = "numeroUnico", source = "metadata.numeroUnico")
  @Mapping(target = "numeroIncidente", source = "metadata.numeroIncidente")
  @Mapping(target = "sentido", source = "metadata.sentido")
  @Mapping(target = "votacion", source = "metadata.votacion")
  @Mapping(target = "usuarioResponsable", source = "metadata.usuarioResponsable")
  @Mapping(target = "codigoEstado", source = "metadata.codigoEstado")
  @Mapping(target = "nombreDocumento", source = "metadata.nombreDocumento")
  @Mapping(target = "archivo", source = "file")
  @Mapping(target = "ipPublica", source = "peticion.ipPublica")
  @Mapping(target = "usuarioSesion", source = "peticion.usuario")
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "extension", ignore = true)
  RegistrarEnvioProyectoCommand toRegistrarEnvioProyectoCommand(
      RegistroEnvioVotoProyectoRequest metadata, MultipartFile file, PeticionServicios peticion, String idProyecto);


  @Mapping(target = "numeroExpediente",
      expression = "java(reporteEstadoVotacionItem.numeroExpediente())")
  @Mapping(target = "recurrente", expression = "java(reporteEstadoVotacionItem.recurrente())")
  @Mapping(source = "fechaProgramacion", target = "fechaProgramacion",
      qualifiedByName = "zonedDateTimeToString")
  ReporteEstadoVotacionResponse toReporteEstadoVotacionResponse(
      ReporteEstadoVotacionItem reporteEstadoVotacionItem);

  List<ReporteEstadoVotacionResponse> toReportesEstadoVotacionResponse(
      List<ReporteEstadoVotacionItem> reporteEstadoVotacionItem);

  @Mapping(target = "numeroExpediente",
      expression = "java(reporteGeneralProyectoItem.numeroExpediente())")
  @Mapping(target = "recurrente", expression = "java(reporteGeneralProyectoItem.recurrente())")
  @Mapping(source = "fechaEnvio", target = "fechaEnvio", qualifiedByName = "zonedDateTimeToString")
  ReporteGeneralProyectoResponse toGenerarGeneralProyectoResponse(
      ReporteGeneralProyectoItem reporteGeneralProyectoItem);

  List<ReporteGeneralProyectoResponse> toReportesGeneralProyectoResponse(
      List<ReporteGeneralProyectoItem> reporteGeneralProyectoItem);

  default InputStream mapFileToInputStream(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      return null;
    }
    try {
      return file.getInputStream();
    } catch (IOException e) {
      throw new RuntimeException("Error al leer el contenido del archivo adjunto.", e);
    }
  }

}
