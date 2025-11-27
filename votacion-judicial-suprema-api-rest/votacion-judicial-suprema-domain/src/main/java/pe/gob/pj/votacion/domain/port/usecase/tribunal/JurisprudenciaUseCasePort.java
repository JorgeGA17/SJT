package pe.gob.pj.votacion.domain.port.usecase.tribunal;

import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.tribunal.Jurisprudencia;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.BuscarJurisprudenciaQuery;

import java.util.List;

public interface JurisprudenciaUseCasePort {
  List<Jurisprudencia> buscarJurisprudencia(PeticionServicios peticion, BuscarJurisprudenciaQuery query);
}
