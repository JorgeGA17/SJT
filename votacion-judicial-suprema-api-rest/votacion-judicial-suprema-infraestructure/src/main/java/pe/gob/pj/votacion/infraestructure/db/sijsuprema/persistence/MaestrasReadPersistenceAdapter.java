package pe.gob.pj.votacion.infraestructure.db.sijsuprema.persistence;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import pe.gob.pj.votacion.domain.model.sijsuprema.*;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.*;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.MaestrasReadPersistencePort;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.*;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MaestrasReadPersistenceAdapter implements MaestrasReadPersistencePort {

  FalloRepository falloRepository;
  SentidoFalloRepository sentidoFalloRepository;
  SalaColegiadoConformacionRepository salaColegiadoConformacionRepository;
  EstadoMaestroRepository estadoMaestroRepository;
  MaeVotoProyEstadoRepository maeVotoProyEstadoRepository;

  @Override
  public List<Fallo> listarFallos(String cuo, ListarFallosQuery query) {
    return falloRepository.listarFallos(query.codigoEspecialidad(), query.codigoAbreviatura(), query.codigoMotivoIngreso());
  }

  @Override
  public List<Sentido> listarSentidos(String cuo, ListarSentidosQuery query) {
    return sentidoFalloRepository.listarSentidos(query.codigoDistrito(), query.codigoProvincia(),
        query.codigoInstancia());
  }

  @Override
  public List<Colegiado> listarColegiados(String cuo, ListarColegiadosQuery query) {
    return salaColegiadoConformacionRepository.listarColegiados(query.codigoDistrito(),
        query.codigoProvincia(), query.codigoInstancia());
  }

  @Override
  public List<EstadoVotacion> listarEstadosVotacion(String cuo, ListarEstadosVotacionQuery query) {
    return estadoMaestroRepository.listarEstadosVotacion();
  }

  @Override
  public List<EstadoProyecto> listarEstadosProyecto(String cuo, ListarEstadosProyectoQuery query) {
    return maeVotoProyEstadoRepository.listarEstadosProyecto();
  }
}
