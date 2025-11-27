package pe.gob.pj.votacion.infraestructure.db.sijsuprema.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.domain.common.enums.Estado;
import pe.gob.pj.votacion.domain.model.sijsuprema.Instancia;
import pe.gob.pj.votacion.domain.model.sijsuprema.Perfil;
import pe.gob.pj.votacion.domain.model.sijsuprema.PerfilOpcions;
import pe.gob.pj.votacion.domain.model.sijsuprema.Usuario;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.AccesoPersistenceReadPort;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.UsuarioRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccesoReadPersistenceAdapter implements AccesoPersistenceReadPort {

  UsuarioRepository usuarioRepository;

  @Override
  public Usuario iniciarSesion(String cuo, String usuario) {
    return usuarioRepository.findByIdCodigoUsuario(usuario)
        .filter(usuarioEntity -> Estado.ACTIVO_LETRA.getNombre()
            .equalsIgnoreCase(usuarioEntity.getActivo()))
        .map(movUsuario -> {
          var u = new Usuario();
          u.setCodigoUsuario(movUsuario.getId().getCodigoUsuario().trim());
          u.setCodigoProvincia(movUsuario.getId().getCodigoProvincia());
          u.setDocumentoIdentidad(movUsuario.getDni());
          u.setApellidoPaterno(movUsuario.getApellidoPaterno());
          u.setApellidoMaterno(movUsuario.getApellidoMaterno());
          u.setNombres(movUsuario.getNombres());
          u.setPerfiles(Optional.ofNullable(movUsuario.getPerfilEntity())
              .map(perfilUsuario -> List
                  .of(new Perfil(perfilUsuario.getCodigoPerfil(), perfilUsuario.getDescripcion())))
              .orElse(List.of()));
          Optional.ofNullable(movUsuario.getDistritoJudicialEntity())
          .ifPresent(distrito->{
            u.setCodigoDistrito(distrito.getCodigoDistrito());
            u.setNombreDistrito(distrito.getNombreDistrito());
          });
          
          var instancias = Optional.ofNullable(movUsuario.getUsuarioInstancias())
              .orElseGet(List::of)
              .stream()
              .map(usuarioInstancia -> {
                  var instanciaEntity = usuarioInstancia.getInstanciaEntity();
                  var id = instanciaEntity.getId();
                  return new Instancia(
                      id.getCodigoInstancia(),
                      instanciaEntity.getNombreInstancia(),
                      instanciaEntity.getOrganoJurisdiccionalEntity().getCodigoOrganoJurisdiccional(),
                      id.getCodigoDistrito(),
                      id.getCodigoProvincia()
                  );
              })
              .toList();
          u.setInstancias(instancias);
          
          return u;
        }).orElse(null);
  }

  @Override
  public PerfilOpcions obtenerOpciones(String cuo, Integer idPerfil) {
    PerfilOpcions perfilOpciones = new PerfilOpcions();
//    maePerfilRepository.findById(idPerfil).ifPresent(maePerfil -> {
//      perfilOpciones.setRol(maePerfil.getRol());
//      maePerfil.getPerfilsOpcion().forEach(x -> {
//        if (x.getActivo().equalsIgnoreCase(Estado.ACTIVO_NUMERICO.getNombre())) {
//          MaeOpcionEntity maeOpcion = x.getOpcion();
//          Opcion opcion = new Opcion();
//          opcion.setId(maeOpcion.getId());
//          opcion.setCodigo(maeOpcion.getCodigo());
//          opcion.setUrl(maeOpcion.getUrl());
//          opcion.setIcono(maeOpcion.getIcono());
//          opcion.setNombre(maeOpcion.getNombre());
//          opcion.setOrden(maeOpcion.getOrden());
//          opcion.setActivo(maeOpcion.getActivo());
//          opcion.setIdOpcionSuperior(
//              Objects.nonNull(maeOpcion.getOpcionSuperior()) ? maeOpcion.getOpcionSuperior().getId()
//                  : null);
//          opcion.setNombreOpcionSuperior(Objects.nonNull(maeOpcion.getOpcionSuperior())
//              ? maeOpcion.getOpcionSuperior().getNombre()
//              : null);
//          perfilOpciones.getOpciones().add(opcion);
//        }
//      });
//    });
    return perfilOpciones;
  }

}
