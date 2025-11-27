package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.InstanciaExpedienteEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "instancia_expediente", schema = EsquemaConstants.DBO)
public class InstanciaExpedienteEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  InstanciaExpedienteEntityPk id;


  @Column(name = "c_conclusion", length = 3)
  String cConclusion;

  @Column(name = "c_motivo_cambio_instancia", length = 3)
  String cMotivoCambioInstancia;

  @Column(name = "n_ano")
  Integer nAno;

  @Column(name = "n_expediente")
  Integer nExpediente;

  @Column(name = "n_cant_folios")
  Integer nCantFolios;

  @Column(name = "l_calificacion", length = 1)
  String lCalificacion = "N";

  @Column(name = "l_apelante", length = 3)
  String lApelante;

  @Column(name = "l_asignado", length = 1)
  String lAsignado;

  @Column(name = "l_repartido", length = 1)
  String lRepartido;

  @Column(name = "f_repartido")
  ZonedDateTime fRepartido;

  @Column(name = "l_ingreso", length = 1)
  String lIngreso;

  @Column(name = "x_secretario", length = 50)
  String xSecretario;

  @Column(name = "n_cuadernos")
  Integer nCuadernos;

  @Column(name = "l_fuera", length = 1)
  String lFuera = "N";

  @Column(name = "l_sec_actas", length = 1)
  String lSecActas;

  @Column(name = "f_conclusion")
  ZonedDateTime fConclusion;

  @Column(name = "f_apelacion")
  ZonedDateTime fApelacion;

  @Column(name = "f_devolucion")
  ZonedDateTime fDevolucion;

  @Column(name = "f_admision_rechazo")
  ZonedDateTime fAdmisionRechazo;

  @Column(name = "c_instancia_destino", length = 3)
  String cInstanciaDestino;

  @Column(name = "f_deriva_ejecucion")
  ZonedDateTime fDerivaEjecucion;

  @Column(name = "c_instancia_ejecucion", length = 3)
  String cInstanciaEjecucion;

  @Column(name = "l_ultimo", length = 1, nullable = false)
  String lUltimo;

  @Column(name = "c_siguiente_equipo", length = 2)
  String cSiguienteEquipo;

  @Column(name = "l_servidor", length = 1)
  String lServidor;

  @Column(name = "x_anaquel", length = 10)
  String xAnaquel;

  @Column(name = "x_fila", length = 10)
  String xFila;

  @Column(name = "l_sent_sala", length = 1)
  String lSentSala;

  @Column(name = "f_servidor")
  ZonedDateTime fServidor;

  @Column(name = "x_resumen_sala", length = 200)
  String xResumenSala;

  @Column(name = "x_obs_conclusion", length = 200)
  String xObsConclusion;

  @Column(name = "x_obs_devolucion", length = 200)
  String xObsDevolucion;

  @Column(name = "c_instancia_ant", length = 3)
  String cInstanciaAnt;

  @Column(name = "n_exp_sala", precision = 16, scale = 0)
  BigDecimal numeroExpedienteSala;

  @Column(name = "n_ano_sala", length = 4)
  String numeroAnioSala;

  @Column(name = "l_obj_registrado", length = 1)
  String lObjRegistrado;

  @Column(name = "x_folios_cuadernos", length = 80)
  String xFoliosCuadernos;

  @Column(name = "c_usuario_redis", length = 15)
  String cUsuarioRedis;

  @Column(name = "l_ind_primero", length = 1)
  String lIndPrimero;

  @Column(name = "l_ultimo_c_org", length = 1)
  String lUltimoCOrg = "S";

  @Column(name = "l_complicado", length = 1)
  String lComplicado = "N";

  @Column(name = "l_ind_redistribucion", length = 1, nullable = false)
  String lIndRedistribucion = "N";

  @Column(name = "c_area", length = 2)
  String cArea;

  @Column(name = "c_procedencia_old", length = 2)
  String cProcedenciaOld;

  @Column(name = "c_proceso_old", length = 3)
  String cProcesoOld;

  @Column(name = "c_motivo_ingreso_old", length = 3)
  String cMotivoIngresoOld;

  @Column(name = "f_inhibicion")
  ZonedDateTime fInhibicion;

  @Column(name = "c_nivel_instancia", length = 1)
  String cNivelInstancia;

  @Column(name = "l_union", length = 1)
  String lUnion;

  @Column(name = "c_motivo_redistribucion", length = 3)
  String cMotivoRedistribucion;

  @Column(name = "l_revisado", length = 1)
  String lRevisado;

  @Column(name = "l_conformado", length = 1, nullable = false)
  String lConformado = "N";

  @Column(name = "f_registro", nullable = false)
  ZonedDateTime fRegistro;

  @Column(name = "l_tipo_juzgado_exp", length = 1)
  String lTipoJuzgadoExp;

  @Column(name = "c_juez", length = 15)
  String cJuez;

  @Column(name = "l_cond_juez", length = 1)
  String lCondJuez;

  @Column(name = "c_proceso_p", length = 3)
  String cProcesoP;

  @Column(name = "l_ind_cfg_proceso", length = 1)
  String lIndCfgProceso = "N";

  @Column(name = "l_cup", length = 3)
  String lCup;

  @Column(name = "_n_expediente_ant")
  Integer nExpedienteAnt;

  @Column(name = "_n_ano_ant")
  Integer nAnoAnt;

  @Column(name = "l_ind_digital", length = 1, nullable = false)
  String lIndDigital = "N";

  @Column(name = "n_funcion")
  Integer nFuncion;

  @Column(name = "c_idCompetencia", length = 2)
  String cIdCompetencia;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false)})
  ExpedienteEntity expedienteEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_proceso", referencedColumnName = "c_proceso")
  ProcesoMaestroEntity procesoMaestroEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_motivo_ingreso", referencedColumnName = "c_motivo_ingreso")
  MotivoIngresoMaestroEntity motivoIngresoMaestroEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_procedencia", referencedColumnName = "c_procedencia")
  ProcedenciaMaestroEntity procedenciaMaestroEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia", insertable = false,
          updatable = false)})
  InstanciaEntity instanciaEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_especialidad", referencedColumnName = "c_especialidad")
  EspecialidadEntity especialidadEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_sub_especialidad", referencedColumnName = "c_especialidad")
  EspecialidadEntity subespecialidadEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_sede", referencedColumnName = "c_sede")
  SedeEntity sedeEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_pais", referencedColumnName = "c_pais")
  PaisEntity paisEntity;
}
