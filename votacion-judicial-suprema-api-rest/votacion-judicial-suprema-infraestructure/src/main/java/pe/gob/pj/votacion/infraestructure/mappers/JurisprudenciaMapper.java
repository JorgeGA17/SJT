package pe.gob.pj.votacion.infraestructure.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import pe.gob.pj.votacion.domain.model.tribunal.Jurisprudencia;
import pe.gob.pj.votacion.infraestructure.rest.responses.JurisprudenciaResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JurisprudenciaMapper {

  @Mapping(target = "uuid", source = "uuid", qualifiedByName = "limpiarUuid")
  JurisprudenciaResponse toJurisprudenciaResponse(Jurisprudencia fallo);

  List<JurisprudenciaResponse> toJurisprudenciasResponse(List<Jurisprudencia> fallos);
  
  @Named("limpiarUuid")
  default String limpiarUuid(String uuid) {
    if (uuid == null) return null;
    int pos = uuid.indexOf(";");
    return pos > -1 ? uuid.substring(0, pos) : uuid;
  }

}
