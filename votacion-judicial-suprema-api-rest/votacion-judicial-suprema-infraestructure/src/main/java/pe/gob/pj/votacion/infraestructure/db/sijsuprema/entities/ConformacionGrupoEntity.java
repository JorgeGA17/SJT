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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ConformacionGrupoEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "conformacion_grupo", schema = EsquemaConstants.DBO)
public class ConformacionGrupoEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  ConformacionGrupoEntityPk id;

  @Column(name = "l_ultimo", length = 1)
  String ultimo;

  @Column(name = "l_informe_oral", length = 1)
  String lInformeOral;

  @Column(name = "c_usuario_vocal", length = 15)
  String codigoUsuarioVocal;

  @Column(name = "l_realizacion", length = 1)
  String realizacion;

  @Column(name = "f_firma_ejecu")
  ZonedDateTime fFirmaEjecu;

  @Column(name = "l_reprogramado", length = 1)
  String lReprogramado;

  @Column(name = "l_ultimo_audiencia", length = 1)
  String lUltimoAudiencia;

  @Column(name = "x_observacion", length = 250)
  String xObservacion;

  @Column(name = "l_audiencia_reservada", length = 1)
  String lAudienciaReservada;

  @Column(name = "l_inf_oral", length = 1)
  String lInfOral;

  @Column(name = "l_publicado", length = 1)
  String publicado;

  @Column(name = "c_id_registro", length = 14)
  String cIdRegistro;

  @Column(name = "f_programacion")
  ZonedDateTime fechaProgramacion;

  @Column(name = "l_adicional", length = 1)
  String adicional;

  @Column(name = "l_no_vista", length = 1, nullable = false)
  String noVista;

  @Column(name = "f_ingreso_acto")
  ZonedDateTime fIngresoActo;

  @Column(name = "c_acto_procesal", length = 3)
  String cActoProcesal;

  @Column(name = "n_cant_acompanado")
  Integer nCantAcompanado;

  @Column(name = "f_registro")
  ZonedDateTime fRegistro;

  @Column(name = "l_sugerido", length = 1)
  String lSugerido;

  @Column(name = "f_publicacion")
  ZonedDateTime fPublicacion;

  @Column(name = "f_real_acto")
  ZonedDateTime fRealActo;

  @Column(name = "l_previo_audiencia", length = 1, nullable = false)
  String lPrevioAudiencia;

  @Column(name = "f_para_cronica")
  ZonedDateTime fParaCronica;

  @Column(name = "l_tipo_apelacion", length = 1)
  String lTipoApelacion;

  @Column(name = "x_desc_dictamen", length = 64)
  String xDescDictamen;

  @Column(name = "l_revisado", length = 1, nullable = false)
  String lRevisado;

  @Column(name = "ano_cargo", length = 4)
  String anoCargo;

  @Column(name = "num_cargo")
  Integer numCargo;

  @Column(name = "n_orden")
  Integer numeroOrden;

  @Column(name = "l_cargo", length = 1, nullable = false)
  String lCargo;

  @Column(name = "l_visualiza_seguimiento", length = 1, nullable = false)
  String visualizaSeguimiento;

  @Column(name = "c_genera_resolucion_word", length = 1, nullable = false)
  String generaResolucionWord;

  @Column(name = "l_discordia_redistribuida", length = 1, nullable = false)
  String discordiaRedistribuida;

  @Column(name = "f_resolucion_editor")
  ZonedDateTime fechaResolucionEditor;

  @Column(name = "c_genera_word", length = 1)
  String cGeneraWord;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_programacion", referencedColumnName = "c_programacion",
          insertable = false, updatable = false),
      @JoinColumn(name = "n_grupo", referencedColumnName = "n_grupo", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_secuencia", referencedColumnName = "n_secuencia", insertable = false,
          updatable = false)})
  GrupoProgramacionEntity grupoProgramacionEntity;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {@JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito"),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia"),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia"),
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico"),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente"),
      @JoinColumn(name = "f_ingreso", referencedColumnName = "f_ingreso")})
  InstanciaExpedienteEntity instanciaExpedienteEntity;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "num_tipo_audiencia", referencedColumnName = "num_tipo_audiencia")
  TipoProgramaAudienciaEntity tipoProgramaAudienciaEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_programacion_blo", referencedColumnName = "c_programacion_blo"),
      @JoinColumn(name = "n_grupo_blo", referencedColumnName = "n_grupo_blo"),
      @JoinColumn(name = "n_secuencia_blo", referencedColumnName = "n_secuencia_blo"),
      @JoinColumn(name = "n_conformacion_blo", referencedColumnName = "n_conformacion_blo")})
  ConformacionBloqueEntity conformacionBloqueEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_area", referencedColumnName = "c_area")
  AreaEntity areaEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "n_dictamen", referencedColumnName = "n_dictamen")
  DictamenEntity dictamenEntity;
}
