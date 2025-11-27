package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.common.enums.Estado;
import pe.gob.pj.votacion.domain.common.enums.EstadoVotoProyecto;
import pe.gob.pj.votacion.domain.model.sijsuprema.ProyectoValidado;
import pe.gob.pj.votacion.domain.model.sijsuprema.ProyectoVoto;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteEstadoVotacionItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.ReporteGeneralProyectoItem;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ContarProyectosPendientesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosPendientesQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosRelacionadosQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ListarProyectosValidadosQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteEstadoVotacionQuery;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.ReporteGeneralProyectoQuery;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QConformacionGrupoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QEstadoMaestroEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QExpedienteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QExpedienteEstadoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QExpedienteSentidoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QExpedienteSentidoVotacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QExpedienteVotacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QExpedienteVotacionParteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QFalloEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QGrupoProgramacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QInstanciaEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QInstanciaExpedienteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QMaeVotoProyEstadoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QMovVotoProyectoEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QMovVotoValidarProyEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QParteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QParteProgramacionEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QProgramacionInstanciaEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QProgramacionInstanciaVocalEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QSentidoFalloEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QTipoParteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.QUsuarioEntity;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class MovVotoProyectoDslRepositoryImpl implements MovVotoProyectoDslRepository {

  JPAQueryFactory queryFactory;

  public MovVotoProyectoDslRepositoryImpl(
      @Qualifier("sijsupremaQDSL") JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  @Override
  public List<ProyectoVoto> listarPendientes(String cuo, ListarProyectosPendientesQuery query) {

    var movVotoProyectoEnity = QMovVotoProyectoEntity.movVotoProyectoEntity;
    var expedienteSentidoEntity = QExpedienteSentidoEntity.expedienteSentidoEntity;
    var conformacionGrupoEntity = QConformacionGrupoEntity.conformacionGrupoEntity;
    var instanciaExpedienteEntity = QInstanciaExpedienteEntity.instanciaExpedienteEntity;
    var expedienteEntity = QExpedienteEntity.expedienteEntity;
    var expedienteEstadoEntity = QExpedienteEstadoEntity.expedienteEstadoEntity;
    var usuarioEntity = QUsuarioEntity.usuarioEntity;
    var maeVotoProyectoEstadoEntity = QMaeVotoProyEstadoEntity.maeVotoProyEstadoEntity;
    var expedienteVotacionEntity = QExpedienteVotacionEntity.expedienteVotacionEntity;
    
    var joinExpedienteVotacion = new BooleanBuilder();
    var filtro = new BooleanBuilder();
    filtro.and(instanciaExpedienteEntity.id.codigoDistrito.eq(query.codigoDistrito()))
        .and(instanciaExpedienteEntity.id.codigoProvincia.eq(query.codigoProvincia()))
        .and(instanciaExpedienteEntity.id.codigoInstancia.eq(query.codigoInstancia()))
        .and(movVotoProyectoEnity.activo.eq(Estado.ACTIVO_LETRA.getNombre()))
        .and(movVotoProyectoEnity.codigoUsuarioResponsable.eq(query.usuarioResponsable()))
        .and(maeVotoProyectoEstadoEntity.id.eq(query.idEstadoProyecto()));

    if (Objects.equals(query.idEstadoProyecto(), EstadoVotoProyecto.OBSERVADO.getIdentificador())) {
      filtro.and(movVotoProyectoEnity.ultimo.eq(Estado.INACTIVO_LETRA.getNombre()));
    } else {
      filtro.and(movVotoProyectoEnity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre()));
    }

    boolean conFechas = query.fechaInicio() != null && query.fechaFin() != null;
    if (conFechas) {
      filtro.and(conformacionGrupoEntity.fechaProgramacion.between(
          query.fechaInicio().atZone(ZoneId.systemDefault()),
          query.fechaFin().atZone(ZoneId.systemDefault())));
    }

    joinExpedienteVotacion
        .and(expedienteVotacionEntity.id.numeroUnico.eq(expedienteSentidoEntity.id.numeroUnico))
        .and(expedienteVotacionEntity.id.numeroIncidente
            .eq(expedienteSentidoEntity.id.numeroIncidente))
        .and(
            expedienteVotacionEntity.id.numeroSentido.eq(expedienteSentidoEntity.id.numeroSentido));

    if (Objects.equals(query.idEstadoProyecto(),EstadoVotoProyecto.OBSERVADO.getIdentificador()) && conFechas) {
      joinExpedienteVotacion
          .and(expedienteVotacionEntity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre()));
    } else {
      joinExpedienteVotacion
          .and(expedienteVotacionEntity.usuario.eq(usuarioEntity.id.codigoUsuario))
          .and(expedienteVotacionEntity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre()));
    }

    return queryFactory.select(Projections.constructor(ProyectoVoto.class,
        instanciaExpedienteEntity.numeroExpedienteSala,
        expedienteEntity.codigoLetra,
        instanciaExpedienteEntity.numeroAnioSala,
        movVotoProyectoEnity.nId,
        conformacionGrupoEntity.fechaProgramacion,
        expedienteEstadoEntity.id.codigoEstado,
        expedienteEstadoEntity.estadoMaestroEntity.descripcion,
        expedienteSentidoEntity.codigoEstado,
        movVotoProyectoEnity.codigoUsuarioResponsable,
        usuarioEntity.iniciales,
        expedienteVotacionEntity.ponente,
        maeVotoProyectoEstadoEntity.id,
        maeVotoProyectoEstadoEntity.descripcion,
        expedienteSentidoEntity.id.numeroUnico,
        expedienteSentidoEntity.id.numeroIncidente,
        expedienteSentidoEntity.id.numeroSentido,
        movVotoProyectoEnity.numeroVotacion,
        movVotoProyectoEnity.uuidAlfresco,
        movVotoProyectoEnity.extension,
        movVotoProyectoEnity.numeroEnvio,
        movVotoProyectoEnity.fechaEnvio,
        instanciaExpedienteEntity.id.fechaIngreso,
        conformacionGrupoEntity.id.codigoProgramacion,
        conformacionGrupoEntity.id.numeroGrupo,
        conformacionGrupoEntity.id.numeroSecuencia,
        conformacionGrupoEntity.id.numeroConformacion
        ))
        .from(movVotoProyectoEnity)
        .innerJoin(movVotoProyectoEnity.expedienteSentidoEntity, expedienteSentidoEntity)
        .innerJoin(conformacionGrupoEntity)
          .on(conformacionGrupoEntity.id.codigoProgramacion.eq(expedienteSentidoEntity.conformacionGrupoEntity.id.codigoProgramacion)
              .and(conformacionGrupoEntity.id.numeroGrupo.eq(expedienteSentidoEntity.conformacionGrupoEntity.id.numeroGrupo))
              .and(conformacionGrupoEntity.id.numeroSecuencia.eq(expedienteSentidoEntity.conformacionGrupoEntity.id.numeroSecuencia))
              .and(conformacionGrupoEntity.id.numeroConformacion.eq(expedienteSentidoEntity.conformacionGrupoEntity.id.numeroConformacion))
              .and(expedienteSentidoEntity.activo.eq(Estado.ACTIVO_LETRA.getNombre()))
              .and(expedienteSentidoEntity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre())))
        .innerJoin(conformacionGrupoEntity.instanciaExpedienteEntity, instanciaExpedienteEntity)
        .innerJoin(instanciaExpedienteEntity.expedienteEntity, expedienteEntity)
        .innerJoin(expedienteEstadoEntity)
          .on(expedienteEstadoEntity.instanciaExpediente.id.codigoDistrito.eq(instanciaExpedienteEntity.id.codigoDistrito)
              .and(expedienteEstadoEntity.instanciaExpediente.id.codigoProvincia.eq(instanciaExpedienteEntity.id.codigoProvincia))
              .and(expedienteEstadoEntity.instanciaExpediente.id.codigoInstancia.eq(instanciaExpedienteEntity.id.codigoInstancia))
              .and(expedienteEstadoEntity.instanciaExpediente.id.numeroUnico.eq(instanciaExpedienteEntity.id.numeroUnico))
              .and(expedienteEstadoEntity.instanciaExpediente.id.numeroIncidente.eq(instanciaExpedienteEntity.id.numeroIncidente))
              .and(expedienteEstadoEntity.instanciaExpediente.id.fechaIngreso.eq(instanciaExpedienteEntity.id.fechaIngreso))
              .and(expedienteEstadoEntity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre())))
        .innerJoin(usuarioEntity)
          .on(usuarioEntity.id.codigoUsuario.eq(movVotoProyectoEnity.codigoUsuarioResponsable))
        .innerJoin(movVotoProyectoEnity.maeVotoProyEstadoEntity, maeVotoProyectoEstadoEntity)
        .innerJoin(expedienteVotacionEntity).on(joinExpedienteVotacion)
        .where(filtro)
        .orderBy(conformacionGrupoEntity.fechaProgramacion.desc(),
            movVotoProyectoEnity.fechaRegistro.desc())
        .fetch();
  }

  @Override
  public List<ProyectoVoto> listarRelacionados(String cuo, ListarProyectosRelacionadosQuery query) {

    var movVotoProyectoEnity = QMovVotoProyectoEntity.movVotoProyectoEntity;
    var expedienteSentidoEntity = QExpedienteSentidoEntity.expedienteSentidoEntity;
    var conformacionGrupoEntity = QConformacionGrupoEntity.conformacionGrupoEntity;
    var instanciaExpedienteEntity = QInstanciaExpedienteEntity.instanciaExpedienteEntity;
    var expedienteEntity = QExpedienteEntity.expedienteEntity;
    var expedienteEstadoEntity = QExpedienteEstadoEntity.expedienteEstadoEntity;
    var usuarioEntity = QUsuarioEntity.usuarioEntity;
    var maeVotoProyectoEstadoEntity = QMaeVotoProyEstadoEntity.maeVotoProyEstadoEntity;
    var expedienteVotacionEntity = QExpedienteVotacionEntity.expedienteVotacionEntity;

    var joinExpedienteVotacion = new BooleanBuilder();
    var filtro = new BooleanBuilder();
    filtro.and(movVotoProyectoEnity.expedienteSentidoEntity.id.numeroUnico.eq(query.numeroUnico()))
        .and(movVotoProyectoEnity.expedienteSentidoEntity.id.numeroIncidente.eq(query.numeroIndicente()))
        .and(movVotoProyectoEnity.expedienteSentidoEntity.id.numeroSentido.eq(query.numeroSentido()))
        .and(movVotoProyectoEnity.numeroVotacion.eq(query.numeroVotacion()))
        .and(movVotoProyectoEnity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre()))
        .and(movVotoProyectoEnity.activo.eq(Estado.ACTIVO_LETRA.getNombre()))
        .and(movVotoProyectoEnity.codigoUsuarioResponsable.ne(query.usuarioResponsable()));

    joinExpedienteVotacion
        .and(expedienteVotacionEntity.id.numeroUnico.eq(expedienteSentidoEntity.id.numeroUnico))
        .and(expedienteVotacionEntity.id.numeroIncidente
            .eq(expedienteSentidoEntity.id.numeroIncidente))
        .and(
            expedienteVotacionEntity.id.numeroSentido.eq(expedienteSentidoEntity.id.numeroSentido))
        .and(expedienteVotacionEntity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre()));;


    return queryFactory.select(Projections.constructor(ProyectoVoto.class,
            instanciaExpedienteEntity.numeroExpedienteSala,
            expedienteEntity.codigoLetra,
            instanciaExpedienteEntity.numeroAnioSala,
            movVotoProyectoEnity.nId,
            conformacionGrupoEntity.fechaProgramacion,
            expedienteEstadoEntity.id.codigoEstado,
            expedienteEstadoEntity.estadoMaestroEntity.descripcion,
            expedienteSentidoEntity.codigoEstado,
            movVotoProyectoEnity.codigoUsuarioResponsable,
            usuarioEntity.iniciales,
            expedienteVotacionEntity.ponente,
            maeVotoProyectoEstadoEntity.id,
            maeVotoProyectoEstadoEntity.descripcion,
            expedienteSentidoEntity.id.numeroUnico,
            expedienteSentidoEntity.id.numeroIncidente,
            expedienteSentidoEntity.id.numeroSentido,
            movVotoProyectoEnity.numeroVotacion,
            movVotoProyectoEnity.uuidAlfresco,
            movVotoProyectoEnity.extension,
            movVotoProyectoEnity.numeroEnvio,
            movVotoProyectoEnity.fechaEnvio,
            instanciaExpedienteEntity.id.fechaIngreso,
            conformacionGrupoEntity.id.codigoProgramacion,
            conformacionGrupoEntity.id.numeroGrupo,
            conformacionGrupoEntity.id.numeroSecuencia,
            conformacionGrupoEntity.id.numeroConformacion
        ))
        .from(movVotoProyectoEnity)
        .innerJoin(movVotoProyectoEnity.expedienteSentidoEntity, expedienteSentidoEntity)
        .innerJoin(conformacionGrupoEntity)
        .on(conformacionGrupoEntity.id.codigoProgramacion.eq(expedienteSentidoEntity.conformacionGrupoEntity.id.codigoProgramacion)
            .and(conformacionGrupoEntity.id.numeroGrupo.eq(expedienteSentidoEntity.conformacionGrupoEntity.id.numeroGrupo))
            .and(conformacionGrupoEntity.id.numeroSecuencia.eq(expedienteSentidoEntity.conformacionGrupoEntity.id.numeroSecuencia))
            .and(conformacionGrupoEntity.id.numeroConformacion.eq(expedienteSentidoEntity.conformacionGrupoEntity.id.numeroConformacion))
            .and(expedienteSentidoEntity.activo.eq(Estado.ACTIVO_LETRA.getNombre()))
            .and(expedienteSentidoEntity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre())))
        .innerJoin(conformacionGrupoEntity.instanciaExpedienteEntity, instanciaExpedienteEntity)
        .innerJoin(instanciaExpedienteEntity.expedienteEntity, expedienteEntity)
        .innerJoin(expedienteEstadoEntity)
        .on(expedienteEstadoEntity.instanciaExpediente.id.codigoDistrito.eq(instanciaExpedienteEntity.id.codigoDistrito)
            .and(expedienteEstadoEntity.instanciaExpediente.id.codigoProvincia.eq(instanciaExpedienteEntity.id.codigoProvincia))
            .and(expedienteEstadoEntity.instanciaExpediente.id.codigoInstancia.eq(instanciaExpedienteEntity.id.codigoInstancia))
            .and(expedienteEstadoEntity.instanciaExpediente.id.numeroUnico.eq(instanciaExpedienteEntity.id.numeroUnico))
            .and(expedienteEstadoEntity.instanciaExpediente.id.numeroIncidente.eq(instanciaExpedienteEntity.id.numeroIncidente))
            .and(expedienteEstadoEntity.instanciaExpediente.id.fechaIngreso.eq(instanciaExpedienteEntity.id.fechaIngreso))
            .and(expedienteEstadoEntity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre())))
        .innerJoin(usuarioEntity)
        .on(usuarioEntity.id.codigoUsuario.eq(movVotoProyectoEnity.codigoUsuarioResponsable))
        .innerJoin(movVotoProyectoEnity.maeVotoProyEstadoEntity, maeVotoProyectoEstadoEntity)
        .innerJoin(expedienteVotacionEntity).on(joinExpedienteVotacion)
        .where(filtro)
        .fetch();
  }

  @Override
  public Integer contarPendientes(String cuo, ContarProyectosPendientesQuery query) {

    var movVotoProyectoEntity = QMovVotoProyectoEntity.movVotoProyectoEntity;
    var maeVotoProyEstadoEntity = QMaeVotoProyEstadoEntity.maeVotoProyEstadoEntity;

    List<Integer> ids =
            queryFactory
                .select(movVotoProyectoEntity.nId)
                .from(movVotoProyectoEntity)
                .join(movVotoProyectoEntity.maeVotoProyEstadoEntity, maeVotoProyEstadoEntity)
                .where(
                    movVotoProyectoEntity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre())
                        .and(movVotoProyectoEntity.activo.eq(Estado.ACTIVO_LETRA.getNombre()))
                        .and(movVotoProyectoEntity.codigoUsuarioResponsable.eq(query.usuarioResponsable()))
                        .and(maeVotoProyEstadoEntity.id.eq(query.idEstado()))
                )
                .fetch();

    return ids != null ? ids.size() : 0;
}

  @Override
  public List<ProyectoVoto> listarValidados(String cuo, ListarProyectosValidadosQuery query) {

    var movVotoProyectoEnity = QMovVotoProyectoEntity.movVotoProyectoEntity;
    var expedienteSentidoEntity = QExpedienteSentidoEntity.expedienteSentidoEntity;
    var conformacionGrupoEntity = QConformacionGrupoEntity.conformacionGrupoEntity;
    var instanciaExpedienteEntity = QInstanciaExpedienteEntity.instanciaExpedienteEntity;
    var expedienteEntity = QExpedienteEntity.expedienteEntity;
    var expedienteEstadoEntity = QExpedienteEstadoEntity.expedienteEstadoEntity;
    var usuarioEntity = QUsuarioEntity.usuarioEntity;
    var maeVotoProyectoEstadoEntity = QMaeVotoProyEstadoEntity.maeVotoProyEstadoEntity;
    var expedienteVotacionEntity = QExpedienteVotacionEntity.expedienteVotacionEntity;
    var movVotoValidarProyEntity = QMovVotoValidarProyEntity.movVotoValidarProyEntity;

    var joinExpedienteVotacion = new BooleanBuilder();
    var joinMovVotoValidarProy = new BooleanBuilder();
    var filtro = new BooleanBuilder();
    filtro.and(instanciaExpedienteEntity.id.codigoDistrito.eq(query.codigoDistrito()))
        .and(instanciaExpedienteEntity.id.codigoProvincia.eq(query.codigoProvincia()))
        .and(instanciaExpedienteEntity.id.codigoInstancia.eq(query.codigoInstancia()))
        .and(movVotoProyectoEnity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre()))
        .and(movVotoProyectoEnity.activo.eq(Estado.ACTIVO_LETRA.getNombre()))
        .and(movVotoProyectoEnity.codigoUsuarioResponsable.ne(query.usuarioResponsable()))
        .and(maeVotoProyectoEstadoEntity.id.eq(query.idEstadoProyecto()));

    boolean conFechas = query.fechaInicio() != null && query.fechaFin() != null;
    if (conFechas) {
      filtro.and(conformacionGrupoEntity.fechaProgramacion.between(
          query.fechaInicio().atZone(ZoneId.systemDefault()),
          query.fechaFin().atZone(ZoneId.systemDefault())));
    }

    joinExpedienteVotacion
        .and(expedienteVotacionEntity.id.numeroUnico.eq(expedienteSentidoEntity.id.numeroUnico))
        .and(expedienteVotacionEntity.id.numeroIncidente
            .eq(expedienteSentidoEntity.id.numeroIncidente))
        .and(expedienteVotacionEntity.id.numeroSentido.eq(expedienteSentidoEntity.id.numeroSentido))
        .and(expedienteVotacionEntity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre()));

    joinMovVotoValidarProy
        .and(movVotoValidarProyEntity.proyecto.eq(movVotoProyectoEnity))
        .and(movVotoValidarProyEntity.numeroValidado.eq(0))
        .and(movVotoValidarProyEntity.usuarioVal.eq(query.usuarioResponsable()));

    return queryFactory.select(Projections.constructor(ProyectoVoto.class,
            instanciaExpedienteEntity.numeroExpedienteSala,
            expedienteEntity.codigoLetra,
            instanciaExpedienteEntity.numeroAnioSala,
            movVotoProyectoEnity.nId,
            conformacionGrupoEntity.fechaProgramacion,
            expedienteEstadoEntity.id.codigoEstado,
            expedienteEstadoEntity.estadoMaestroEntity.descripcion,
            expedienteSentidoEntity.codigoEstado,
            movVotoProyectoEnity.codigoUsuarioResponsable,
            usuarioEntity.iniciales,
            expedienteVotacionEntity.ponente,
            maeVotoProyectoEstadoEntity.id,
            maeVotoProyectoEstadoEntity.descripcion,
            expedienteSentidoEntity.id.numeroUnico,
            expedienteSentidoEntity.id.numeroIncidente,
            expedienteSentidoEntity.id.numeroSentido,
            movVotoProyectoEnity.numeroVotacion,
            movVotoProyectoEnity.uuidAlfresco,
            movVotoProyectoEnity.extension,
            movVotoProyectoEnity.numeroEnvio,
            movVotoProyectoEnity.fechaEnvio,
            instanciaExpedienteEntity.id.fechaIngreso,
            conformacionGrupoEntity.id.codigoProgramacion,
            conformacionGrupoEntity.id.numeroGrupo,
            conformacionGrupoEntity.id.numeroSecuencia,
            conformacionGrupoEntity.id.numeroConformacion
        ))
        .from(movVotoProyectoEnity)
        .innerJoin(movVotoProyectoEnity.expedienteSentidoEntity, expedienteSentidoEntity)
        .innerJoin(conformacionGrupoEntity)
        .on(conformacionGrupoEntity.id.codigoProgramacion.eq(expedienteSentidoEntity.conformacionGrupoEntity.id.codigoProgramacion)
            .and(conformacionGrupoEntity.id.numeroGrupo.eq(expedienteSentidoEntity.conformacionGrupoEntity.id.numeroGrupo))
            .and(conformacionGrupoEntity.id.numeroSecuencia.eq(expedienteSentidoEntity.conformacionGrupoEntity.id.numeroSecuencia))
            .and(conformacionGrupoEntity.id.numeroConformacion.eq(expedienteSentidoEntity.conformacionGrupoEntity.id.numeroConformacion))
            .and(expedienteSentidoEntity.activo.eq(Estado.ACTIVO_LETRA.getNombre()))
            .and(expedienteSentidoEntity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre())))
        .innerJoin(conformacionGrupoEntity.instanciaExpedienteEntity, instanciaExpedienteEntity)
        .innerJoin(instanciaExpedienteEntity.expedienteEntity, expedienteEntity)
        .innerJoin(expedienteEstadoEntity)
        .on(expedienteEstadoEntity.instanciaExpediente.id.codigoDistrito.eq(instanciaExpedienteEntity.id.codigoDistrito)
            .and(expedienteEstadoEntity.instanciaExpediente.id.codigoProvincia.eq(instanciaExpedienteEntity.id.codigoProvincia))
            .and(expedienteEstadoEntity.instanciaExpediente.id.codigoInstancia.eq(instanciaExpedienteEntity.id.codigoInstancia))
            .and(expedienteEstadoEntity.instanciaExpediente.id.numeroUnico.eq(instanciaExpedienteEntity.id.numeroUnico))
            .and(expedienteEstadoEntity.instanciaExpediente.id.numeroIncidente.eq(instanciaExpedienteEntity.id.numeroIncidente))
            .and(expedienteEstadoEntity.instanciaExpediente.id.fechaIngreso.eq(instanciaExpedienteEntity.id.fechaIngreso))
            .and(expedienteEstadoEntity.ultimo.eq(Estado.ACTIVO_LETRA.getNombre())))
        .innerJoin(usuarioEntity)
        .on(usuarioEntity.id.codigoUsuario.eq(movVotoProyectoEnity.codigoUsuarioResponsable))
        .innerJoin(movVotoProyectoEnity.maeVotoProyEstadoEntity, maeVotoProyectoEstadoEntity)
        .innerJoin(expedienteVotacionEntity).on(joinExpedienteVotacion)
        .innerJoin(movVotoValidarProyEntity).on(joinMovVotoValidarProy)
        .where(filtro)
        .orderBy(conformacionGrupoEntity.fechaProgramacion.desc(),
            movVotoProyectoEnity.fechaRegistro.desc())
        .fetch();
  }

  @Override
  public List<ProyectoValidado> listarValidacionesProyecto(String cuo,
      List<Integer> idsProyectos) {

    var movVotoValidarProyEntity = QMovVotoValidarProyEntity.movVotoValidarProyEntity;
    var usuarioEntity = QUsuarioEntity.usuarioEntity;
    
    return queryFactory.select(Projections.constructor(ProyectoValidado.class,
        movVotoValidarProyEntity.id,
        movVotoValidarProyEntity.usuarioVal,
        movVotoValidarProyEntity.numeroValidado,
        movVotoValidarProyEntity.observacion,
        usuarioEntity.iniciales))
        .from(movVotoValidarProyEntity)
        .innerJoin(usuarioEntity)
          .on(usuarioEntity.id.codigoUsuario.eq(movVotoValidarProyEntity.usuarioVal))
        .where(movVotoValidarProyEntity.id.in(idsProyectos))
        .fetch();
  }

  @Override
  public List<ReporteEstadoVotacionItem> findReporteEstadoVotacion(String cuo,
      ReporteEstadoVotacionQuery query) {

    var programacionInstanciaEntity = QProgramacionInstanciaEntity.programacionInstanciaEntity;
    var instanciaEntity = QInstanciaEntity.instanciaEntity;
    var grupoProgramacionEntity = QGrupoProgramacionEntity.grupoProgramacionEntity;
    var conformacionGrupoEntity = QConformacionGrupoEntity.conformacionGrupoEntity;
    var programacionInstanciaVocalEntity =
        QProgramacionInstanciaVocalEntity.programacionInstanciaVocalEntity;
    var instanciaExpedienteEntity = QInstanciaExpedienteEntity.instanciaExpedienteEntity;
    var expedienteEntity = QExpedienteEntity.expedienteEntity;
    var expedienteEstadoEntity = QExpedienteEstadoEntity.expedienteEstadoEntity;
    var expedienteSentidoEntity = QExpedienteSentidoEntity.expedienteSentidoEntity;
    var estadoMaestroEntityE = new QEstadoMaestroEntity("estadoMaestroEntityE");
    var estadoMaestroEntityS = new QEstadoMaestroEntity("estadoMaestroEntityS");
    var expedienteSentidoVotacionEntity =
        new QExpedienteSentidoVotacionEntity("expedienteSentidoVotacionEntity3");
    var parteProgramacionEntity = QParteProgramacionEntity.parteProgramacionEntity;
    var parteEntity = QParteEntity.parteEntity;
    var tipoParteEntity = QTipoParteEntity.tipoParteEntity;
    var expedienteVotacionParteEntity =
        new QExpedienteVotacionParteEntity("expedienteVotacionParteEntity");
    var sentidoFalloEntity = QSentidoFalloEntity.sentidoFalloEntity;
    var falloEntity = QFalloEntity.falloEntity;

    var subSentido = JPAExpressions.select(sentidoFalloEntity.descripcionSentido)
        .from(sentidoFalloEntity).where(sentidoFalloEntity.codigoSentido
            .eq(expedienteVotacionParteEntity.sentidoFallo.codigoSentido));
    var subFallo = JPAExpressions.select(falloEntity.descripcion).from(falloEntity)
        .where(falloEntity.codigoFallo.eq(expedienteVotacionParteEntity.cFallo));

      var evpSub = new QExpedienteVotacionParteEntity("evpSub");
      var fSub = new QFalloEntity("fSub");
      StringExpression estadoVotoExpression = new CaseBuilder()
          .when(JPAExpressions.selectOne()
              .from(evpSub)
              .join(fSub).on(evpSub.cFallo.eq(fSub.codigoFallo))
              .where(evpSub.id.unico.eq(instanciaExpedienteEntity.id.numeroUnico),
                  evpSub.id.nIncidente.eq(instanciaExpedienteEntity.id.numeroIncidente), 
                  evpSub.lActivo.eq("S"),
                  evpSub.lUltimo.eq("S"), 
                  fSub.descripcion.eq("AL VOTO"),
                  evpSub.id.nSentido.eq(expedienteSentidoEntity.id.numeroSentido),
                  evpSub.id.nVotacion.eq(expedienteSentidoVotacionEntity.id.numeroVotacion))
              .exists()
          )
          .then(Expressions.stringTemplate("'AL VOTO'"))
          .when(estadoMaestroEntityS.descripcion.isNotNull())
            .then(estadoMaestroEntityS.descripcion)
          .otherwise(estadoMaestroEntityE.descripcion);

      var filtro = new BooleanBuilder();
      filtro.and(programacionInstanciaEntity.instanciaEntity.id.codigoDistrito.eq(query.codigoDistrito()));
      filtro.and(programacionInstanciaEntity.instanciaEntity.id.codigoProvincia.eq(query.codigoProvincia()));
      filtro.and(programacionInstanciaEntity.instanciaEntity.id.codigoInstancia.eq(query.codigoInstancia()));

      if (query.fechaInicio() != null && query.fechaFin() != null) {
          filtro.and(conformacionGrupoEntity.fechaProgramacion
              .between(query.fechaInicio().atZone(ZoneId.systemDefault()), 
                  query.fechaFin().atZone(ZoneId.systemDefault())));
      }

      if (query.idEstadoVotacion() != null && !query.idEstadoVotacion().isBlank()) {
          if ("495".equals(query.idEstadoVotacion())) {
              filtro.and(expedienteVotacionParteEntity.cFallo.eq(1727));
          } else {
              filtro.and(expedienteEstadoEntity.id.codigoEstado.eq(query.idEstadoVotacion()));
              if ("900".equals(query.idEstadoVotacion())) {
                  filtro.and(expedienteVotacionParteEntity.cFallo.ne(1727)
                      .or(expedienteVotacionParteEntity.cFallo.isNull()));
              } else {
                  filtro.and(expedienteVotacionParteEntity.cFallo.ne(1727)
                      .or(expedienteVotacionParteEntity.cFallo.isNull()));
              }
          }
      }

      return queryFactory
          .select(Projections.constructor(ReporteEstadoVotacionItem.class,
              conformacionGrupoEntity.fechaProgramacion, 
              conformacionGrupoEntity.codigoUsuarioVocal,
              programacionInstanciaVocalEntity.lIndNivelInstruccion, 
              estadoVotoExpression,
              tipoParteEntity.xAbreviatura.coalesce(tipoParteEntity.id.lTipoParte), 
              subSentido, 
              subFallo,
              instanciaExpedienteEntity.numeroExpedienteSala, 
              expedienteEntity.codigoLetra,
              instanciaExpedienteEntity.numeroAnioSala, 
              parteEntity.apePaterno, 
              parteEntity.apeMaterno,
              parteEntity.nombres, 
              parteEntity.tipoPersona))
          .from(programacionInstanciaEntity)
          .join(programacionInstanciaEntity.instanciaEntity, instanciaEntity)
          .join(grupoProgramacionEntity)
            .on(grupoProgramacionEntity.programacionInstanciaEntity.eq(programacionInstanciaEntity))
          .join(conformacionGrupoEntity)
            .on(conformacionGrupoEntity.grupoProgramacionEntity.eq(grupoProgramacionEntity))
          .leftJoin(programacionInstanciaVocalEntity)
            .on(programacionInstanciaVocalEntity.programacionInstancia
                .eq(programacionInstanciaEntity)
              .and(programacionInstanciaVocalEntity.codigoUsuarioVocal
                  .eq(conformacionGrupoEntity.codigoUsuarioVocal)))
          .join(conformacionGrupoEntity.instanciaExpedienteEntity, instanciaExpedienteEntity)
          .join(instanciaExpedienteEntity.expedienteEntity, expedienteEntity)
          .join(expedienteEstadoEntity)
            .on(expedienteEstadoEntity.instanciaExpediente.eq(instanciaExpedienteEntity)
                .and(expedienteEstadoEntity.ultimo.eq("S")))
          .leftJoin(expedienteSentidoEntity)
            .on(expedienteSentidoEntity.conformacionGrupoEntity.eq(conformacionGrupoEntity)
                .and(expedienteSentidoEntity.activo.eq("S")))
          .leftJoin(estadoMaestroEntityE)
            .on(estadoMaestroEntityE.codigoEstado.eq(expedienteEstadoEntity.id.codigoEstado))
          .leftJoin(estadoMaestroEntityS)
            .on(estadoMaestroEntityS.codigoEstado.eq(expedienteSentidoEntity.codigoEstado))
          .leftJoin(expedienteSentidoVotacionEntity)
            .on(expedienteSentidoVotacionEntity.expedienteSentido.eq(expedienteSentidoEntity)
                .and(expedienteSentidoVotacionEntity.ultimo.eq("S")))
          .join(parteProgramacionEntity)
            .on(parteProgramacionEntity.id.nUnico.eq(instanciaExpedienteEntity.id.numeroUnico)
              .and(parteProgramacionEntity.id.nIncidente
                  .eq(instanciaExpedienteEntity.id.numeroIncidente))
              .and(parteProgramacionEntity.id.cProgramacion
                  .eq(programacionInstanciaEntity.codigoProgramacion))
              .and(parteProgramacionEntity.publicadoTablilla.eq("S")))
          .join(parteProgramacionEntity.parte, parteEntity)
            .on(parteEntity.activo.eq("S")
                .and(parteEntity.recurrente.eq("S")))
          .join(parteEntity.tipoParteEntity, tipoParteEntity)
          .leftJoin(expedienteVotacionParteEntity)
            .on(expedienteVotacionParteEntity.expedienteSentidoVotacionEntity
                .eq(expedienteSentidoVotacionEntity)
              .and(expedienteVotacionParteEntity.id.nSecuenciaParte.eq(parteEntity.id.nSecuencia)))
          .where(filtro)
          .orderBy(conformacionGrupoEntity.fechaProgramacion.desc(),
              instanciaExpedienteEntity.numeroExpedienteSala.asc())
          .fetch();
      
  }

  @Override
  public List<ReporteGeneralProyectoItem> findReporteGeneralProyecto(String cuo,
      ReporteGeneralProyectoQuery query) {

    var programacionInstanciaEntity = QProgramacionInstanciaEntity.programacionInstanciaEntity;
    var grupoProgramacionEntity = QGrupoProgramacionEntity.grupoProgramacionEntity;
    var conformacionGrupoEntity = QConformacionGrupoEntity.conformacionGrupoEntity;
    var instanciaExpedienteEntity = QInstanciaExpedienteEntity.instanciaExpedienteEntity;
    var expedienteEntity = QExpedienteEntity.expedienteEntity;
    var expedienteEstadoEntity = QExpedienteEstadoEntity.expedienteEstadoEntity;
    var parteProgramacionEntity = QParteProgramacionEntity.parteProgramacionEntity;
    var parteEntity = QParteEntity.parteEntity;
    var tipoParteEntity = QTipoParteEntity.tipoParteEntity;
    var expedienteSentidoEntity = QExpedienteSentidoEntity.expedienteSentidoEntity;
    var expedienteSentidoVotacionEntity =
        QExpedienteSentidoVotacionEntity.expedienteSentidoVotacionEntity;
    var expedienteVotacionParteEntity =
        QExpedienteVotacionParteEntity.expedienteVotacionParteEntity;
    var movVotoProyectoEntity = QMovVotoProyectoEntity.movVotoProyectoEntity;
    var maeVotoProyEstadoEntity = QMaeVotoProyEstadoEntity.maeVotoProyEstadoEntity;
    var estadoMaestroEntityE = new QEstadoMaestroEntity("estadoMaestroEntityE");
    var estadoMaestroEntityS = new QEstadoMaestroEntity("estadoMaestroEntityS");
    var sentidoFalloEntity = QSentidoFalloEntity.sentidoFalloEntity;
    var falloEntity = QFalloEntity.falloEntity;

    var subSentido = JPAExpressions.select(sentidoFalloEntity.descripcionSentido)
        .from(sentidoFalloEntity).where(sentidoFalloEntity.codigoSentido
            .eq(expedienteVotacionParteEntity.sentidoFallo.codigoSentido));
    var subFallo = JPAExpressions.select(falloEntity.descripcion).from(falloEntity)
        .where(falloEntity.codigoFallo.eq(expedienteVotacionParteEntity.cFallo));
    StringExpression estadoVotoExpression =
        new CaseBuilder().when(estadoMaestroEntityS.descripcion.isNotNull())
            .then(estadoMaestroEntityS.descripcion).otherwise(estadoMaestroEntityE.descripcion);

    var filtro = new BooleanBuilder();
    filtro.and(
        programacionInstanciaEntity.instanciaEntity.id.codigoDistrito.eq(query.codigoDistrito()));
    filtro.and(
        programacionInstanciaEntity.instanciaEntity.id.codigoProvincia.eq(query.codigoProvincia()));
    filtro.and(
        programacionInstanciaEntity.instanciaEntity.id.codigoInstancia.eq(query.codigoInstancia()));

    if (query.fechaInicio() != null && query.fechaFin() != null) {
      filtro.and(conformacionGrupoEntity.fechaProgramacion.between(
          query.fechaInicio().atZone(ZoneId.systemDefault()),
          query.fechaFin().atZone(ZoneId.systemDefault())));
    }

    boolean responsableEspecifico =
        query.usuarioResponsable() != null && !query.usuarioResponsable().isBlank();
    boolean estadoVotacionEspecifico =
        query.idEstadoVotacion() != null && !query.idEstadoVotacion().isBlank();
    boolean estadoProyectoEspecifico =
        query.idEstadoProyecto() != null && query.idEstadoProyecto() != 0;

    if (responsableEspecifico) {
      filtro.and(movVotoProyectoEntity.codigoUsuarioResponsable.eq(query.usuarioResponsable()));
    }
    if (estadoVotacionEspecifico && !"900".equals(query.idEstadoVotacion())
        && !"495".equals(query.idEstadoVotacion())) {
      filtro.and(expedienteEstadoEntity.id.codigoEstado.eq(query.idEstadoVotacion()));
    }
    if (estadoProyectoEspecifico) {
      if (query.idEstadoProyecto() == 1) { // PENDIENTE
        filtro.and(movVotoProyectoEntity.maeVotoProyEstadoEntity.isNull());
      } else {
        filtro.and(movVotoProyectoEntity.maeVotoProyEstadoEntity.id.eq(query.idEstadoProyecto()));
      }
    }

    return queryFactory.select(
              Projections.constructor(ReporteGeneralProyectoItem.class,
                  conformacionGrupoEntity.fechaProgramacion,
                  conformacionGrupoEntity.codigoUsuarioVocal,
                  estadoVotoExpression,
                  tipoParteEntity.xAbreviatura.coalesce(tipoParteEntity.id.lTipoParte),
                  subSentido,
                  subFallo,
                  expedienteVotacionParteEntity.xAnotacion,
                  movVotoProyectoEntity.codigoUsuarioResponsable,
                  maeVotoProyEstadoEntity.descripcion.coalesce("PENDIENTE"),
                  movVotoProyectoEntity.fechaEnvio,
                  movVotoProyectoEntity.nId,
                  instanciaExpedienteEntity.numeroExpedienteSala,
                  expedienteEntity.codigoLetra,
                  instanciaExpedienteEntity.numeroAnioSala,
                  parteEntity.apePaterno,
                  parteEntity.apeMaterno,
                  parteEntity.nombres,
                  parteEntity.tipoPersona
              ))
          .from(programacionInstanciaEntity)
          .join(programacionInstanciaEntity.instanciaEntity)
          .join(grupoProgramacionEntity)
            .on(grupoProgramacionEntity.programacionInstanciaEntity.eq(programacionInstanciaEntity))
          .join(conformacionGrupoEntity)
            .on(conformacionGrupoEntity.grupoProgramacionEntity.eq(grupoProgramacionEntity))
          .join(conformacionGrupoEntity.instanciaExpedienteEntity, instanciaExpedienteEntity)
          .join(instanciaExpedienteEntity.expedienteEntity, expedienteEntity)
          .join(expedienteEstadoEntity)
            .on(expedienteEstadoEntity.instanciaExpediente.eq(instanciaExpedienteEntity)
              .and(expedienteEstadoEntity.ultimo.eq("S")))
          .join(parteProgramacionEntity)
            .on(parteProgramacionEntity.programacionInstancia.eq(programacionInstanciaEntity)
              .and(parteProgramacionEntity.id.nUnico.eq(instanciaExpedienteEntity.id.numeroUnico))
              .and(parteProgramacionEntity.id.nIncidente
                  .eq(instanciaExpedienteEntity.id.numeroIncidente))
              .and(parteProgramacionEntity.publicadoTablilla.eq("S")))
          .join(parteProgramacionEntity.parte, parteEntity).on(parteEntity.activo.eq("S")
              .and(parteEntity.recurrente.eq("S")))
          .join(parteEntity.tipoParteEntity, tipoParteEntity)
          .leftJoin(expedienteSentidoEntity)
            .on(expedienteSentidoEntity.conformacionGrupoEntity.eq(conformacionGrupoEntity)
              .and(expedienteSentidoEntity.activo.eq("S"))
              .and(expedienteSentidoEntity.ultimo.eq("S")))
          .leftJoin(expedienteSentidoVotacionEntity)
            .on(expedienteSentidoVotacionEntity.expedienteSentido.eq(expedienteSentidoEntity)
              .and(expedienteSentidoVotacionEntity.ultimo.eq("S")))
          .leftJoin(expedienteVotacionParteEntity)
            .on(expedienteVotacionParteEntity.expedienteSentidoVotacionEntity
                .eq(expedienteSentidoVotacionEntity)
              .and(expedienteVotacionParteEntity.id.nSecuenciaParte.eq(parteEntity.id.nSecuencia)))
          .leftJoin(movVotoProyectoEntity)
            .on(movVotoProyectoEntity.expedienteSentidoEntity.eq(expedienteSentidoEntity)
              .and(movVotoProyectoEntity.numeroVotacion
                  .eq(expedienteSentidoVotacionEntity.id.numeroVotacion))
              .and(movVotoProyectoEntity.ultimo.eq("S"))
              .and(movVotoProyectoEntity.activo.eq("S")))
          .leftJoin(movVotoProyectoEntity.maeVotoProyEstadoEntity, maeVotoProyEstadoEntity)
          .leftJoin(estadoMaestroEntityE)
            .on(estadoMaestroEntityE.codigoEstado.eq(expedienteEstadoEntity.id.codigoEstado))
          .leftJoin(estadoMaestroEntityS)
            .on(estadoMaestroEntityS.codigoEstado.eq(expedienteSentidoEntity.codigoEstado))
          .where(filtro)
          .orderBy(conformacionGrupoEntity.fechaProgramacion.desc())
          .fetch();
  }

  @Override
  public Map<Integer, String> findMagistradosPendientes() {
    var movVotoValidarProyEntity = QMovVotoValidarProyEntity.movVotoValidarProyEntity;
    List<Tuple> results = queryFactory
        .select(movVotoValidarProyEntity.proyecto.nId, movVotoValidarProyEntity.usuarioVal)
        .from(movVotoValidarProyEntity).where(movVotoValidarProyEntity.numeroValidado.eq(0))
        .fetch();
    return results.stream()
        .collect(Collectors.groupingBy(tuple -> tuple.get(movVotoValidarProyEntity.proyecto.nId),
            Collectors.mapping(tuple -> tuple.get(movVotoValidarProyEntity.usuarioVal),
                Collectors.joining(" - "))));
  }

}
