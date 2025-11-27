package pe.gob.pj.votacion.infraestructure.db.sijsuprema.persistence;


import java.time.ZonedDateTime;
import java.util.Optional;
import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.exceptions.negocio.VotoDiscordiaNoActualizadoException;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.DiscordiaCommand;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.FalloCommand;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarVotoCommand;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.DiscordantesWritePersistencePort;
import pe.gob.pj.votacion.infraestructure.common.enums.Respuesta;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.MovVotoDiscordiaEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.MovVotoDiscordiaRepository;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class DiscordantesWritePersistenceAdapter implements DiscordantesWritePersistencePort {

  MovVotoDiscordiaRepository movVotoDiscordiaRepository;
  ZonedDateTime fechaActual = ZonedDateTime.now();


  @Override
  public void registrarDiscordantes(String cuo, RegistrarVotoCommand command) {
    for(FalloCommand parte : command.fallos()) {
      if (parte.discordias()!=null && !parte.discordias().isEmpty()){
        for (DiscordiaCommand dis : parte.discordias()) {
            /* 1) UPDATE primero (por @n_id si viene; si no, por clave lógica) */
            int filasAfectadas;
            try{
              if(dis.id() != null){
                filasAfectadas = movVotoDiscordiaRepository.actualizarVotoDiscordiaById(
                    dis.flagActivo(),
                    fechaActual,
                    "U",
                    command.codigoAudUid(),
                    "DOMINIO\\usuario",
                    "HOSTNAME01",
                    command.numeroAudIp(),
                    "00-11-22-33-44-55",
                    dis.id()
                );
              } else{
                filasAfectadas = movVotoDiscordiaRepository.actualizarVotoDiscordiaByUnico(
                    dis.flagActivo(),
                    fechaActual,
                    "U",
                    command.codigoAudUid(),
                    "DOMINIO\\usuario",
                    "HOSTNAME01",
                    command.numeroAudIp(),
                    "00-11-22-33-44-55",
                    command.numeroUnico(),
                    command.numeroIncidente(),
                    command.numeroSentido(),
                    parte.numeroSecuencia(),
                    command.numeroVotacion(),
                    dis.codigoUsuario()
                );
              }
            } catch(Exception e){
              throw new VotoDiscordiaNoActualizadoException(Respuesta.VOTO_DISCORDIA_NO_ACTUALIZADO.getDescripcionRespuesta());
            }

            Optional<MovVotoDiscordiaEntity> existente = movVotoDiscordiaRepository.findByFiltros(
                command.numeroUnico(),
                command.numeroIncidente(),
                command.numeroSentido(),
                parte.numeroSecuencia(),
                command.numeroVotacion(),
                dis.codigoUsuario()
            );

            if (existente.isPresent()) {
              MovVotoDiscordiaEntity entity = existente.get();
              entity.setActivo(dis.flagActivo());
              entity.setFechaAuditoria(fechaActual);
              entity.setBitacoraAuditoria("U");
              movVotoDiscordiaRepository.save(entity);
            } else {
              /* 2) Si no actualizó, intentamos INSERT */
              log.info("TRY INSERT");
              MovVotoDiscordiaEntity entity = new MovVotoDiscordiaEntity();
              entity.setNumeroUnico(command.numeroUnico());
              entity.setNumeroIncidente(command.numeroIncidente());
              entity.setNumeroSentido(command.numeroSentido());
              entity.setNumeroSecuenciaParte(parte.numeroSecuencia());
              entity.setNumeroVotacion(command.numeroVotacion());
              entity.setCodigoUsuarioDiscordia(dis.codigoUsuario());
              entity.setActivo(dis.flagActivo());
              entity.setFRegistro(fechaActual);
              entity.setFechaAuditoria(fechaActual);
              entity.setBitacoraAuditoria("I");
              entity.setUsuarioAuditoria(command.codigoAudUid());
              entity.setUsuarioRedAuditoria("DOMINIO\\usuario");
              entity.setPcAuditoria("HOSTNAME01");
              entity.setIpAuditoria(command.numeroAudIp());
              entity.setMacAuditoria("00-11-22-33-44-55");
              movVotoDiscordiaRepository.save(entity);
            }
        }
      }
    }
  }
}
