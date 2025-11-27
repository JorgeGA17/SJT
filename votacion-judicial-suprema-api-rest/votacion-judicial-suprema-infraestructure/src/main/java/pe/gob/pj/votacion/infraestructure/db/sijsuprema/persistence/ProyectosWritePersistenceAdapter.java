package pe.gob.pj.votacion.infraestructure.db.sijsuprema.persistence;

import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.common.enums.EstadoVotoProyecto;
import pe.gob.pj.votacion.domain.exceptions.general.CargaArchivoAlfrescoFallidoException;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarEnvioProyectoCommand;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.ProyectosWritePersistencePort;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteVotacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.MovVotoDiscordiaEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.MovVotoValidarProyEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ExpedienteVotacionRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.MaeVotoProyEstadoRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.MovVotoDiscordiaRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.MovVotoProyectoRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.MovVotoValidarProyRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ProyectosWritePersistenceAdapter implements ProyectosWritePersistencePort {

  ExpedienteVotacionRepository expedienteVotacionRepository;
  MovVotoDiscordiaRepository movVotoDiscordiaRepository;
  MovVotoProyectoRepository movVotoProyectoRepository;
  MovVotoValidarProyRepository movVotoValidarProyRepository;
  MaeVotoProyEstadoRepository maeVotoProyEstadoRepository;

  @Override
  public void registrarEnvioVoto(PeticionServicios peticion,
      RegistrarEnvioProyectoCommand command) {

    try {

      boolean isDiscordia = "FAR".equals(command.codigoEstado());
      if (!isDiscordia) {
        handleCasoNoDiscordia(command);
      } else {
        handleCasoDiscordia(command);
      }
      actualizarMovVotoProyecto(command);

    } catch (Exception e) {
      throw new CargaArchivoAlfrescoFallidoException(e.getMessage());
    }

  }

  private void handleCasoNoDiscordia(RegistrarEnvioProyectoCommand command) {
    List<ExpedienteVotacionEntity> noPonentes = expedienteVotacionRepository
        .findByIdNumeroUnicoAndIdNumeroIncidenteAndIdNumeroSentidoAndUltimoAndPonenteNot(
            command.numeroUnico(), command.numeroIncidente(), command.sentido(), "S", "S");
    if (!noPonentes.isEmpty()) {
      List<MovVotoValidarProyEntity> validaciones =
          noPonentes.stream().map(ev -> crearValidacion(command, ev.getUsuario())).toList();
      movVotoValidarProyRepository.saveAll(validaciones);
    }
  }

  private void handleCasoDiscordia(RegistrarEnvioProyectoCommand command) {

    boolean esUsuarioPonente = expedienteVotacionRepository
        .existsByIdNumeroUnicoAndIdNumeroIncidenteAndIdNumeroSentidoAndUltimoAndPonenteAndUsuario(
            command.numeroUnico(), command.numeroIncidente(), command.sentido(), "S", "S",
            command.usuarioResponsable());

    if (esUsuarioPonente) {

      List<String> usuariosAValidar =
          expedienteVotacionRepository.findUsuariosNoPonentesSinDiscordia(command.numeroUnico(),
              command.numeroIncidente(), command.sentido());

      if (!usuariosAValidar.isEmpty()) {
        List<MovVotoValidarProyEntity> validaciones =
            usuariosAValidar.stream().map(usuario -> crearValidacion(command, usuario)).toList();
        movVotoValidarProyRepository.saveAll(validaciones);
      }

    } else {

      List<MovVotoDiscordiaEntity> otrosEnDiscordia = movVotoDiscordiaRepository
          .findByNumeroUnicoAndNumeroIncidenteAndNumeroSentidoAndNumeroVotacionAndCodigoUsuarioDiscordiaNot(
              command.numeroUnico(), command.numeroIncidente(), command.sentido(),
              command.votacion(), command.usuarioResponsable());

      if (!otrosEnDiscordia.isEmpty()) {
        List<MovVotoValidarProyEntity> validaciones = otrosEnDiscordia.stream()
            .map(md -> crearValidacion(command, md.getCodigoUsuarioDiscordia())).toList();
        movVotoValidarProyRepository.saveAll(validaciones);
      }
    }
  }

  private void actualizarMovVotoProyecto(RegistrarEnvioProyectoCommand command) {

    var estado =
        maeVotoProyEstadoRepository.findById(EstadoVotoProyecto.POR_VALIDAR.getIdentificador())
            .orElseThrow(() -> new EntityNotFoundException("MaeVotoProyEstadoEntity con ID: "
                + EstadoVotoProyecto.POR_VALIDAR.getIdentificador()));

    var proyecto = movVotoProyectoRepository.findById(command.idProyecto()).orElseThrow(
        () -> new EntityNotFoundException("MovVotoProyectoEntity con ID: " + command.idProyecto()));

    proyecto.setMaeVotoProyEstadoEntity(estado);
    proyecto.setFechaEnvio(ZonedDateTime.now());
    proyecto.setExtension(command.extension());
    proyecto.setUuidAlfresco(command.uuid());
    proyecto.setIpAuditoria(command.ipPublica());
    proyecto.setUsuarioAuditoria(command.usuarioSesion());

    movVotoProyectoRepository.save(proyecto);
  }

  private MovVotoValidarProyEntity crearValidacion(RegistrarEnvioProyectoCommand command,
      String usuarioAValidar) {
    MovVotoValidarProyEntity validacion = new MovVotoValidarProyEntity();
    validacion.setId(command.idProyecto());
    validacion.setUsuarioVal(usuarioAValidar);
    validacion.setIpAuditoria(command.ipPublica());
    validacion.setUsuarioAuditoria(command.usuarioResponsable());
    return validacion;
  }


}
