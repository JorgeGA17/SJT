package pe.gob.pj.votacion.domain.port.persistence.sijsuprema;

import java.util.List;

import pe.gob.pj.votacion.domain.model.sijsuprema.*;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.*;

public interface ProgramacionReadPersistencePort {

  List<MagistradoProgramacion> listarMagistradosProgramacion(String cuo,
      ListarMagistradosProgramacionQuery query);

  List<ImpedidoProgramacion> listarImpedimentosProgramacion(String cuo,
      ListarImpedidosProgramacionQuery query);

  List<MateriaProgramacion> listarMateriasProgramacion(String cuo,
      ListarMateriasProgramacionQuery query);

  List<CausalProgramacion> listarCausalesProgramacion(String cuo,
      ListarCausalesProgramacionQuery query);

  List<ParteProgramacion> listarPartesProgramacion(String cuo, ListarPartesProgramacionQuery query);

  List<MagistradoDiscordiaProgramacion> listarMagistradosDiscordiaProgramacion(String cuo,
      ListarMagistradosDiscordiaProgramacionQuery query);

  ApuntesProgramacion obtenerApuntesProgramacion(String cuo, ObtenerApuntesProgramacionQuery query);

  List<DocumentoProgramacion> listarDocumentosProgramacion(String cuo,
      ListarDocumentosProgramacionQuery query);

  List<DocumentoDigitalProgramacion> listarDocumentosDigitalesProgramacion(String cuo,
      ListarDocumentosDigitalesProgramacionQuery query);
}
