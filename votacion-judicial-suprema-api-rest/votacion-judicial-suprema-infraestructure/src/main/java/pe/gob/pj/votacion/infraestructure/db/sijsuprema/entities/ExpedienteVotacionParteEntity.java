package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteVotacionParteEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "expediente_votacion_parte", schema = EsquemaConstants.DBO)
public class ExpedienteVotacionParteEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  ExpedienteVotacionParteEntityPk id;

  @Column(name = "c_fallo")
  Integer cFallo;

  @Column(name = "x_observacion", length = 255)
  String xObservacion;

  @Column(name = "f_votacion")
  ZonedDateTime fVotacion;

  @Column(name = "f_registro")
  ZonedDateTime fRegistro;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "l_ultimo", length = 1)
  String lUltimo;

  @Column(name = "l_discordia", length = 1)
  String lDiscordia;

  @Column(name = "l_parte_nueva", length = 1)
  String lParteNueva;

  @Column(name = "l_publicado", length = 1)
  String lPublicado;

  @Column(name = "x_observaciones", length = 1200)
  String xObservaciones;

  @Column(name = "c_sub_fallo")
  Integer cSubFallo;

  @Column(name = "n_votacion_new")
  Integer nVotacionNew;

  @Column(name = "n_votacion_old")
  Integer nVotacionOld;

  @Column(name = "l_vot_dia", length = 1)
  String lVotDia;

  @Column(name = "x_anotacion", length = 2500)
  String xAnotacion;

  @ManyToOne
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_secuencia_parte", referencedColumnName = "n_secuencia_parte",
          insertable = false, updatable = false)})
  ParteVotacionEntity parteVotacionEntity;

  @ManyToOne
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_sentido", referencedColumnName = "n_sentido", insertable = false,
          updatable = false)})
  ExpedienteSentidoEntity expedienteSentidoEntity;

  @ManyToOne
  @JoinColumn(name = "c_sentido", referencedColumnName = "c_sentido")
  SentidoFalloEntity sentidoFallo;

  @ManyToOne
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_sentido", referencedColumnName = "n_sentido", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_votacion", referencedColumnName = "n_votacion", insertable = false,
          updatable = false)})
  ExpedienteSentidoVotacionEntity expedienteSentidoVotacionEntity;

}
