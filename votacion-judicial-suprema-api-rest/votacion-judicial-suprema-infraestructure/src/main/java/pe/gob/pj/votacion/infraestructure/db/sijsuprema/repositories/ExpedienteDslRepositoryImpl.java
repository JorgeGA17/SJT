package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.time.ZoneId;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.common.enums.Estado;
import pe.gob.pj.votacion.domain.common.enums.Formatos;
import pe.gob.pj.votacion.domain.common.utils.ProjectUtils;
import pe.gob.pj.votacion.domain.model.sijsuprema.Casacion;
import pe.gob.pj.votacion.domain.model.sijsuprema.CasacionRelacionada;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarCasacionesRelacionadasQuery;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QActoProcesalEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QConformacionGrupoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QDistritoJudicialEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QEstadoMaestroEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QExpedienteElevacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QExpedienteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QExpedienteEstadoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QExpedienteSentidoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QExpedienteSentidoVotacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QExpedienteVotacionParteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QFalloEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QGrupoProgramacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QInstanciaEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QInstanciaExpedienteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QMotivoIngresoMaestroEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QProcesoMaestroEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QProgramacionInstanciaEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QSentidoFalloEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QTipoProgramaAudienciaOrganoEntity;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class ExpedienteDslRepositoryImpl implements ExpedienteDslRepository {

  JPAQueryFactory queryFactory;

  public ExpedienteDslRepositoryImpl(@Qualifier("sijsupremaQDSL") JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  @Override
  public List<Casacion> listarCasaciones(String cuo, ListarCasacionesQuery query) {

    var conformacionGrupoEntity = QConformacionGrupoEntity.conformacionGrupoEntity;
    var grupoProgramacionEntity = QGrupoProgramacionEntity.grupoProgramacionEntity;
    var programacionInstanciaEntity = QProgramacionInstanciaEntity.programacionInstanciaEntity;
    var instanciaExpedienteEntity = QInstanciaExpedienteEntity.instanciaExpedienteEntity;
    var motivoIngresoMaestroEntity = QMotivoIngresoMaestroEntity.motivoIngresoMaestroEntity;
    var procesoMestroEntity = QProcesoMaestroEntity.procesoMaestroEntity;
    var expedienteElevacionEntity = QExpedienteElevacionEntity.expedienteElevacionEntity;
    var actoProcesalOrigenEntity = new QActoProcesalEntity("actoProcesalOrigenEntity");
    var actoProcesalJuzgadoEntity = new QActoProcesalEntity("actoProcesalJuzgadoEntity");
    var distritoJudicialOrigenEntity = QDistritoJudicialEntity.distritoJudicialEntity;
    var falloSalaEntity = new QFalloEntity("falloSalaEntity");
    var falloJuzgadoEntity = new QFalloEntity("falloJuzgadoEntity");
    var instanciaEntity = QInstanciaEntity.instanciaEntity;
    var expedienteEstadoEntity = QExpedienteEstadoEntity.expedienteEstadoEntity;
    var expedienteEntity = QExpedienteEntity.expedienteEntity;
    var tipoProgramacionAudienciaOrganoEntity = QTipoProgramaAudienciaOrganoEntity.tipoProgramaAudienciaOrganoEntity;
    var expedienteSentidoEntity = QExpedienteSentidoEntity.expedienteSentidoEntity;
    var expedienteSentidoVotacionEntity = QExpedienteSentidoVotacionEntity.expedienteSentidoVotacionEntity;
    var sentidoFalloEntity = QSentidoFalloEntity.sentidoFalloEntity;
    var estadoMaestroEntity = QEstadoMaestroEntity.estadoMaestroEntity;
    
    var inicio = ProjectUtils.inicioDelDiaLocalDateTime(query.fechaInicio(),
        Formatos.FECHA_YYYY_MM_DD_GUION.getFormato()).atZone(ZoneId.systemDefault());
    var fin = ProjectUtils
        .finDelDiaLocalDateTime(query.fechaFin(), Formatos.FECHA_YYYY_MM_DD_GUION.getFormato())
        .atZone(ZoneId.systemDefault());
    
    return queryFactory
        .select(Projections.constructor(Casacion.class,
            estadoMaestroEntity.codigoEstado,
            estadoMaestroEntity.descripcion,
            programacionInstanciaEntity.codigoProgramacion,
            programacionInstanciaEntity.instanciaEntity.id.codigoDistrito,
            programacionInstanciaEntity.instanciaEntity.id.codigoProvincia,
            programacionInstanciaEntity.instanciaEntity.id.codigoInstancia,
            programacionInstanciaEntity.instanciaEntity.nombreInstancia,
            programacionInstanciaEntity.numeroProgramacion,
            conformacionGrupoEntity.id.numeroGrupo,
            conformacionGrupoEntity.id.numeroSecuencia,
            conformacionGrupoEntity.id.numeroConformacion,
            instanciaExpedienteEntity.id.numeroUnico,
            instanciaExpedienteEntity.id.numeroIncidente,
            instanciaExpedienteEntity.id.fechaIngreso,
            conformacionGrupoEntity.fechaProgramacion,
            instanciaExpedienteEntity.numeroExpedienteSala,
            expedienteEntity.codigoLetra,
            instanciaExpedienteEntity.numeroAnioSala,
            motivoIngresoMaestroEntity.codigoMotivoIngreso,
            motivoIngresoMaestroEntity.descripcionMotivoIngreso,
            instanciaExpedienteEntity.procedenciaMaestroEntity.codigoProcedencia,
            expedienteElevacionEntity.distritoJudicialOrigenEntity.nombreDistrito,
            conformacionGrupoEntity.numeroOrden,
            expedienteEstadoEntity.id.fechaEstado,
            conformacionGrupoEntity.areaEntity.codigoArea,
            expedienteSentidoEntity.id.numeroSentido,
            expedienteSentidoVotacionEntity.id.numeroVotacion,
            conformacionGrupoEntity.codigoUsuarioVocal,
            tipoProgramacionAudienciaOrganoEntity.descripcion,
            tipoProgramacionAudienciaOrganoEntity.abreviatura,
            procesoMestroEntity.descripcionProceso,
            actoProcesalJuzgadoEntity.descripcionActoProcesal,
            expedienteElevacionEntity.numeroFojasJuzgado,
            actoProcesalOrigenEntity.descripcionActoProcesal,
            expedienteElevacionEntity.numeroFojasOrigen,
            falloSalaEntity.codigoFallo,
            falloSalaEntity.descripcion,
            falloJuzgadoEntity.codigoFallo,
            falloJuzgadoEntity.descripcion
            ))
        .from(conformacionGrupoEntity)
        .join(conformacionGrupoEntity.grupoProgramacionEntity, grupoProgramacionEntity)
        .join(grupoProgramacionEntity.programacionInstanciaEntity, programacionInstanciaEntity)
        .join(conformacionGrupoEntity.instanciaExpedienteEntity, instanciaExpedienteEntity)
        .join(instanciaExpedienteEntity.motivoIngresoMaestroEntity, motivoIngresoMaestroEntity)
        .join(instanciaExpedienteEntity.procesoMaestroEntity, procesoMestroEntity)
        .innerJoin(expedienteElevacionEntity)
        .on(expedienteElevacionEntity.id.codigoDistrito.eq(instanciaExpedienteEntity.id.codigoDistrito)
            .and(expedienteElevacionEntity.id.codigoProvincia.eq(instanciaExpedienteEntity.id.codigoProvincia))
            .and(expedienteElevacionEntity.id.codigoInstancia.eq(instanciaExpedienteEntity.id.codigoInstancia))
            .and(expedienteElevacionEntity.id.numeroUnico.eq(instanciaExpedienteEntity.id.numeroUnico))
            .and(expedienteElevacionEntity.id.numeroIncidente.eq(instanciaExpedienteEntity.id.numeroIncidente))
            .and(expedienteElevacionEntity.id.fechaIngreso.eq(instanciaExpedienteEntity.id.fechaIngreso)))
        .join(expedienteElevacionEntity.actoProcesalOrigenEntity, actoProcesalOrigenEntity)
        .leftJoin(expedienteElevacionEntity.actoProcesalJuzgadoEntity, actoProcesalJuzgadoEntity)
        .join(expedienteElevacionEntity.distritoJudicialOrigenEntity, distritoJudicialOrigenEntity)
        .join(expedienteElevacionEntity.falloSalaEntity, falloSalaEntity)
        .leftJoin(expedienteElevacionEntity.falloJuzgadoEntity, falloJuzgadoEntity)
        .join(instanciaExpedienteEntity.instanciaEntity, instanciaEntity)
        .innerJoin(expedienteEstadoEntity)
          .on(expedienteEstadoEntity.id.codigoDistrito.eq(instanciaExpedienteEntity.id.codigoDistrito)
            .and(expedienteEstadoEntity.id.codigoProvincia.eq(instanciaExpedienteEntity.id.codigoProvincia))
            .and(expedienteEstadoEntity.id.codigoInstancia.eq(instanciaExpedienteEntity.id.codigoInstancia))
            .and(expedienteEstadoEntity.id.numeroUnico.eq(instanciaExpedienteEntity.id.numeroUnico))
            .and(expedienteEstadoEntity.id.numeroIncidente.eq(instanciaExpedienteEntity.id.numeroIncidente))
            .and(expedienteEstadoEntity.id.fechaIngreso.eq(instanciaExpedienteEntity.id.fechaIngreso))
            .and(expedienteEstadoEntity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre())))
        .join(instanciaExpedienteEntity.expedienteEntity, expedienteEntity)
        .innerJoin(tipoProgramacionAudienciaOrganoEntity)
          .on(tipoProgramacionAudienciaOrganoEntity.tipoProgramaAudiencia.eq(conformacionGrupoEntity.tipoProgramaAudienciaEntity)
            .and(tipoProgramacionAudienciaOrganoEntity.organoJurisdiccionalEntity.eq(instanciaEntity.organoJurisdiccionalEntity))
            .and(tipoProgramacionAudienciaOrganoEntity.especialidadEntity.eq(expedienteEntity.especialidadEntity)))
        .leftJoin(expedienteSentidoEntity)
          .on(expedienteSentidoEntity.id.numeroUnico.eq(instanciaExpedienteEntity.id.numeroUnico)
              .and(expedienteSentidoEntity.id.numeroIncidente.eq(instanciaExpedienteEntity.id.numeroIncidente))
              .and(expedienteSentidoEntity.activo.eq(Estado.ACTIVO_LETRA.getNombre())))
        .leftJoin(expedienteSentidoVotacionEntity)
          .on(expedienteSentidoVotacionEntity.id.numeroUnico.eq(expedienteSentidoEntity.id.numeroUnico)
              .and(expedienteSentidoVotacionEntity.id.numeroIncidente.eq(expedienteSentidoEntity.id.numeroIncidente))
              .and(expedienteSentidoVotacionEntity.id.numeroSentido.eq(expedienteSentidoEntity.id.numeroSentido)))
        .leftJoin(expedienteSentidoEntity.sentidoFalloEntity, sentidoFalloEntity)
        .leftJoin(estadoMaestroEntity)
          .on(estadoMaestroEntity.codigoEstado.eq(new CaseBuilder()
              .when(expedienteSentidoEntity.codigoEstado.isNotNull())
              .then(expedienteSentidoEntity.codigoEstado)
              .otherwise(expedienteEstadoEntity.estadoMaestroEntity.codigoEstado)))
         .where(instanciaExpedienteEntity.id.codigoDistrito.eq(query.codigoDistrito())
             .and(instanciaExpedienteEntity.id.codigoProvincia.eq(query.codigoProvincia()))
             .and(instanciaExpedienteEntity.id.codigoInstancia.eq(query.codigoInstancia()))
             .and(conformacionGrupoEntity.fechaProgramacion.between(inicio, fin)))
        .fetch();
    
  }

  @Override
  public List<CasacionRelacionada> listarCasacionesRelacionadas(String cuo, ListarCasacionesRelacionadasQuery query) {

    var conformacionGrupoEntity = QConformacionGrupoEntity.conformacionGrupoEntity;
    var grupoProgramacionEntity = QGrupoProgramacionEntity.grupoProgramacionEntity;
    var programacionInstanciaEntity = QProgramacionInstanciaEntity.programacionInstanciaEntity;
    var instanciaEntity = QInstanciaEntity.instanciaEntity;
    var instanciaExpedienteEntity = QInstanciaExpedienteEntity.instanciaExpedienteEntity;
    var expedienteEntity = QExpedienteEntity.expedienteEntity;
    var tipoProgramacionAudienciaOrganoEntity = QTipoProgramaAudienciaOrganoEntity.tipoProgramaAudienciaOrganoEntity;
    var expedienteSentidoEntity = QExpedienteSentidoEntity.expedienteSentidoEntity;
    var expedienteSentidoVotacionEntity = QExpedienteSentidoVotacionEntity.expedienteSentidoVotacionEntity;
    var expedienteVotacionParteEntity = QExpedienteVotacionParteEntity.expedienteVotacionParteEntity;
    var sentidoFalloEntity = QSentidoFalloEntity.sentidoFalloEntity;
    var falloEntity = QFalloEntity.falloEntity;

    var result = queryFactory
        .select(Projections.constructor(CasacionRelacionada.class,
            conformacionGrupoEntity.fechaProgramacion,
            instanciaExpedienteEntity.numeroExpedienteSala,
            expedienteEntity.codigoLetra,
            instanciaExpedienteEntity.numeroAnioSala,
            sentidoFalloEntity.descripcionSentido,
            falloEntity.codigoFallo,
            falloEntity.descripcion,
            tipoProgramacionAudienciaOrganoEntity.descripcion,
            instanciaExpedienteEntity.id.codigoDistrito,
            instanciaExpedienteEntity.id.codigoProvincia,
            instanciaExpedienteEntity.id.codigoInstancia,
            instanciaExpedienteEntity.id.numeroUnico,
            instanciaExpedienteEntity.id.numeroIncidente,
            instanciaExpedienteEntity.id.fechaIngreso,
            grupoProgramacionEntity.id.codigoProgramacion,
            conformacionGrupoEntity.id.numeroGrupo,
            conformacionGrupoEntity.id.numeroSecuencia,
            conformacionGrupoEntity.id.numeroConformacion,
            expedienteSentidoEntity.codigoEstado,
            expedienteSentidoEntity.id.numeroSentido,
            expedienteSentidoVotacionEntity.id.numeroVotacion
        ))
        .from(programacionInstanciaEntity)
        .join(programacionInstanciaEntity.instanciaEntity, instanciaEntity)
        .innerJoin(grupoProgramacionEntity)
        .on(grupoProgramacionEntity.programacionInstanciaEntity.codigoProgramacion.eq(programacionInstanciaEntity.codigoProgramacion))
        .innerJoin(conformacionGrupoEntity)
        .on(conformacionGrupoEntity.id.codigoProgramacion.eq(grupoProgramacionEntity.id.codigoProgramacion)
            .and(conformacionGrupoEntity.id.numeroGrupo.eq(grupoProgramacionEntity.id.numeroGrupo))
            .and(conformacionGrupoEntity.id.numeroSecuencia.eq(grupoProgramacionEntity.id.numeroSecuencia)))
        .join(conformacionGrupoEntity.instanciaExpedienteEntity, instanciaExpedienteEntity)
        .innerJoin(tipoProgramacionAudienciaOrganoEntity)
        .on(tipoProgramacionAudienciaOrganoEntity.organoJurisdiccionalEntity.codigoOrganoJurisdiccional.eq(instanciaEntity.organoJurisdiccionalEntity.codigoOrganoJurisdiccional)
            .and(tipoProgramacionAudienciaOrganoEntity.especialidadEntity.codigoEspecialidad.eq(expedienteEntity.especialidadEntity.codigoEspecialidad))
            .and(tipoProgramacionAudienciaOrganoEntity.tipoProgramaAudiencia.numTipoAudiencia.eq(conformacionGrupoEntity.tipoProgramaAudienciaEntity.numTipoAudiencia)))
        .innerJoin(expedienteSentidoEntity)
        .on(expedienteSentidoEntity.conformacionGrupoEntity.id.codigoProgramacion.eq(conformacionGrupoEntity.id.codigoProgramacion)
        .and(expedienteSentidoEntity.conformacionGrupoEntity.id.numeroGrupo.eq(conformacionGrupoEntity.id.numeroGrupo))
        .and(expedienteSentidoEntity.conformacionGrupoEntity.id.numeroSecuencia.eq(conformacionGrupoEntity.id.numeroSecuencia))
        .and(expedienteSentidoEntity.conformacionGrupoEntity.id.numeroConformacion.eq(conformacionGrupoEntity.id.numeroConformacion))
        .and(expedienteSentidoEntity.activo.eq(Estado.ACTIVO_LETRA.getNombre())))
        .innerJoin(expedienteSentidoVotacionEntity)
        .on(expedienteSentidoVotacionEntity.id.numeroUnico.eq(instanciaExpedienteEntity.id.numeroUnico)
            .and(expedienteSentidoVotacionEntity.id.numeroIncidente.eq(instanciaExpedienteEntity.id.numeroIncidente)
            .and(expedienteSentidoVotacionEntity.id.numeroSentido.eq(expedienteSentidoEntity.id.numeroSentido))
            .and(expedienteSentidoVotacionEntity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre()))))
        .leftJoin(expedienteVotacionParteEntity)
        .on(expedienteVotacionParteEntity.id.unico.eq(instanciaExpedienteEntity.id.numeroUnico)
            .and(expedienteVotacionParteEntity.id.nIncidente.eq(instanciaExpedienteEntity.id.numeroIncidente))
            .and(expedienteVotacionParteEntity.id.nSentido.eq(expedienteSentidoEntity.id.numeroSentido))
            .and(expedienteVotacionParteEntity.id.nVotacion.eq(expedienteSentidoVotacionEntity.id.numeroVotacion))
            .and(expedienteVotacionParteEntity.lActivo.eq(Estado.ACTIVO_LETRA.getNombre()))
            .and(expedienteVotacionParteEntity.lUltimo.eq(Estado.ACTIVO_LETRA.getNombre())))
        .leftJoin(sentidoFalloEntity)
        .on(sentidoFalloEntity.codigoSentido.eq(expedienteVotacionParteEntity.sentidoFallo.codigoSentido))
        .leftJoin(falloEntity)
        .on(falloEntity.codigoFallo.eq(expedienteVotacionParteEntity.cFallo))
        .where(instanciaExpedienteEntity.id.numeroUnico.eq(query.numeroUnico())
            .and(instanciaExpedienteEntity.id.numeroIncidente.eq(query.numeroIncidente()))
            .and(instanciaExpedienteEntity.id.codigoDistrito.eq(query.codigoDistrito()))
            .and(instanciaExpedienteEntity.id.codigoProvincia.eq(query.codigoProvincia()))
            .and(instanciaExpedienteEntity.id.codigoInstancia.eq(query.codigoInstancia()))
            .and(tipoProgramacionAudienciaOrganoEntity.abreviatura.eq(Estado.CALIFICACION_LETRA.getNombre())))
        .fetch();


    return result.stream()
        .map(dto -> new CasacionRelacionada(
            dto.fechaProgramacion(),
            dto.numeroExpedienteSala(),
            dto.codigoLetra(),
            dto.numeroAnioSala(),
            dto.descripcionSentido(),
            dto.codigoFallo(),
            dto.descripcionFallo(),
            dto.descripcionPrograma(),
            dto.codigoDistrito(),
            dto.codigoProvincia(),
            dto.instancia(),
            dto.numeroUnico(),
            dto.numeroIncidente(),
            dto.fechaIngreso(),
            dto.codigoProgramacion(),
            dto.numeroGrupo(),
            dto.numeroSecuencia(),
            dto.numeroConformacion(),
            "FAR".equals(dto.flagDiscordia()) ? "S" : "N",
            dto.codigoSentido(),
            dto.numeroVotacion()
        ))
        .toList();
  }

}
