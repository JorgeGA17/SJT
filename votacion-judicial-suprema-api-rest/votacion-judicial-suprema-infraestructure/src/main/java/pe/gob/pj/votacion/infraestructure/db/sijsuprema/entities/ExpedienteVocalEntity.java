package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteVocalEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "expediente_vocal", schema = EsquemaConstants.DBO)
public class ExpedienteVocalEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  ExpedienteVocalEntityPk id;

  @Column(name = "c_usuario", length = 15, nullable = false)
  String codigoUsuario;

  @Column(name = "n_colegiado")
  Integer nColegiado;

  @Column(name = "n_item")
  Integer nItem;

  @Column(name = "l_ultimo", length = 1)
  String lUltimo;

  @Column(name = "c_tipo_vocal")
  Integer cTipoVocal;

  @Column(name = "c_sede", length = 4)
  String cSede;

  @Column(name = "n_secuencial_group")
  Integer nSecuencialGroup;

  @Column(name = "n_ano_group")
  Integer nAnoGroup;

  @Column(name = "n_secuencial_group_det")
  Integer nSecuencialGroupDet;

  @Column(name = "x_motivo_reasignacion", length = 200)
  String xMotivoReasignacion;

  @Column(name = "f_ultimo")
  ZonedDateTime fUltimo;

  @Column(name = "f_asignacion_vocal")
  ZonedDateTime fAsignacionVocal;

  @Column(name = "n_grupo")
  Integer nGrupo;

  @Column(name = "c_fallo")
  Integer cFallo;

  @Column(name = "c_instancia_vocal", length = 3)
  String cInstanciaVocal;

  @Column(name = "l_impedimento", length = 1)
  String impedimento;

  @Column(name = "x_desc_observacion", length = 200)
  String xDescObservacion;

  @Column(name = "f_impedimento")
  ZonedDateTime fImpedimento;

  @Column(name = "c_motivo_ingreso", length = 3)
  String cMotivoIngreso;

  @Column(name = "l_revertido", length = 1)
  String lRevertido;

  @Column(name = "l_reemplazante", length = 1)
  String lReemplazante;

  @Column(name = "l_regularizar", length = 1)
  String lRegularizar;

  @Column(name = "n_colegiado_sec")
  Integer nColegiadoSec;

  @Column(name = "l_nivel_instruccion", length = 1)
  String lNivelInstruccion;

  @Column(name = "c_usuario_old", length = 16)
  String cUsuarioOld;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "f_ingreso", referencedColumnName = "f_ingreso", insertable = false,
          updatable = false)})
  InstanciaExpedienteEntity instanciaExpediente;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_colegiado", referencedColumnName = "n_colegiado", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_item", referencedColumnName = "n_item", insertable = false,
          updatable = false)})
  SalaColegiadoConformacionEntity salaColegiadoConformacion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_colegiado_sec", referencedColumnName = "n_colegiado_sec",
          insertable = false, updatable = false),
      @JoinColumn(name = "n_item", referencedColumnName = "n_item", insertable = false,
          updatable = false)})
  ColegiadoInstanciaDetalleEntity colegiadoInstanciaDetalleEntity;

}
