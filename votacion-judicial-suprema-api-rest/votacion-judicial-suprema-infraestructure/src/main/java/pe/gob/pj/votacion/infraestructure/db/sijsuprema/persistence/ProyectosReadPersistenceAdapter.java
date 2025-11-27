package pe.gob.pj.votacion.infraestructure.db.sijsuprema.persistence;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.domain.model.sijsuprema.ProyectoValidado;
import pe.gob.pj.votacion.domain.model.sijsuprema.ProyectoVoto;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteEstadoVotacionItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteGeneralProyectoItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ContarProyectosPendientesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosPendientesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosRelacionadosQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosValidadosQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteEstadoVotacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteGeneralProyectoQuery;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.ProyectosReadPersistencePort;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.MovVotoProyectoRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProyectosReadPersistenceAdapter implements ProyectosReadPersistencePort {

  MovVotoProyectoRepository movVotoProyectoRepository;

  @Override
  public List<ProyectoVoto> listarPendientes(String cuo, ListarProyectosPendientesQuery query) {

    var proyectos = movVotoProyectoRepository.listarPendientes(cuo, query);

    if (proyectos.isEmpty()) {
      return Collections.emptyList();
    }

    var idsDeProyectos = proyectos.stream().map(ProyectoVoto::idProyecto).toList();

    var todasLasValidaciones =
        movVotoProyectoRepository.listarValidacionesProyecto(cuo, idsDeProyectos);

    Map<Integer, List<ProyectoValidado>> validacionesPorProyectoId =
        todasLasValidaciones.stream().collect(Collectors.groupingBy(ProyectoValidado::idProyecto));

    return proyectos.stream()
        .map(
            proyectoIncompleto -> new ProyectoVoto(proyectoIncompleto.numeroExpedienteSala(),
                proyectoIncompleto.codigoLetra(), proyectoIncompleto.numeroAnioSala(),
                proyectoIncompleto.idProyecto(), proyectoIncompleto.fechaProgramacion(),
                proyectoIncompleto.codigoEstadoVotacion(),
                proyectoIncompleto.descripcionEstadoVotacion(), proyectoIncompleto.flagDiscordia(),
                proyectoIncompleto.usuarioResponsable(), proyectoIncompleto.iniciales(),
                proyectoIncompleto.flagPonente(), proyectoIncompleto.idEstadoProyecto(),
                proyectoIncompleto.descripcionEstadoProyecto(), proyectoIncompleto.numeroUnico(),
                proyectoIncompleto.numeroIncidente(), proyectoIncompleto.numeroSentido(),
                proyectoIncompleto.numeroVotacion(), proyectoIncompleto.uuidAlfresco(),
                proyectoIncompleto.extension(), proyectoIncompleto.numeroEnvio(),
                proyectoIncompleto.fechaEnvio(), proyectoIncompleto
                    .fechaIngreso(),
                proyectoIncompleto.codigoProgramacion(), proyectoIncompleto.numeroGrupo(),
                proyectoIncompleto.numeroSecuencia(), proyectoIncompleto.numeroConformacion(),
                validacionesPorProyectoId.getOrDefault(proyectoIncompleto.idProyecto(),
                    Collections.emptyList())))
        .toList();
  }

  @Override
  public List<ProyectoVoto> listarRelacionados(String cuo, ListarProyectosRelacionadosQuery query) {

    var proyectos = movVotoProyectoRepository.listarRelacionados(cuo, query);

    if (proyectos.isEmpty()) {
      return Collections.emptyList();
    }

    return proyectos.stream()
        .map(proyectoIncompleto -> new ProyectoVoto(proyectoIncompleto.numeroExpedienteSala(),
            proyectoIncompleto.codigoLetra(), proyectoIncompleto.numeroAnioSala(),
            proyectoIncompleto.idProyecto(), proyectoIncompleto.fechaProgramacion(),
            proyectoIncompleto.codigoEstadoVotacion(),
            proyectoIncompleto.descripcionEstadoVotacion(), proyectoIncompleto.flagDiscordia(),
            proyectoIncompleto.usuarioResponsable(), proyectoIncompleto.iniciales(),
            proyectoIncompleto.flagPonente(), proyectoIncompleto.idEstadoProyecto(),
            proyectoIncompleto.descripcionEstadoProyecto(), proyectoIncompleto.numeroUnico(),
            proyectoIncompleto.numeroIncidente(), proyectoIncompleto.numeroSentido(),
            proyectoIncompleto.numeroVotacion(), proyectoIncompleto.uuidAlfresco(),
            proyectoIncompleto.extension(), proyectoIncompleto.numeroEnvio(),
            proyectoIncompleto.fechaEnvio(), proyectoIncompleto.fechaIngreso(),
            proyectoIncompleto.codigoProgramacion(), proyectoIncompleto.numeroGrupo(),
            proyectoIncompleto.numeroSecuencia(), proyectoIncompleto.numeroConformacion()))
        .toList();
  }

  @Override
  public Integer contarPendientes(String cuo, ContarProyectosPendientesQuery query) {
    return movVotoProyectoRepository.contarPendientes(cuo, query);
  }

  @Override
  public List<ProyectoVoto> listarValidados(String cuo, ListarProyectosValidadosQuery query) {

    var proyectos = movVotoProyectoRepository.listarValidados(cuo, query);

    if (proyectos.isEmpty()) {
      return Collections.emptyList();
    }

    var idsDeProyectos = proyectos.stream().map(ProyectoVoto::idProyecto).toList();

    var todasLasValidaciones =
        movVotoProyectoRepository.listarValidacionesProyecto(cuo, idsDeProyectos);

    Map<Integer, List<ProyectoValidado>> validacionesPorProyectoId =
        todasLasValidaciones.stream().collect(Collectors.groupingBy(ProyectoValidado::idProyecto));

    return proyectos.stream()
        .map(
            proyectoIncompleto -> new ProyectoVoto(proyectoIncompleto.numeroExpedienteSala(),
                proyectoIncompleto.codigoLetra(), proyectoIncompleto.numeroAnioSala(),
                proyectoIncompleto.idProyecto(), proyectoIncompleto.fechaProgramacion(),
                proyectoIncompleto.codigoEstadoVotacion(),
                proyectoIncompleto.descripcionEstadoVotacion(), proyectoIncompleto.flagDiscordia(),
                proyectoIncompleto.usuarioResponsable(), proyectoIncompleto.iniciales(),
                proyectoIncompleto.flagPonente(), proyectoIncompleto.idEstadoProyecto(),
                proyectoIncompleto.descripcionEstadoProyecto(), proyectoIncompleto.numeroUnico(),
                proyectoIncompleto.numeroIncidente(), proyectoIncompleto.numeroSentido(),
                proyectoIncompleto.numeroVotacion(), proyectoIncompleto.uuidAlfresco(),
                proyectoIncompleto.extension(), proyectoIncompleto.numeroEnvio(),
                proyectoIncompleto.fechaEnvio(), proyectoIncompleto
                    .fechaIngreso(),
                proyectoIncompleto.codigoProgramacion(), proyectoIncompleto.numeroGrupo(),
                proyectoIncompleto.numeroSecuencia(), proyectoIncompleto.numeroConformacion(),
                validacionesPorProyectoId.getOrDefault(proyectoIncompleto.idProyecto(),
                    Collections.emptyList())))
        .toList();
  }

  @Override
  public List<ReporteEstadoVotacionItem> generarReporteEstadoVotacion(String cuo,
      ReporteEstadoVotacionQuery query) {
    return movVotoProyectoRepository.findReporteEstadoVotacion(cuo, query);
  }

  @Override
  public List<ReporteGeneralProyectoItem> generarReporteGeneralProyecto(String cuo,
      ReporteGeneralProyectoQuery query) {

    Map<Integer, String> magistradosPendientesMap =
        movVotoProyectoRepository.findMagistradosPendientes();

    List<ReporteGeneralProyectoItem> reportDataIncompleto =
        movVotoProyectoRepository.findReporteGeneralProyecto(cuo, query);

    return reportDataIncompleto.stream()
        .map(itemIncompleto -> new ReporteGeneralProyectoItem(itemIncompleto.fechaProgramacion(),
            itemIncompleto.ponente(), itemIncompleto.estadoVoto(), itemIncompleto.tipoParte(),
            itemIncompleto.sentido(), itemIncompleto.fallo(), itemIncompleto.anotacion(),
            itemIncompleto.responsableProyecto(), itemIncompleto.estadoProyecto(),
            itemIncompleto.fechaEnvio(),
            magistradosPendientesMap.getOrDefault(itemIncompleto.idProyecto(), ""),
            itemIncompleto.idProyecto(), itemIncompleto.rawExpSala(), itemIncompleto.rawLetra(),
            itemIncompleto.rawAnioSala(), itemIncompleto.rawApePaterno(),
            itemIncompleto.rawApeMaterno(), itemIncompleto.rawNombres(),
            itemIncompleto.rawTipoPersona()))
        .toList();
  }

}
