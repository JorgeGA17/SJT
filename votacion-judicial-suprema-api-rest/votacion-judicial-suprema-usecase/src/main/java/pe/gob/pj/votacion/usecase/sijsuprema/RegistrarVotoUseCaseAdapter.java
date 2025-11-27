package pe.gob.pj.votacion.usecase.sijsuprema;

import java.sql.SQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.common.utils.ProjectUtils;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.Registro;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarVotoCommand;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ValidarDatosQuery;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.ApuntesWritePersistencePort;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.DiscordantesWritePersistencePort;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.EstadoReadPersistencePort;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.EstadoWritePersistencePort;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.JurisprudenciaWritePersistencePort;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.SentidoFalloWritePersistencePort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.RegistrarVotoUseCasePort;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RegistrarVotoUseCaseAdapter implements RegistrarVotoUseCasePort {

  EstadoReadPersistencePort estadoReadPersistencePort;
  EstadoWritePersistencePort estadoWritePersistencePort;
  ApuntesWritePersistencePort apuntesWritePersistencePort;
  SentidoFalloWritePersistencePort sentidoFalloWritePersistencePort;
  DiscordantesWritePersistencePort discordantesWritePersistencePort;
  JurisprudenciaWritePersistencePort jurisprudenciaWritePersistencePort;

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRED, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public Registro validarDatos(PeticionServicios peticion, ValidarDatosQuery query) {
    return estadoReadPersistencePort.validarDatos(peticion.getCuo(), query);
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRES_NEW, readOnly = false,
      rollbackFor = {Exception.class, SQLException.class})
  public void registrarVoto(PeticionServicios peticion, RegistrarVotoCommand command) {

    // 0. Validar estado
    estadoReadPersistencePort.validarEstado(peticion.getCuo(), command);

    if (ProjectUtils.isNullOrEmpty(command.numeroSentido())
        && ProjectUtils.isNullOrEmpty(command.numeroVotacion())) {
      // 1. Registrar estado
      estadoWritePersistencePort.registrarEstado(peticion.getCuo(), command);
    }

    if (!command.codigoUsuarioPonente().equalsIgnoreCase(command.codigoUsuario())) {
      // 4. Registrar apuntes (juez)
      apuntesWritePersistencePort.registrarApuntes(peticion.getCuo(), command);
      // 5. Registrar jurisprudencias
      jurisprudenciaWritePersistencePort.registrarJurisprudencia(peticion.getCuo(), command);
    } else {
      // 2. Registrar sentido-fallo (parte)
      try {
        sentidoFalloWritePersistencePort.registrarSentidoFallo(peticion.getCuo(), command);
      } catch (Exception e) {
        log.error("Error en registrarSentidoFallo: {}", e.getMessage());
      }

      // 3. Registrar discordias
      try {
        discordantesWritePersistencePort.registrarDiscordantes(peticion.getCuo(), command);
      } catch (Exception e) {
        log.error("Error en registrarDiscordantes: {}", e.getMessage());
      }

      // 4. Registrar apuntes (juez)
      try {
        apuntesWritePersistencePort.registrarApuntes(peticion.getCuo(), command);
      } catch (Exception e) {
        log.error("Error en registrarApuntes: {}", e.getMessage());
      }

      // 5. Registrar jurisprudencias
      try {
        jurisprudenciaWritePersistencePort.registrarJurisprudencia(peticion.getCuo(), command);
      } catch (Exception e) {
        log.error("Error en registrarJurisprudencia: {}", e.getMessage());
      }

    }
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRES_NEW, readOnly = false,
      rollbackFor = {Exception.class, SQLException.class})
  public void autoguardado(PeticionServicios peticion, RegistrarVotoCommand command) {

    // 0. Validar estado
    estadoReadPersistencePort.validarEstado(peticion.getCuo(), command);

    if (ProjectUtils.isNullOrEmpty(command.numeroSentido())
        && ProjectUtils.isNullOrEmpty(command.numeroVotacion())) {
      // 1. Registrar estado
      estadoWritePersistencePort.registrarEstado(peticion.getCuo(), command);
    }

    if (!command.codigoUsuarioPonente().equalsIgnoreCase(command.codigoUsuario())) {
      // 4. Registrar apuntes (juez)
      apuntesWritePersistencePort.registrarApuntes(peticion.getCuo(), command);
      // 5. Registrar jurisprudencias
      jurisprudenciaWritePersistencePort.registrarJurisprudencia(peticion.getCuo(), command);
    } else {
      // 2. Registrar anotacion
      // 3. Registrar discordias
      discordantesWritePersistencePort.registrarDiscordantes(peticion.getCuo(), command);
      // 4. Registrar apuntes (juez)
      apuntesWritePersistencePort.registrarApuntes(peticion.getCuo(), command);
      // 5. Registrar jurisprudencias
      jurisprudenciaWritePersistencePort.registrarJurisprudencia(peticion.getCuo(), command);
    }

  }
}
