package pe.gob.pj.votacion.infraestructure.db.sijsuprema.persistence;


import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.exceptions.negocio.MovVotoJurispNoActualizadoException;
import pe.gob.pj.votacion.domain.exceptions.negocio.ParametrosRequeridosNulosException;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.JurisprudenciaCommand;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarVotoCommand;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.JurisprudenciaWritePersistencePort;
import pe.gob.pj.votacion.infraestructure.common.enums.Respuesta;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.MovVotoJurispEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.MovVotoJurispRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class JurisprudenciaWritePersistenceAdapter implements JurisprudenciaWritePersistencePort {

  MovVotoJurispRepository movVotoJurispRepository;

  ZonedDateTime fechaActual = ZonedDateTime.now();

  @Override
  public void registrarJurisprudencia(String cuo, RegistrarVotoCommand command) {
    if(command.jurisprudencias()!=null && !command.jurisprudencias().isEmpty()) {
      for (JurisprudenciaCommand juris : command.jurisprudencias()) {
        /* ===== Validación previa ===== */
        if (command.numeroUnico() == null || command.numeroIncidente() == null || juris.uuid() == null) {
          throw new ParametrosRequeridosNulosException(Respuesta.PARAMETROS_NULOS.getDescripcionRespuesta());
        }

        /* 1) UPDATE por clave natural (sin alias; NULL-safe en c_programacion) */
        int filas;

        try {
          filas = movVotoJurispRepository.actualizarMovVotoJurisp(
              juris.flagActivo(),
              fechaActual,
              "U",
              command.codigoAudUid(),
              command.numeroAudIp(),
              command.numeroUnico(),
              command.numeroIncidente(),
              command.codigoProgramacion(),
              juris.uuid()
          );

        } catch (Exception e) {
          throw new MovVotoJurispNoActualizadoException(Respuesta.VOTO_JURISPRUDENCIA_NO_ACTUALIZADO.getDescripcionRespuesta());
        }

        if (filas == 0) {
          /* 2) No existía ? INSERT */
          try{
            MovVotoJurispEntity movVotoJurispEntity = new MovVotoJurispEntity();
            movVotoJurispEntity.setNUnico(command.numeroUnico());
            movVotoJurispEntity.setNIncidente(command.numeroIncidente());
            movVotoJurispEntity.setCProgramacion(command.codigoProgramacion());
            movVotoJurispEntity.setXEntidad(juris.source());
            movVotoJurispEntity.setXRecurso(juris.recurso());
            movVotoJurispEntity.setXUuid(juris.uuid());
            movVotoJurispEntity.setLActivo(juris.flagActivo());
            movVotoJurispEntity.setFRegistro(fechaActual);
            movVotoJurispEntity.setFechaAuditoria(fechaActual);
            movVotoJurispEntity.setBitacoraAuditoria("I");
            movVotoJurispEntity.setUsuarioAuditoria(command.codigoAudUid());
            movVotoJurispEntity.setIpAuditoria(command.numeroAudIp());

            movVotoJurispRepository.save(movVotoJurispEntity);

          } catch (Exception e) {
            /* 3) Reintento de UPDATE por posible colisión concurrente */
            try {
              filas = movVotoJurispRepository.actualizarMovVotoJurisp(
                  juris.flagActivo(),
                  fechaActual,
                  "U",
                  command.codigoAudUid(),
                  command.numeroAudIp(),
                  command.numeroUnico(),
                  command.numeroIncidente(),
                  command.codigoProgramacion(),
                  juris.uuid()
              );

            } catch (Exception ex) {
              throw new MovVotoJurispNoActualizadoException(Respuesta.VOTO_JURISPRUDENCIA_NO_ACTUALIZADO_POST_INSERT.getDescripcionRespuesta());
            }

            if (filas == 0) {
              throw new MovVotoJurispNoActualizadoException(Respuesta.VOTO_JURISPRUDENCIA_NO_ACTUALIZADO_NO_INSERTADO.getDescripcionRespuesta());
            }
          }
        }
      }
    }
    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
  }
}
