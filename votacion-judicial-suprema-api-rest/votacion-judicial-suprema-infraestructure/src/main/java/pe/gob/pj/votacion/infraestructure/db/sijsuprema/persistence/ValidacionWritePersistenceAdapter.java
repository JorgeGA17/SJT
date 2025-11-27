package pe.gob.pj.votacion.infraestructure.db.sijsuprema.persistence;

import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarValidacionCommand;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.ValidacionWritePersistencePort;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteSentidoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.MaeVotoProyEstadoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.MovVotoProyectoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.MovVotoValidarProyEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteSentidoEntityPk;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.MovVotoProyectoRespository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.MovVotoValidarProyRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ValidacionWritePersistenceAdapter implements ValidacionWritePersistencePort {

  MovVotoValidarProyRepository movVotoValidarProyRepository;
  MovVotoProyectoRespository movVotoProyectoRespository;

  @Override
  public void registrarValidacion(String cuo, RegistrarValidacionCommand command) {

    ZonedDateTime fechaActual = ZonedDateTime.now();

    MovVotoValidarProyEntity entidad =
        movVotoValidarProyRepository.findByUsuarioVal(command.idProyecto(), command.codigoUsuario())
            .orElseThrow(() -> new IllegalArgumentException("Validación no encontrada"));

    entidad.setNumeroValidado(command.numeroValidado());

    if (command.numeroValidado() != 1) {
      entidad.setObservacion(command.observacion());
    }
    movVotoValidarProyRepository.save(entidad);
    Integer numeroTotal;

    var validado0 = movVotoValidarProyRepository.findByValidado(command.idProyecto(), 0);
    var validado2 = movVotoValidarProyRepository.findByValidado(command.idProyecto(), 2);

    if (!validado0.isEmpty()) {
      if (!validado2.isEmpty()) {
        MovVotoProyectoEntity movVotoProyectoEntity =
            movVotoProyectoRespository.findById(command.idProyecto())
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));

        MaeVotoProyEstadoEntity nuevoEstado = new MaeVotoProyEstadoEntity();
        nuevoEstado.setId(4);
        movVotoProyectoEntity.setMaeVotoProyEstadoEntity(nuevoEstado);
        movVotoProyectoEntity.setUltimo("N");

        movVotoProyectoRespository.save(movVotoProyectoEntity);

        numeroTotal = movVotoProyectoEntity.getNumeroEnvio();

        numeroTotal++;

        MovVotoProyectoEntity registro = new MovVotoProyectoEntity();
        ExpedienteSentidoEntity expedienteSentidoEntity = new ExpedienteSentidoEntity();
        ExpedienteSentidoEntityPk pk = ExpedienteSentidoEntityPk.builder()
            .numeroUnico(
                movVotoProyectoEntity.getExpedienteSentidoEntity().getId().getNumeroUnico())
            .numeroIncidente(
                movVotoProyectoEntity.getExpedienteSentidoEntity().getId().getNumeroIncidente())
            .numeroSentido(
                movVotoProyectoEntity.getExpedienteSentidoEntity().getId().getNumeroSentido())
            .build();
        expedienteSentidoEntity.setId(pk);
        registro.setExpedienteSentidoEntity(expedienteSentidoEntity);
        registro.setNumeroVotacion(movVotoProyectoEntity.getNumeroVotacion());
        registro.setNumeroEnvio(numeroTotal);
        MaeVotoProyEstadoEntity registroEstado = new MaeVotoProyEstadoEntity();
        registroEstado.setId(1);
        registro.setMaeVotoProyEstadoEntity(registroEstado);
        registro.setCodigoUsuarioResponsable(movVotoProyectoEntity.getCodigoUsuarioResponsable());
        registro.setFechaRegistro(fechaActual);
        registro.setFechaAuditoria(fechaActual);
        registro.setBitacoraAuditoria("I");
        registro.setUsuarioAuditoria("UserAuditoria");
        registro.setIpAuditoria("10.0.0.1");
        movVotoProyectoRespository.save(registro);
        log.info("Proyecto {} marcado como OBSERVADO y se generó nueva versión (envío {}).",
            command.idProyecto(), numeroTotal);
      } else {
        MovVotoProyectoEntity movVotoProyectoEntity =
            movVotoProyectoRespository.findById(command.idProyecto())
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));
        MaeVotoProyEstadoEntity nuevoEstado = new MaeVotoProyEstadoEntity();
        nuevoEstado.setId(3);
        movVotoProyectoEntity.setMaeVotoProyEstadoEntity(nuevoEstado);
        movVotoProyectoRespository.save(movVotoProyectoEntity);

        log.info("Proyecto {} marcado como APROBADO (estado=3)", command.idProyecto());
      }
    }
    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

  }
}
