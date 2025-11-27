package pe.gob.pj.votacion.infraestructure.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import pe.gob.pj.votacion.domain.model.sijsuprema.Casacion;
import pe.gob.pj.votacion.domain.model.sijsuprema.CasacionRelacionada;
import pe.gob.pj.votacion.infraestructure.rest.responses.CasacionRelacionadaResponse;
import pe.gob.pj.votacion.infraestructure.rest.responses.CasacionResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = FechaMapperHelper.class)
public interface ExpedienteMapper {

  @Mapping(target = "numeroRecurso", expression = "java(casacion.numeroRecurso())")
  @Mapping(target = "descripcionActoProcesalJuzgadoAdicional",
      expression = "java(casacion.descripcionActoProcesalJuzgadoAdicional())")
  @Mapping(target = "descripcionActoProcesalSalaAdicional",
      expression = "java(casacion.descripcionActoProcesalSalaAdicional())")
  @Mapping(source = "fechaIngreso", target = "fechaIngreso",
      qualifiedByName = "zonedDateTimeToString")
  @Mapping(source = "fechaProgramacion", target = "fechaProgramacion",
      qualifiedByName = "zonedDateTimeToString")
  @Mapping(source = "fechaEstado", target = "fechaEstado",
      qualifiedByName = "zonedDateTimeToString")
  CasacionResponse toCasacionResponse(Casacion casacion);

  List<CasacionResponse> toCasacionesResponse(List<Casacion> casaciones);

  @Mapping(target = "numeroExpediente", expression = "java(casacion.numeroRecurso())")
  @Mapping(source = "fechaProgramacion", target = "fechaProgramacion",
      qualifiedByName = "zonedDateTimeToString")
  @Mapping(source = "fechaIngreso", target = "fechaIngreso",
      qualifiedByName = "zonedDateTimeToString")
  CasacionRelacionadaResponse toCasacionRelacionadaResponse(CasacionRelacionada casacion);

  List<CasacionRelacionadaResponse> toCasacionesRelacionadasResponse(
      List<CasacionRelacionada> casaciones);
}
