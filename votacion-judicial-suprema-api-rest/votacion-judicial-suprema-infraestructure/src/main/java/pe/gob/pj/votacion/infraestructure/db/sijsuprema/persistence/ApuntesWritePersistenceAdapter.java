package pe.gob.pj.votacion.infraestructure.db.sijsuprema.persistence;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.exceptions.negocio.ExpedienteNoActualizadoException;
import pe.gob.pj.votacion.domain.exceptions.negocio.ExpedienteNoInsertadoException;
import pe.gob.pj.votacion.domain.exceptions.negocio.UsuarioNoProgramadoException;
import pe.gob.pj.votacion.domain.exceptions.negocio.VocalNoProgramadoException;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarVotoCommand;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.ApuntesWritePersistencePort;
import pe.gob.pj.votacion.infraestructure.common.enums.Respuesta;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteVocalEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteVotacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ProgramacionInstanciaVocalEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.SalaColegiadoConformacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteVotacionEntityPk;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ExpedienteVocalRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ExpedienteVotacionRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ProgramacionInstanciaVocalRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.SalaColegiadoConformacionRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ApuntesWritePersistenceAdapter implements ApuntesWritePersistencePort {

  ProgramacionInstanciaVocalRepository programacionInstanciaVocalRepository;
  ExpedienteVocalRepository expedienteVocalEntityRepository;
  ExpedienteVotacionRepository expedienteVotacionEntityRepository;
  SalaColegiadoConformacionRepository salaColegiadoConformacionRepository;

  @Override
  public void registrarApuntes(String cuo, RegistrarVotoCommand command) {

    ZonedDateTime fechaActual = ZonedDateTime.now();

    log.info("numeroSentido {}", command.numeroSentido());

    // Validar usuario pertenece al colegiado
    var usuario = programacionInstanciaVocalRepository
        .findProgramacionInstanciaVocal(command.codigoProgramacion(), command.codigoUsuario());

    if (usuario.isEmpty()) {
      throw new UsuarioNoProgramadoException(Respuesta.USUARIO_NO_PROGRAMADO.getDescripcionRespuesta());
    }

    Integer expvocal0 = expedienteVocalEntityRepository.findMaxNumeroVocal(command.codigoDistrito(),
        command.codigoProvincia(), command.codigoInstancia(), command.numeroUnico(),
        command.numeroIncidente(), command.fechaIngreso());

    Integer expvotacion0 =
        expedienteVotacionEntityRepository.findMaxNumeroVotacion(command.numeroUnico(),
            command.numeroIncidente(), command.numeroSentido()).orElse(0);

    log.info("expvocal0: {}, expvotacion0 {}", expvocal0, expvotacion0);

    // 1. Desactivar l_ultimo en expediente_vocal para fuera de PIV
    try {
      expedienteVocalEntityRepository.updateUltimo(command.codigoDistrito(),
          command.codigoProvincia(), command.codigoInstancia(), command.numeroUnico(),
          command.numeroIncidente(), command.fechaIngreso(), command.codigoProgramacion(),
          fechaActual, "I", command.codigoAudUid(), command.numeroAudIp());
    } catch (Exception e) {
      throw new ExpedienteNoActualizadoException(Respuesta.EXPEDIENTE_ULTIMO_NO_ACTUALIZADO.getDescripcionRespuesta());
    }

    try {
      var fechaLocaDate = command.fechaIngreso().toLocalDateTime();
      var rows = programacionInstanciaVocalRepository.insertarExpedienteVocal(
          command.codigoDistrito(), command.codigoProvincia(), command.codigoInstancia(),
          command.numeroUnico(), command.numeroIncidente(), fechaLocaDate, expvocal0,
          command.codigoProgramacion(), fechaActual, "I", command.codigoAudUid(),
          command.numeroAudIp());

      if (rows == 0) {
        log.info("No se insertó ningún registro en expediente_vocal");
      } else {
        log.info("Se insertaron {} registros en expediente_vocal", rows);
      }

    } catch (Exception e) {
      throw new ExpedienteNoInsertadoException(Respuesta.EXPEDIENTE_VOCAL_NO_INSERTADO.getDescripcionRespuesta());
    }

    try {
      // 1. Traer registros de ExpedienteVocal
      var listaEv = expedienteVocalEntityRepository.findByFiltros(command.codigoDistrito(),
          command.codigoProvincia(), command.codigoInstancia(), command.numeroUnico(),
          command.numeroIncidente(), command.fechaIngreso());

      for (ExpedienteVocalEntity ev : listaEv) {
        // 2. Traer registro de ProgramacionInstanciaVocal
        ProgramacionInstanciaVocalEntity piv =
            programacionInstanciaVocalRepository.findByUsuarioProgramacion(ev.getCodigoUsuario(),
                command.codigoProgramacion(), "S", "N").orElse(null);

        // 3. Traer registro de SalaColegiadoConformacion
        SalaColegiadoConformacionEntity sc = salaColegiadoConformacionRepository
            .findByUsuarioActivo(ev.getCodigoUsuario(), "S", command.codigoDistrito(),
                command.codigoProvincia(), command.codigoInstancia())
            .orElse(null);

        // 4. Condición CASE WHEN
        if (sc != null && sc.getId().getColegiado() != null && sc.getId().getItem() != null) {
          ev.setNColegiado(sc.getId().getColegiado());
          ev.setNItem(sc.getId().getItem());
        } else {
          // Mantener valores originales si no hay SC
          ev.setNColegiado(ev.getNColegiado());
          ev.setNItem(ev.getNItem());
        }

        if (sc != null && sc.getTipoVocalEntity().getId() != null) {
          ev.setCTipoVocal(sc.getTipoVocalEntity().getId());
        }

        if (piv != null) {
          ev.setLNivelInstruccion(piv.getLIndNivelInstruccion());
        }

        ev.setLUltimo("S");
        ev.setFechaAuditoria(fechaActual);
        ev.setBitacoraAuditoria("U");
        ev.setUsuarioAuditoria(command.codigoAudUid());
        ev.setIpAuditoria(command.numeroAudIp());

        boolean actualizar = !"S".equals(ev.getLUltimo())
            || (sc != null && sc.getId().getColegiado() != null
                && !Objects.equals(ev.getNColegiado(), sc.getId().getColegiado()))
            || (sc != null && sc.getId().getItem() != null
                && !Objects.equals(ev.getNItem(), sc.getId().getItem()))
            || (piv != null
                && !Objects.equals(ev.getLNivelInstruccion(), piv.getLIndNivelInstruccion()))
            || (sc != null && sc.getTipoVocalEntity().getId() != null
                && !Objects.equals(ev.getCTipoVocal(), sc.getTipoVocalEntity().getId()));

        if (actualizar) {
          expedienteVocalEntityRepository.save(ev);
        }
      }
    } catch (Exception e) {
      throw new ExpedienteNoActualizadoException(Respuesta.EXPEDIENTE_VOCAL_NO_ACTUALIZADO.getDescripcionRespuesta());
    }

    try {
      // 1. Traer los registros de ProgramacionInstanciaVocal activos
      List<ProgramacionInstanciaVocalEntity> listaPiv = programacionInstanciaVocalRepository
          .findByProgramacionActivos(command.codigoProgramacion(), "S", "N");

      List<ExpedienteVotacionEntity> nuevosApuntes = new ArrayList<>();

      // 2. Obtener el máximo n_votacion existente (equivalente a @ll_expvotacion0)
      int llExpVotacion0 =
          expedienteVotacionEntityRepository.findMaxNumeroVotacion(command.numeroUnico(),
              command.numeroIncidente(), command.numeroSentido()).orElse(0);;

      if (listaPiv.isEmpty()) {
        throw new VocalNoProgramadoException(Respuesta.VOCAL_NO_PROGRAMADO.getDescripcionRespuesta());
      }

      for (ProgramacionInstanciaVocalEntity piv : listaPiv) {
        // 3. Verificar si ya existe expediente votacion
        Optional<ExpedienteVotacionEntity> votoExistente = expedienteVotacionEntityRepository
            .findByIdNumeroUnicoAndIdNumeroIncidenteAndIdNumeroSentidoAndUsuario(
                command.numeroUnico(), command.numeroIncidente(), command.numeroSentido(),
                piv.getCodigoUsuarioVocal());

        if (!votoExistente.isPresent()) {
          // 4. Traer el expediente vocal asociado
          ExpedienteVocalEntity ev = expedienteVocalEntityRepository
              .findByIdNumeroUnicoAndIdNumeroIncidenteAndCodigoUsuario(command.numeroUnico(),
                  command.numeroIncidente(), piv.getCodigoUsuarioVocal())
              .orElseThrow();

          // 5. Contar cuántos vocales anteriores cumplen la condición del SQL
          int countAnterior = (int) listaPiv.stream().filter(piv2 -> piv2.getActivo().equals("S")
              && piv2.getAdicional().equals("N")
              && !expedienteVotacionEntityRepository
                  .existsByIdNumeroUnicoAndIdNumeroIncidenteAndIdNumeroSentidoAndUsuario(
                      command.numeroUnico(), command.numeroIncidente(), command.numeroSentido(),
                      piv2.getCodigoUsuarioVocal())
              && (piv2.getLIndNivelInstruccion().compareTo(piv.getLIndNivelInstruccion()) > 0
                  || (piv2.getLIndNivelInstruccion().equals(piv.getLIndNivelInstruccion())
                      && piv2.getCodigoUsuarioVocal().compareTo(piv.getCodigoUsuarioVocal()) < 0)))
              .count();

          // 6. Calcular nVotacion
          Integer nVotacion = llExpVotacion0 + 1 + countAnterior;

          // 7. Crear nueva entidad
          ExpedienteVotacionEntity voto = new ExpedienteVotacionEntity();
          ExpedienteVotacionEntityPk pk = ExpedienteVotacionEntityPk.builder()
              .numeroUnico(command.numeroUnico()).numeroIncidente(command.numeroIncidente())
              .numeroSentido(command.numeroSentido()).numeroVotacion(nVotacion).build();

          voto.setId(pk);
          voto.setUsuario(piv.getCodigoUsuarioVocal());
          voto.setPonente(
              piv.getCodigoUsuarioVocal().equals(command.codigoUsuarioPonente()) ? "S" : "N");
          voto.setApuntes(
              piv.getCodigoUsuarioVocal().equals(command.codigoUsuario()) ? command.apuntes()
                  : null);
          voto.setUltimo("S");
          voto.setVocal(ev.getId().getNumeroVocal());
          voto.setArea(command.codigoArea());
          voto.setNivelInstruccion(piv.getLIndNivelInstruccion());
          voto.setFechaAuditoria(fechaActual);
          voto.setBitacoraAuditoria("I");
          voto.setUsuarioAuditoria(command.codigoAudUid());
          voto.setIpAuditoria(command.numeroAudIp());

          nuevosApuntes.add(voto);
        }
      }

      // 8. Guardar todos los nuevos registros
      expedienteVotacionEntityRepository.saveAll(nuevosApuntes);

    } catch (Exception e) {
      throw new ExpedienteNoInsertadoException(Respuesta.EXPEDIENTE_VOTACION_NO_INSERTADO.getDescripcionRespuesta());
    }

    try {
      // 1. Obtener lista de votaciones que cumplan con las condiciones base del WHERE
      List<ExpedienteVotacionEntity> listaVotaciones = expedienteVotacionEntityRepository
          .findByIdNumeroUnicoAndIdNumeroIncidenteAndIdNumeroSentido(command.numeroUnico(),
              command.numeroIncidente(), command.numeroSentido());

      // 2. Obtener lista de vocales activos según la programación
      List<ProgramacionInstanciaVocalEntity> listaPiv = programacionInstanciaVocalRepository
          .findByProgramacionActivos(command.codigoProgramacion(), "S", "N");

      // 3. Obtener lista de expediente_vocal relacionados
      List<ExpedienteVocalEntity> listaEv = expedienteVocalEntityRepository.findByFiltros(
          command.codigoDistrito(), command.codigoProvincia(), command.codigoInstancia(),
          command.numeroUnico(), command.numeroIncidente(), command.fechaIngreso());

      // 4. Recorrer cada votación y actualizar si cumple las condiciones
      for (ExpedienteVotacionEntity evt : listaVotaciones) {

        ProgramacionInstanciaVocalEntity piv =
            listaPiv.stream().filter(p -> p.getCodigoUsuarioVocal().equals(evt.getUsuario()))
                .findFirst().orElse(null);

        if (piv == null)
          continue;

        ExpedienteVocalEntity ev = listaEv.stream()
            .filter(e -> e.getCodigoUsuario().equals(evt.getUsuario())).findFirst().orElse(null);

        if (ev == null)
          continue;

        // 5. Evaluar las condiciones del WHERE del SQL ---
        boolean condicionActualizar = !Objects.equals(evt.getUltimo(), "S")
            || !Objects.equals(evt.getPonente(),
                piv.getCodigoUsuarioVocal().equals(command.codigoUsuarioPonente()) ? "S" : "N")
            || !Objects.equals(Optional.ofNullable(evt.getVocal()).orElse(0),
                Optional.ofNullable(ev.getId().getNumeroVocal()).orElse(0))
            || !Objects.equals(Optional.ofNullable(evt.getArea()).orElse(""),
                Optional.ofNullable(command.codigoArea()).orElse(""))
            || !Objects.equals(Optional.ofNullable(evt.getNivelInstruccion()).orElse(" "),
                Optional.ofNullable(piv.getLIndNivelInstruccion()).orElse(" "))
            || (evt.getUsuario().equals(command.codigoUsuario())
                && !Objects.equals(Optional.ofNullable(evt.getApuntes()).orElse(""),
                    Optional.ofNullable(command.apuntes()).orElse("")));

        if (condicionActualizar) {
          // --- 6. Aplicar los SET del SQL ---
          evt.setUltimo("S");
          evt.setPonente(
              piv.getCodigoUsuarioVocal().equals(command.codigoUsuarioPonente()) ? "S" : "N");
          evt.setVocal(ev.getId().getNumeroVocal());
          evt.setArea(command.codigoArea());
          evt.setNivelInstruccion(piv.getLIndNivelInstruccion());

          if (evt.getUsuario().equals(command.codigoUsuario())) {
            evt.setApuntes(command.apuntes());
          }

          evt.setFechaAuditoria(fechaActual);
          evt.setBitacoraAuditoria("U");
          evt.setUsuarioAuditoria(command.codigoAudUid());
          evt.setIpAuditoria(command.numeroAudIp());
        }
      }

      // 7️. Guardar los cambios
      expedienteVotacionEntityRepository.saveAll(listaVotaciones);

    } catch (Exception e) {
      throw new ExpedienteNoActualizadoException(Respuesta.EXPEDIENTE_VOTACION_NO_ACTUALIZADO.getDescripcionRespuesta());
    }

  }
}
