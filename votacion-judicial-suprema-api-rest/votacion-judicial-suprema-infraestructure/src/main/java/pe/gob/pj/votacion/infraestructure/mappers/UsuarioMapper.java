package pe.gob.pj.votacion.infraestructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import pe.gob.pj.votacion.domain.model.sijsuprema.Usuario;
import pe.gob.pj.votacion.infraestructure.rest.responses.UsuarioResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {

  @Mapping(target = "token", ignore = true)
  UsuarioResponse toUsuarioResponse(Usuario usuario);

}
