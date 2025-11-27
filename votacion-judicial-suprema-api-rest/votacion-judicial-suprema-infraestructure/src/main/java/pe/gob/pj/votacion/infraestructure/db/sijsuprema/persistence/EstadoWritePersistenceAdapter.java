package pe.gob.pj.votacion.infraestructure.db.sijsuprema.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarVotoCommand;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.EstadoWritePersistencePort;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ConformacionGrupoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteSentidoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ExpedienteSentidoVotacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.InstanciaExpedienteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.SecuenciaDocumentoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ConformacionGrupoEntityPk;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteSentidoEntityPk;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteSentidoVotacionEntityPk;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.InstanciaExpedienteEntityPk;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.SecuenciaDocumentoEntityPk;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ConformacionGrupoRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ExpedienteSentidoRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ExpedienteSentidoVotacionRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.InstanciaExpedienteRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.ProgramaAudienciaSalaRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories.SecuenciaDocumentoRepository;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class EstadoWritePersistenceAdapter implements EstadoWritePersistencePort {


  ExpedienteSentidoRepository expedienteSentidoRepository;
  SecuenciaDocumentoRepository secuenciaDocumentoRepository;
  ProgramaAudienciaSalaRepository programaAudienciaSalaRepository;
  ConformacionGrupoRepository conformacionGrupoRepository;
  InstanciaExpedienteRepository instanciaExpedienteRepository;
  ExpedienteSentidoVotacionRepository expedienteSentidoVotacionRepository;

  @Override
  public void registrarEstado(String cuo, RegistrarVotoCommand command) {

    String numeroAño = String.valueOf(LocalDate.now().getYear());

    // 1. Obtener expediente
    var expediente = expedienteSentidoRepository.findExpedienteSentido(command.numeroUnico(),
        command.numeroIncidente(), command.codigoDistrito(), command.codigoProvincia(),
        command.codigoInstancia(), command.fechaIngreso(), command.codigoProgramacion(),
        command.numeroGrupoVoto(), command.numeroSecuenciaVoto(), command.numeroConformacionVoto());

    log.info("Expediente encontrado: {}", expediente.orElse(null));

    // 2. Incrementar o crear secuencia
    int numeroSecuencia = incrementarSecuencia(numeroAño, command.codigoDistrito());
    log.info("Secuencia final: {}", numeroSecuencia);

    // 3. Construir ID de registro
    String idRegistroOrigen = String.format("ESE%s%07d", numeroAño, numeroSecuencia);

    // 4. Determinar número de sentido
    int numeroSentido = obtenerNumeroSentido(command.numeroUnico(), command.numeroIncidente(),
        command.codigoAudUid());

    // 5. Guardar expediente_sentido
    guardarExpedienteSentido(command, numeroSentido, idRegistroOrigen);

    // 6. Actualizar otras entidades relacionadas
    programaAudienciaSalaRepository.actualizarProgramaAudienciaSala(numeroSentido,
        command.numeroUnico(), command.numeroIncidente(), command.codigoInstancia(),
        command.fechaIngreso(), command.fechaProgramacion());

    conformacionGrupoRepository.marcarNoReprogramado(command.codigoProgramacion(),
        command.numeroGrupoVoto(), command.numeroSecuenciaVoto(), command.numeroConformacionVoto(),
        ZonedDateTime.now(), "U", command.codigoAudUid());

    instanciaExpedienteRepository.marcarConformado(command.numeroUnico(), command.numeroIncidente(),
        command.codigoInstancia(), command.fechaIngreso(), ZonedDateTime.now(), "U",
        command.codigoAudUid());

    // 7. Insertar expediente_sentido_votacion
    int numeroVotacion =
        calcularNumeroVotacion(command.numeroUnico(), command.numeroIncidente(), numeroSentido);

    ExpedienteSentidoVotacionEntity entity =
        buildExpedienteSentidoVotacionEntity(command, numeroSentido, numeroVotacion);
    expedienteSentidoVotacionRepository.saveAndFlush(entity);

    // 8. Leer la entidad completa inmediatamente
    ExpedienteSentidoVotacionEntity entityCompleta =
        expedienteSentidoVotacionRepository.findById(entity.getId()).orElseThrow(
            () -> new IllegalStateException("No se encontró la entidad después de guardar"));

    log.info("Entidad guardada correctamente: {}", entityCompleta);

    command.numeroSentido(numeroSentido);
    command.numeroVotacion(numeroVotacion);
  }

  private int incrementarSecuencia(String numeroAño, String codigoDistrito) {

    // 1. Intentar incrementar la secuencia existente
    int filasActualizadas =
        secuenciaDocumentoRepository.incrementarSecuencia(numeroAño, codigoDistrito);

    if (filasActualizadas > 0) {
      // Secuencia incrementada correctamente
      return secuenciaDocumentoRepository.getSecuencia(numeroAño, codigoDistrito);
    }

    // 2. Si no existe la fila, crearla
    SecuenciaDocumentoEntityPk pk =
        SecuenciaDocumentoEntityPk.builder().numeroAño(numeroAño).codigoSede(codigoDistrito)
            .codigoOrganoJuris("ALL").codigoEspecialidad("AL").codigoTipo("SEC_TIPO").build();

    SecuenciaDocumentoEntity nuevaSecuencia = new SecuenciaDocumentoEntity();
    nuevaSecuencia.setId(pk);
    nuevaSecuencia.setNumeroSecuencia(1);

    try {
      secuenciaDocumentoRepository.saveAndFlush(nuevaSecuencia);
      return 1; // La nueva secuencia comienza en 1
    } catch (DataIntegrityViolationException ex) {
      // Conflicto de concurrencia: alguien más insertó la fila, intentar incrementar de nuevo
      int filas = secuenciaDocumentoRepository.incrementarSecuencia(numeroAño, codigoDistrito);
      if (filas == 0) {
        throw new IllegalStateException(
            "No se pudo incrementar la secuencia después del conflicto de concurrencia", ex);
      }
      return secuenciaDocumentoRepository.getSecuencia(numeroAño, codigoDistrito);
    } catch (Exception ex) {
      throw new IllegalStateException("Error al crear la secuencia de documento", ex);
    }
  }

  private int obtenerNumeroSentido(BigDecimal numeroUnico, Integer numeroIncidente,
      String codigoAudUid) {

    // 1. Obtener todos los sentidos existentes
    var sentidosExistentes = expedienteSentidoRepository
        .findByIdNumeroUnicoAndIdNumeroIncidente(numeroUnico, numeroIncidente);

    int numeroSentido = sentidosExistentes.size(); // cantidad actual de sentidos

    if (numeroSentido > 0) {
      // 2. Actualizar el campo "ultimo" de los anteriores a "U"
      int filasActualizadas = expedienteSentidoRepository.actualizarUltimo(numeroUnico,
          numeroIncidente, ZonedDateTime.now(), "U", codigoAudUid);

      if (filasActualizadas < 0) {
        throw new IllegalStateException(
            "No se pudo actualizar el campo 'ultimo' de los sentidos anteriores");
      }
    }

    // 3. Retornar el siguiente número de sentido
    return numeroSentido + 1;
  }

  private void guardarExpedienteSentido(RegistrarVotoCommand command, int numeroSentido,
      String idRegistroOrigen) {

    // 1. Construir la PK de ExpedienteSentido
    ExpedienteSentidoEntityPk pk =
        ExpedienteSentidoEntityPk.builder().numeroUnico(command.numeroUnico())
            .numeroIncidente(command.numeroIncidente()).numeroSentido(numeroSentido).build();

    // 2. Construir la entidad principal
    ExpedienteSentidoEntity entity = new ExpedienteSentidoEntity();
    entity.setId(pk);
    entity.setFechaSentido(ZonedDateTime.now());
    entity.setUltimo("S");
    entity.setFechaAuditoria(ZonedDateTime.now());
    entity.setBitacoraAuditoria("I");
    entity.setUsuarioAuditoria(command.codigoAudUid());
    entity.setIpAuditoria(null);
    entity.setDistrito(command.codigoDistrito());
    entity.setProvincia(command.codigoProvincia());
    entity.setInstancia(command.codigoInstancia());
    entity.setFechaIngreso(command.fechaIngreso());
    entity.setRealizado("S");
    entity.setCodigoEstado("900");
    entity.setFechaEstado(ZonedDateTime.now());
    entity.setIdRegistro(idRegistroOrigen);

    // 3. Construir la relación con ConformacionGrupoEntity
    ConformacionGrupoEntityPk conformacionGrupoPk = ConformacionGrupoEntityPk.builder()
        .codigoProgramacion(command.codigoProgramacion()).numeroGrupo(command.numeroGrupoVoto())
        .numeroConformacion(command.numeroConformacionVoto())
        .numeroSecuencia(command.numeroSecuenciaVoto()).build();

    ConformacionGrupoEntity conformacionGrupoEntity = new ConformacionGrupoEntity();
    conformacionGrupoEntity.setId(conformacionGrupoPk);

    // 4. Construir la relación con InstanciaExpedienteEntity
    InstanciaExpedienteEntityPk instanciaPk = InstanciaExpedienteEntityPk.builder()
        .codigoDistrito(command.codigoDistrito()).codigoProvincia(command.codigoProvincia())
        .codigoInstancia(command.codigoInstancia()).numeroUnico(command.numeroUnico())
        .numeroIncidente(command.numeroIncidente()).fechaIngreso(command.fechaIngreso()).build();

    InstanciaExpedienteEntity instanciaExpedienteEntity = new InstanciaExpedienteEntity();
    instanciaExpedienteEntity.setId(instanciaPk);

    entity.setConformacionGrupoEntity(conformacionGrupoEntity);
    entity.setInstanciaExpediente(instanciaExpedienteEntity);

    InstanciaExpedienteEntity instanciaEntity = instanciaExpedienteRepository
        .findInstanciaExpediente(command.codigoDistrito(), command.codigoProvincia(),
            command.codigoInstancia(), command.numeroUnico(), command.numeroIncidente(),
            command.fechaIngreso())
        .orElseThrow(() -> new IllegalStateException(
            "No se encontró instancia_expediente con PK: " + instanciaPk));
    log.info(">>> FECHA BD (entidad): {}", instanciaEntity.getId().getFechaIngreso());
    log.info(">>> FECHA COMMAND (request): {}", command.fechaIngreso());


    // 5. Persistir la entidad
    try {
      expedienteSentidoRepository.saveAndFlush(entity);
      log.info("ExpedienteSentido guardado correctamente: {}", entity);
    } catch (Exception ex) {
      throw new IllegalStateException("Error al guardar ExpedienteSentido", ex);
    }
  }

  private int calcularNumeroVotacion(BigDecimal numeroUnico, Integer numeroIncidente,
      int numeroSentido) {
    try {
      Integer maxVot = expedienteSentidoVotacionRepository.maxVotacion(numeroUnico, numeroIncidente,
          numeroSentido);

      if (maxVot == null) {
        maxVot = 0;
      }

      return maxVot + 1;
    } catch (Exception ex) {
      throw new IllegalStateException("Error al calcular el número de votación", ex);
    }
  }

  private ExpedienteSentidoVotacionEntity buildExpedienteSentidoVotacionEntity(
      RegistrarVotoCommand command, int numeroSentido, int numeroVotacion) {

    // 1. Construir la PK
    ExpedienteSentidoVotacionEntityPk pk = ExpedienteSentidoVotacionEntityPk.builder()
        .numeroUnico(command.numeroUnico()).numeroIncidente(command.numeroIncidente())
        .numeroSentido(numeroSentido).numeroVotacion(numeroVotacion).build();

    // 2. Construir la entidad principal
    ExpedienteSentidoVotacionEntity entity = new ExpedienteSentidoVotacionEntity();
    entity.setId(pk);
    entity.setActivo("S");
    entity.setUltimo("S");
    entity.setEstado("900");
    entity.setCronica("N");
    entity.setFechaCronica(null);
    entity.setUsuarioRelator(null);
    entity.setImpresion("N");
    entity.setRealizado("S");
    entity.setFechaAuditoria(ZonedDateTime.now());
    entity.setBitacoraAuditoria("I");
    entity.setUsuarioAuditoria(command.codigoAudUid());

    return entity;
  }
}
