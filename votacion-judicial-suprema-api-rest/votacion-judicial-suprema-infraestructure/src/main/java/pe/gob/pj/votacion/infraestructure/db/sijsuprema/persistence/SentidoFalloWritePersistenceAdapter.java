package pe.gob.pj.votacion.infraestructure.db.sijsuprema.persistence;

import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.exceptions.negocio.CodigoSentidoNoExisteException;
import pe.gob.pj.votacion.domain.exceptions.negocio.ExpedienteNoActualizadoException;
import pe.gob.pj.votacion.domain.exceptions.negocio.ExpedienteNoInsertadoException;
import pe.gob.pj.votacion.domain.exceptions.negocio.ExpedienteSentidoNoExisteException;
import pe.gob.pj.votacion.domain.exceptions.negocio.ExpedienteSentidoVotacionNoExisteException;
import pe.gob.pj.votacion.domain.exceptions.negocio.PadreNoInsertadoException;
import pe.gob.pj.votacion.domain.exceptions.negocio.ParteNoCumpleFiltrosException;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.FalloCommand;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarVotoCommand;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.SentidoFalloWritePersistencePort;
import pe.gob.pj.votacion.infraestructure.common.enums.Respuesta;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteVotacionParteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ParteVotacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.SentidoFalloEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteSentidoVotacionEntityPk;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteVotacionParteEntityPk;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ParteVotacionEntityPk;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ExpedienteSentidoRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ExpedienteSentidoVotacionParteRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ExpedienteSentidoVotacionRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ExpedienteVotacionParteRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ParteRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ParteVotacionRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.SentidoFalloRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class SentidoFalloWritePersistenceAdapter implements SentidoFalloWritePersistencePort {

  ParteRepository parteRepository;
  ParteVotacionRepository parteVotacionRepository;
  ExpedienteSentidoRepository expedienteSentidoRepository;
  
  SentidoFalloRepository sentidoFalloRepository;
  ExpedienteSentidoVotacionRepository expedienteSentidoVotacionRepository;
  ExpedienteSentidoVotacionParteRepository expedienteSentidoVotacionParteRepository;
  ExpedienteVotacionParteRepository expedienteVotacionParteRepository;

  ZonedDateTime fechaActual = ZonedDateTime.now();

  /* Flags opcionales para resets de l_ultimo */
  String flagResetEs = "N";    /* Reset l_ultimo en expediente_sentido */
  String flagResetVp = "N";    /* Reset l_ultimo en expediente_votacion_parte (otras votaciones) */

  @Override
  public void registrarSentidoFallo(String cuo, RegistrarVotoCommand command) {

    for (FalloCommand commandFallo : command.fallos()) {
      
      if (commandFallo.flagDiscordia() == null || commandFallo.flagDiscordia().isEmpty()) {
        commandFallo.flagDiscordia("N");
      }

      /* 1. Validar que la PARTE existe y es recurrente (negocio) */
      if (!parteRepository.findParteValida(
          command.numeroUnico(), command.numeroIncidente(), commandFallo.numeroSecuencia()).isPresent()) {
        throw new ParteNoCumpleFiltrosException(Respuesta.PARTE_NO_RECURRENTE.getDescripcionRespuesta());
      }

      /* 2. Asegurar PADRE FK#00: parte_votacion */
      try {
        boolean existe = parteVotacionRepository
            .findParteVotacion(command.numeroUnico(), command.numeroIncidente(), commandFallo.numeroSecuencia())
            .isPresent();

        if (!existe) {
          ParteVotacionEntityPk pk = ParteVotacionEntityPk.builder()
              .unico(command.numeroUnico())
              .incidente(command.numeroIncidente())
              .secuenciaParte(commandFallo.numeroSecuencia())
              .build();

          ParteVotacionEntity pv = new ParteVotacionEntity();
          pv.setLActivo("S");
          pv.setFRegistro(fechaActual);
          pv.setFechaAuditoria(fechaActual);
          pv.setBitacoraAuditoria("I");
          pv.setUsuarioAuditoria(command.codigoAudUid());
          pv.setIpAuditoria(command.numeroAudIp());
          pv.setId(pk);
          parteVotacionRepository.save(pv);
        }
      } catch (Exception ex) {
        throw new PadreNoInsertadoException(Respuesta.PADRE_NO_INSERTADO.getDescripcionRespuesta());
      }

      /* 3. Validar PADRES restantes: expediente_sentido, sentido_fallo, expediente_sentido_votacion */
      boolean existe = expedienteSentidoRepository
          .findByIdNumeroUnicoAndIdNumeroIncidenteAndIdNumeroSentido(
              command.numeroUnico(), command.numeroIncidente(), command.numeroSentido()).isPresent();

      if (!existe) {
        throw new ExpedienteSentidoNoExisteException(Respuesta.EXPEDIENTE_SENTIDO_NO_EXISTE.getDescripcionRespuesta());
      }

      /*  */
      if (commandFallo.codigoSentido() != null && !commandFallo.codigoSentido().isBlank()) {
        if (!sentidoFalloRepository.findByCodigoSentido(commandFallo.codigoSentido()).isPresent()) {
          throw new CodigoSentidoNoExisteException(Respuesta.CODIGO_SENTIDO_NO_EXISTE.getDescripcionRespuesta());
        }
      }

      /*  */
      if (!expedienteSentidoVotacionRepository.findById(new ExpedienteSentidoVotacionEntityPk(
          command.numeroUnico(),
          command.numeroIncidente(),
          command.numeroSentido(),
          command.numeroVotacion())).isPresent()) {
        throw new ExpedienteSentidoVotacionNoExisteException(Respuesta.EXPEDIENTE_SENTIDO_VOTACION_NO_EXISTE.getDescripcionRespuesta());
      }

      /* 3. (OPCIONAL) Reset l_ultimo = 'N' en expediente_sentido, con control de error */
      if ("S".equalsIgnoreCase(flagResetEs)) {
        int filasAfectadas = expedienteSentidoRepository.resetearUltimo(
            command.numeroUnico(), command.numeroIncidente(), fechaActual);

        if (filasAfectadas <= 0) {
          throw new ExpedienteNoActualizadoException(Respuesta.EXPEDIENTE_SENTIDO_NO_ACTUALIZADO.getDescripcionRespuesta());
        }
      }

      /* 4. (RECOMENDADO) Reset l_ultimo = 'N' en expediente_votacion_parte (otras votaciones de la misma parte) */
      if ("S".equalsIgnoreCase(flagResetVp)) {
        int filasAfectadas = expedienteSentidoVotacionParteRepository.resetearUltimoOtrasVotaciones(
            command.numeroUnico(), command.numeroIncidente(), command.numeroSentido(), commandFallo.numeroSecuencia(),
            command.numeroVotacion(), fechaActual, "U", command.codigoAudUid(), command.numeroAudIp());
        if (filasAfectadas < 0) {
          throw new ExpedienteNoActualizadoException(Respuesta.EXPEDIENTE_VOTACION_PARTE_ULTIMO_NO_ACTUALIZADO.getDescripcionRespuesta());
        }
      }

      /* 5) UPSERT del detalle */
      if (!expedienteSentidoVotacionParteRepository
          .findExpedienteVotacionParte(command.numeroUnico(), command.numeroIncidente(),
              command.numeroSentido(), commandFallo.numeroSecuencia(), command.numeroVotacion()).isPresent()) {

        try {
          ExpedienteVotacionParteEntityPk pk = ExpedienteVotacionParteEntityPk.builder()
              .unico(command.numeroUnico())
              .nIncidente(command.numeroIncidente())
              .nSentido(command.numeroSentido())
              .nSecuenciaParte(commandFallo.numeroSecuencia())
              .nVotacion(command.numeroVotacion())
              .build();

          SentidoFalloEntity sentidoFallo = new SentidoFalloEntity();
          sentidoFallo.setCodigoSentido(commandFallo.codigoSentido());

          ExpedienteVotacionParteEntity parte = new ExpedienteVotacionParteEntity();

          parte.setId(pk);
          parte.setSentidoFallo(sentidoFallo);
          parte.setCFallo(commandFallo.codigoFallo());
          parte.setXAnotacion(commandFallo.anotacion());
          parte.setFVotacion(fechaActual);
          parte.setFRegistro(fechaActual);
          parte.setLActivo("S");
          parte.setLUltimo("S");
          parte.setLDiscordia(commandFallo.flagDiscordia());
          parte.setFechaAuditoria(fechaActual);
          parte.setBitacoraAuditoria("I");
          parte.setUsuarioAuditoria(command.codigoAudUid());
          parte.setIpAuditoria(command.numeroAudIp());
          parte.setLPublicado("N");

          expedienteVotacionParteRepository.save(parte);
        } catch (Exception ex) {
          throw new ExpedienteNoInsertadoException(Respuesta.EXPEDIENTE_VOTACION_PARTE_NO_INSERTADO.getDescripcionRespuesta());
        }

      } else {
        int filasAfectadas = expedienteVotacionParteRepository.updateExpedienteVotacionParte(
            command.numeroUnico(), command.numeroIncidente(), command.numeroSentido(),
            commandFallo.numeroSecuencia(), command.numeroVotacion(), commandFallo.codigoSentido(),
            commandFallo.codigoFallo(), commandFallo.anotacion(), fechaActual, commandFallo.flagDiscordia(),
            fechaActual, "U", command.codigoAudUid(), command.numeroAudIp());

        if (filasAfectadas <= 0) {
          throw new ExpedienteNoActualizadoException(Respuesta.EXPEDIENTE_VOTACION_PARTE_NO_ACTUALIZADO.getDescripcionRespuesta());
        }
      }
    }

  }
}
