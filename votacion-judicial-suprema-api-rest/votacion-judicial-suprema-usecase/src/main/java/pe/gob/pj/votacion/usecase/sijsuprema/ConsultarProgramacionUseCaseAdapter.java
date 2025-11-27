package pe.gob.pj.votacion.usecase.sijsuprema;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.*;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.*;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.ProgramacionReadPersistencePort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.ConsultarProgramacionUseCasePort;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ConsultarProgramacionUseCaseAdapter implements ConsultarProgramacionUseCasePort {

  ProgramacionReadPersistencePort programacionReadPersistencePort;

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<MagistradoProgramacion> listarMagistradosProgramacion(PeticionServicios peticion,
      ListarMagistradosProgramacionQuery query) {
    return programacionReadPersistencePort.listarMagistradosProgramacion(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<ImpedidoProgramacion> listarImpedimentosProgramacion(PeticionServicios peticion,
      ListarImpedidosProgramacionQuery query) {
    return programacionReadPersistencePort.listarImpedimentosProgramacion(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<MateriaProgramacion> listarMateriasProgramacion(PeticionServicios peticion,
      ListarMateriasProgramacionQuery query) {
    return programacionReadPersistencePort.listarMateriasProgramacion(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<CausalProgramacion> listarCausalesProgramacion(PeticionServicios peticion,
      ListarCausalesProgramacionQuery query) {
    return programacionReadPersistencePort.listarCausalesProgramacion(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<ParteProgramacion> listarPartesProgramacion(PeticionServicios peticion,
      ListarPartesProgramacionQuery query) {
    return programacionReadPersistencePort.listarPartesProgramacion(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<MagistradoDiscordiaProgramacion> listarMagistradosDiscordiaProgramacion(
      PeticionServicios peticion, ListarMagistradosDiscordiaProgramacionQuery query) {
    return programacionReadPersistencePort.listarMagistradosDiscordiaProgramacion(peticion.getCuo(),
        query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public ApuntesProgramacion obtenerApuntesProgramacion(PeticionServicios peticion,
      ObtenerApuntesProgramacionQuery query) {
    return programacionReadPersistencePort.obtenerApuntesProgramacion(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<DocumentoProgramacion> listarDocumentosProgramacion(PeticionServicios peticion,
      ListarDocumentosProgramacionQuery query) {
    return programacionReadPersistencePort.listarDocumentosProgramacion(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public List<DocumentoDigitalProgramacion> listarDocumentosDigitalesProgramacion(
      PeticionServicios peticion, ListarDocumentosDigitalesProgramacionQuery query) {
    return programacionReadPersistencePort.listarDocumentosDigitalesProgramacion(peticion.getCuo(),
        query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public DocumentoDigitalProgramacion visualizarDocumentoDigital(PeticionServicios peticion,
      VisualizarDocumentoDigitalQuery query) {

    List<DocumentoDigitalProgramacion> listaDocs =
        programacionReadPersistencePort.listarDocumentosDigitalesProgramacion(peticion.getCuo(),
            ListarDocumentosDigitalesProgramacionQuery.builder().numeroUnico(query.numeroUnico())
                .numeroIncidente(query.numeroIncidente()).build());

    // recorrer listaDocs y buscar el documento con nDocumento
    if (listaDocs != null && !listaDocs.isEmpty()) {
      DocumentoDigitalProgramacion doc = listaDocs.stream()
          .filter(d -> d.nDocumento().equals(query.numeroDocumento())).findFirst().orElse(null);
      if (doc != null) {
        return doc;
      }
    }

    return null;

  }
}
