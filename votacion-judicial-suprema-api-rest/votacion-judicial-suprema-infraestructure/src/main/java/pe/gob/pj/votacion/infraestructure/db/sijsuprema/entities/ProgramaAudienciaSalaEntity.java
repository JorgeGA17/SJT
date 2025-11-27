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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ProgramaAudienciaSalaEntityPk;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "programa_audiencia_sala", schema = EsquemaConstants.DBO)
public class ProgramaAudienciaSalaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  ProgramaAudienciaSalaEntityPk id;

  @Column(name = "c_acto_procesal", length = 3)
  String cActoProcesal;

  @Column(name = "f_programa_audiencia")
  ZonedDateTime fProgramaAudiencia;

  @Column(name = "f_registro")
  ZonedDateTime fRegistro;

  @Column(name = "c_usuario", length = 15)
  String cUsuario;

  @Column(name = "ind_activo", length = 1)
  String indActivo;

  @Column(name = "l_ultimo", length = 1)
  String lUltimo;

  @Column(name = "l_anulado", length = 1)
  String lAnulado;

  @Column(name = "f_plazo")
  ZonedDateTime fPlazo;

  @Column(name = "l_tipo_plazo", length = 1)
  String lTipoPlazo;

  @Column(name = "c_id_registro_origen", length = 14)
  String cIdRegistroOrigen;

  @Column(name = "l_ultimo_audiencia", length = 1)
  String lUltimoAudiencia;

  @Column(name = "l_inf_oral", length = 1)
  String lInfOral;

  @Column(name = "l_audiencia_reservada", length = 1)
  String lAudienciaReservada;

  @Column(name = "l_no_vista", length = 1)
  String lNoVista;

  @Column(name = "x_obs_novista", length = 100)
  String xObsNoVista;

  @Column(name = "l_inf_tv", length = 1)
  String lInfTv;

  @Column(name = "n_sentido")
  Integer nSentido;

  @Column(name = "num_tipo_audiencia_alt")
  Integer numTipoAudienciaAlt;

  @Column(name = "l_actualizado", length = 4)
  String lActualizado;

  @Column(name = "f_ingreso")
  ZonedDateTime fechaIngreso;

  @Column(name = "f_ingreso_acto")
  ZonedDateTime fechaIngresoActo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_motivo_ingreso", referencedColumnName = "c_motivo_ingreso")
  MotivoIngresoMaestroEntity motivoIngresoMaestroEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "num_tipo_audiencia", referencedColumnName = "num_tipo_audiencia")
  TipoProgramaAudienciaEntity tipoProgramaAudiencia;

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
          updatable = false),
      @JoinColumn(name = "f_ingreso_acto", referencedColumnName = "f_ingreso_acto",
          insertable = false, updatable = false),
      @JoinColumn(name = "c_acto_procesal", referencedColumnName = "c_acto_procesal",
          insertable = false, updatable = false)})
  HistoriaEntity historiaEntity;
}
