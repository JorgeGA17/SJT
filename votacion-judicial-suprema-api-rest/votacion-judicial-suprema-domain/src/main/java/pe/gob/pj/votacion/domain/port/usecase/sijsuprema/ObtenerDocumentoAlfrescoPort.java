package pe.gob.pj.votacion.domain.port.usecase.sijsuprema;

import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.report.ArchivoReporte;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ObtenerDocumentoAlfrescoQuery;

public interface ObtenerDocumentoAlfrescoPort {

  ArchivoReporte obtenerDocumento(PeticionServicios peticion, ObtenerDocumentoAlfrescoQuery query);

}
