package pe.gob.pj.votacion.domain.port.persistence.tribunal;

import pe.gob.pj.votacion.domain.model.tribunal.Jurisprudencia;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.BuscarJurisprudenciaQuery;

import java.util.List;

public interface JurisprudenciaReadPersistencePort {
  List<Jurisprudencia> buscarJurisprudencia(String cuo, BuscarJurisprudenciaQuery query);
}
