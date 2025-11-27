package pe.gob.pj.votacion.usecase.tribunal;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.common.enums.Formatos;
import pe.gob.pj.votacion.domain.common.enums.Source;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.BuscarJurisprudenciaQuery;
import pe.gob.pj.votacion.domain.model.tribunal.Jurisprudencia;
import pe.gob.pj.votacion.domain.port.persistence.tribunal.JurisprudenciaReadPersistencePort;
import pe.gob.pj.votacion.domain.port.usecase.tribunal.JurisprudenciaUseCasePort;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JurisprudenciaUseCaseAdapter implements JurisprudenciaUseCasePort, Serializable {

  static final long serialVersionUID = 1L;

  JurisprudenciaReadPersistencePort jurisprudenciaReadPersistencePort;

  @Override
  @Transactional(transactionManager = "txManagerTribunal", propagation = Propagation.REQUIRED,
      readOnly = true, rollbackFor = {Exception.class, SQLException.class})
  public List<Jurisprudencia> buscarJurisprudencia(PeticionServicios peticion,
      BuscarJurisprudenciaQuery query) {
    if (query.fuente().equals(Source.TRIBUNAL_CONSTITUCIONAL.getEntidad())) {
      if (!Pattern.matches(Formatos.EXPEDIENTE_TC.getFormato(), query.numeroExpediente())) {
        throw new IllegalArgumentException("El formato del número de expediente no es válido");
      }
    }

    return jurisprudenciaReadPersistencePort.buscarJurisprudencia(peticion.getCuo(), query);
  }
}
