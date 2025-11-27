package pe.gob.pj.votacion.infraestructure.db.sijsuprema.persistence;

import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.domain.common.enums.Formatos;
import pe.gob.pj.votacion.domain.common.utils.ProjectUtils;
import pe.gob.pj.votacion.domain.model.sijsuprema.*;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.*;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.ProgramacionReadPersistencePort;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProgramacionReadPersistenceAdapter implements ProgramacionReadPersistencePort {

  ProgramacionInstanciaVocalRepository programacionInstanciaVocalRepository;
  MateriaExpedienteRepository materiaExpedienteRepository;
  ExpedienteCausalDetRepository expedienteCausalDetRepository;
  ParteRepository parteRepository;
  ExpedienteVotacionRepository expedienteVotacionRepository;
  MovVotoJurispRepository movVotoJurispRepository;
  InstanciaExpedienteRepository instanciaExpedienteRepository;

  @Override
  public List<MagistradoProgramacion> listarMagistradosProgramacion(String cuo,
      ListarMagistradosProgramacionQuery query) {
    return programacionInstanciaVocalRepository.findMagistradosByProgramacion(
        query.codigoProgramacion(), query.numeroGrupo(), query.numeroSecuencia(),
        query.numeroConformacion());
  }

  @Override
  public List<ImpedidoProgramacion> listarImpedimentosProgramacion(String cuo,
      ListarImpedidosProgramacionQuery query) {
    return programacionInstanciaVocalRepository.findImpedidosByProgramacion(
        query.codigoProgramacion(), query.codigoDistrito(), query.codigoProvincia(),
        query.codigoInstancia(), query.numeroUnico(), query.numeroIncidente(),
        ProjectUtils.convertStringToLocalDateTime(query.fechaIngreso(),
            Formatos.FECHA_YYYY_MM_DD_HH_MM_SS_SSS.getFormato()).atZone(ZoneId.systemDefault()));
  }

  @Override
  public List<MateriaProgramacion> listarMateriasProgramacion(String cuo,
      ListarMateriasProgramacionQuery query) {
    return materiaExpedienteRepository.findMateriasByProgramacion(query.codigoProgramacion(),
        query.numeroUnico(), query.numeroIncidente());
  }

  @Override
  public List<CausalProgramacion> listarCausalesProgramacion(String cuo,
      ListarCausalesProgramacionQuery query) {
    return expedienteCausalDetRepository
        .findCausalesByProgramacion(query.codigoProgramacion(), query.numeroUnico(),
            query.numeroIncidente())
        .stream()
        .map(t -> new CausalProgramacion(t[0] != null ? ((Number) t[0]).intValue() : null,
            t[1] != null ? ((Number) t[1]).intValue() : null, Objects.toString(t[2], ""),
            Objects.toString(t[3], "")))
        .toList();
  }

  @Override
  public List<ParteProgramacion> listarPartesProgramacion(String cuo,
      ListarPartesProgramacionQuery query) {
    return parteRepository.findPartesByProgramacion(query.codigoProgramacion(), query.numeroUnico(),
        query.numeroIncidente(), query.flagDiscordia(), query.numeroSentido(),
        query.numeroVotacion());
  }

  @Override
  public List<MagistradoDiscordiaProgramacion> listarMagistradosDiscordiaProgramacion(String cuo,
      ListarMagistradosDiscordiaProgramacionQuery query) {
    return programacionInstanciaVocalRepository.findMagistradosDiscordiaByProgramacion(
        query.codigoProgramacion(), query.numeroUnico(), query.numeroIncidente(),
        query.numeroSecuenciaParte(), query.numeroSentido(), query.numeroVotacion(),
        query.codigoVocalPonente());
  }

  @Override
  public ApuntesProgramacion obtenerApuntesProgramacion(String cuo,
      ObtenerApuntesProgramacionQuery query) {
    return expedienteVotacionRepository.findApuntesByProgramacion(query.numeroUnico(),
        query.numeroIncidente(), query.numeroSentido(), query.codigoVocalUsuario());
  }

  @Override
  public List<DocumentoProgramacion> listarDocumentosProgramacion(String cuo,
      ListarDocumentosProgramacionQuery query) {
    return movVotoJurispRepository.findDocumentosByProgramacion(query.codigoProgramacion(),
        query.numeroUnico(), query.numeroIncidente());
  }

  @Override
  public List<DocumentoDigitalProgramacion> listarDocumentosDigitalesProgramacion(String cuo,
      ListarDocumentosDigitalesProgramacionQuery query) {
    return instanciaExpedienteRepository.findDocumentosDigitalesByUnico(query.numeroUnico(),
        query.numeroIncidente());
  }
}
