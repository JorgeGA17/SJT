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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteVotacionEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "expediente_votacion", schema = EsquemaConstants.DBO)
public class ExpedienteVotacionEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  ExpedienteVotacionEntityPk id;

  @Column(name = "c_usuario", length = 15)
  String usuario;

  @Column(name = "l_ponente", length = 1)
  String ponente;

  @Column(name = "l_dirimente", length = 1)
  String dirimente;

  @Column(name = "x_desc_observacion", length = 200)
  String observacion;

  @Column(name = "l_ultimo", length = 1)
  String ultimo;

  @Column(name = "n_vocal")
  Integer vocal;

  @Column(name = "c_area", length = 2)
  String area;

  @Column(name = "f_votacion")
  ZonedDateTime votacionFecha;

  @Column(name = "l_ind_nivel_instruccion", length = 1)
  String nivelInstruccion;

  @Column(name = "n_dirimente")
  Integer nDirimente;

  @Column(name = "x_apuntes", length = 2500)
  String apuntes;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_fallo")
  FalloEntity fallo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_dirimente", referencedColumnName = "n_dirimente", insertable = false,
          updatable = false)})
  ExpedienteDirimenteEntity expedienteDirimente;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_sentido", referencedColumnName = "n_sentido", insertable = false,
          updatable = false)})
  ExpedienteSentidoEntity expedienteSentido;

}
