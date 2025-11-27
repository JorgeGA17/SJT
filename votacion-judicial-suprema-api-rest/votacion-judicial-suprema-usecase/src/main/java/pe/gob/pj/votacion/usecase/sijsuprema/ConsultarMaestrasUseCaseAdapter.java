package pe.gob.pj.votacion.usecase.sijsuprema;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.*;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.*;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.MaestrasReadPersistencePort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.ConsultarMaestrasUseCasePort;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ConsultarMaestrasUseCaseAdapter implements ConsultarMaestrasUseCasePort, Serializable {

    static final long serialVersionUID = 1L;

    MaestrasReadPersistencePort maestrasReadPersistencePort;

    @Override
    @Transactional(transactionManager = "sijsupremaTransactionManager",
            propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {Exception.class, SQLException.class})
    public List<Fallo> listarFallos(PeticionServicios peticion, ListarFallosQuery query) {
        return maestrasReadPersistencePort.listarFallos(peticion.getCuo(), query);
    }

    @Override
    @Transactional(transactionManager = "sijsupremaTransactionManager",
            propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {Exception.class, SQLException.class})
    public List<Sentido> listarSentidos(PeticionServicios peticion, ListarSentidosQuery query) {
        return maestrasReadPersistencePort.listarSentidos(peticion.getCuo(), query);
    }

    @Override
    @Transactional(transactionManager = "sijsupremaTransactionManager",
            propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {Exception.class, SQLException.class})
    public List<Colegiado> listarColegiados(PeticionServicios peticion, ListarColegiadosQuery query) {
        return maestrasReadPersistencePort.listarColegiados(peticion.getCuo(), query);
    }

    @Override
    @Transactional(transactionManager = "sijsupremaTransactionManager",
            propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {Exception.class, SQLException.class})
    public List<EstadoVotacion> listarEstadosVotacion(PeticionServicios peticion,
                                                      ListarEstadosVotacionQuery query) {
        return maestrasReadPersistencePort.listarEstadosVotacion(peticion.getCuo(), query);
    }

    @Override
    @Transactional(transactionManager = "sijsupremaTransactionManager",
            propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {Exception.class, SQLException.class})
    public List<EstadoProyecto> listarEstadosProyecto(PeticionServicios peticion,
                                                      ListarEstadosProyectoQuery query) {
        return maestrasReadPersistencePort.listarEstadosProyecto(peticion.getCuo(), query);
    }
}

