package pe.gob.pj.votacion.infraestructure.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pe.gob.pj.votacion.domain.model.sijsuprema.*;
import pe.gob.pj.votacion.infraestructure.rest.responses.*;
import org.mapstruct.Mapping;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProgramacionMapper {

  MagistradoProgramacionResponse toMagistradoProgramacionResponse(
      MagistradoProgramacion magistradoProgramacion);

  List<MagistradoProgramacionResponse> toMagistradosProgramacionResponse(
      List<MagistradoProgramacion> lista);

  ImpedidoProgramacionResponse toImpedidoProgramacionResponse(
      ImpedidoProgramacion impedidoProgramacion);

  List<ImpedidoProgramacionResponse> toImpedidosProgramacionResponse(
      List<ImpedidoProgramacion> lista);

  MateriaProgramacionResponse toMateriaProgramacionResponse(
      MateriaProgramacion materiaProgramacion);

  List<MateriaProgramacionResponse> toMateriasProgramacionResponse(List<MateriaProgramacion> lista);

  CausalProgramacionResponse toCausalProgramacionResponse(CausalProgramacion causalProgramacion);

  List<CausalProgramacionResponse> toCausalesProgramacionResponse(List<CausalProgramacion> lista);

  @Mapping(target = "descripcionParte", expression = "java(generarDescripcion(parteProgramacion))")
  ParteProgramacionResponse toParteProgramacionResponse(ParteProgramacion parteProgramacion);

  List<ParteProgramacionResponse> toPartesProgramacionResponse(List<ParteProgramacion> lista);

  MagistradoDiscordiaProgramacionResponse toMagistradoDiscordiaProgramacionResponse(
      MagistradoDiscordiaProgramacion magistradoDiscordiaProgramacion);

  List<MagistradoDiscordiaProgramacionResponse> toMagistradosDiscordiaProgramacionResponse(
      List<MagistradoDiscordiaProgramacion> lista);

  ApuntesProgramacionResponse toApuntesProgramacionResponse(
      ApuntesProgramacion apuntesProgramacion);

  DocumentoProgramacionResponse toDocumentoProgramacionResponse(
      DocumentoProgramacion documentoProgramacion);

  List<DocumentoProgramacionResponse> toDocumentosProgramacionResponse(
      List<DocumentoProgramacion> lista);

  DocumentoDigitalProgramacionResponse toDocumentoDigitalProgramacionResponse(
      DocumentoDigitalProgramacion documentoDigitalProgramacion);

  List<DocumentoDigitalProgramacionResponse> toDocumentosDigitalesProgramacionResponse(
      List<DocumentoDigitalProgramacion> lista);

  default String generarDescripcion(ParteProgramacion parte) {
    StringBuilder sb = new StringBuilder();

    if ("S".equals(parte.flagRecurrente())) {
      if (parte.numeroFojas() != null && parte.numeroFojas() != 0) {
        sb.append("(Fjs. ").append(parte.numeroFojas()).append(") ");
      }
      sb.append("(Recurrente) ");

    }

    sb.append(parte.descripcion());
    return sb.toString();
  }

}
