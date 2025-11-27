package pe.gob.pj.votacion.infraestructure.db.sijsuprema.persistence;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.gob.pj.votacion.domain.exceptions.negocio.ColegiadoNoValidoException;
import pe.gob.pj.votacion.domain.exceptions.negocio.PonenteNoValidoException;
import pe.gob.pj.votacion.domain.model.sijsuprema.Registro;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarVotoCommand;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ValidarDatosQuery;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.EstadoReadPersistencePort;
import pe.gob.pj.votacion.infraestructure.common.enums.Respuesta;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ConformacionGrupoRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.SalaColegiadoConformacionRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class EstadoReadPersistenceAdapter implements EstadoReadPersistencePort {

  SalaColegiadoConformacionRepository salaColegiadoConformacionRepository;
  ConformacionGrupoRepository conformacionGrupoRepository;

  @Override
  public Registro validarDatos(String cuo, ValidarDatosQuery query) {

    var miembros = salaColegiadoConformacionRepository.listarMiembros(query.codigoDistrito(),
        query.codigoProvincia(), query.codigoInstancia());
    var ponentes = conformacionGrupoRepository.listarPonentes(query.codigoProgramacion(),
        query.numeroGrupoVoto(), query.numeroSecuenciaVoto(), query.numeroConformacionVoto());

    if (miembros.size() != 5) {
      return new Registro(Respuesta.VALIDACION_COLEGIADO.getCodigoRespuesta(),
          Respuesta.VALIDACION_COLEGIADO.getDescripcionRespuesta());
    }

    if (ponentes.isEmpty()) {
      return new Registro(Respuesta.VALIDACION_PONENTE.getCodigoRespuesta(),
          Respuesta.VALIDACION_PONENTE.getDescripcionRespuesta());
    }

    return new Registro(Respuesta.VALIDACION_EXITOSA.getCodigoRespuesta(),
        Respuesta.VALIDACION_EXITOSA.getDescripcionRespuesta());
  }

  @Override
  public void validarEstado(String cuo, RegistrarVotoCommand command) {

    var miembros = salaColegiadoConformacionRepository.listarMiembros(command.codigoDistrito(),
        command.codigoProvincia(), command.codigoInstancia());
    var ponentes = conformacionGrupoRepository.listarPonentes(command.codigoProgramacion(),
        command.numeroGrupoVoto(), command.numeroSecuenciaVoto(), command.numeroConformacionVoto());

    if (miembros.size() != 5) {
      throw new ColegiadoNoValidoException(
          Respuesta.VALIDACION_COLEGIADO.getDescripcionRespuesta());
    }

    if (ponentes.isEmpty()) {
      throw new PonenteNoValidoException(Respuesta.VALIDACION_PONENTE.getDescripcionRespuesta());
    }

  }
}
