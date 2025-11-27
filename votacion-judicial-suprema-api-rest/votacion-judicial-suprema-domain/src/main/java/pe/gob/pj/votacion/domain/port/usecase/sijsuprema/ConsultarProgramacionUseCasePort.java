package pe.gob.pj.votacion.domain.port.usecase.sijsuprema;

import java.util.List;

import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.*;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.*;

public interface ConsultarProgramacionUseCasePort {

    List<MagistradoProgramacion> listarMagistradosProgramacion(PeticionServicios peticion,
                                                               ListarMagistradosProgramacionQuery query);

    List<ImpedidoProgramacion> listarImpedimentosProgramacion(PeticionServicios peticion,
                                                              ListarImpedidosProgramacionQuery query);

    List<MateriaProgramacion> listarMateriasProgramacion(PeticionServicios peticion,
                                                         ListarMateriasProgramacionQuery query);

    List<CausalProgramacion> listarCausalesProgramacion(PeticionServicios peticion,
                                                        ListarCausalesProgramacionQuery query);

    List<ParteProgramacion> listarPartesProgramacion(PeticionServicios peticion,
                                                     ListarPartesProgramacionQuery query);

    List<MagistradoDiscordiaProgramacion> listarMagistradosDiscordiaProgramacion(PeticionServicios peticion,
                                                     ListarMagistradosDiscordiaProgramacionQuery query);

    ApuntesProgramacion obtenerApuntesProgramacion(PeticionServicios peticion,
                                                     ObtenerApuntesProgramacionQuery query);

    List<DocumentoProgramacion> listarDocumentosProgramacion(PeticionServicios peticion,
                                                                                 ListarDocumentosProgramacionQuery query);

    List<DocumentoDigitalProgramacion> listarDocumentosDigitalesProgramacion(PeticionServicios peticion,
                                                             ListarDocumentosDigitalesProgramacionQuery query);

    DocumentoDigitalProgramacion visualizarDocumentoDigital(PeticionServicios peticion,
                                                            VisualizarDocumentoDigitalQuery query);
}
