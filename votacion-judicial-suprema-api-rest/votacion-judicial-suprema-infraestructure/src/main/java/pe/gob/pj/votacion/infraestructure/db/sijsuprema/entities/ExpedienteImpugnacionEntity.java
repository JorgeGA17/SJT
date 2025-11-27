package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteImpugnacionEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "expediente_impugnacion", schema = EsquemaConstants.DBO)
public class ExpedienteImpugnacionEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  ExpedienteImpugnacionEntityPk id;

  @Column(name = "n_fojas_recurso")
  Integer nFojasRecurso;

  @Column(name = "f_recurso")
  ZonedDateTime fRecurso;

  @Column(name = "n_fojas_resolucion")
  Integer nFojasResolucion;

  @Column(name = "f_resolucion")
  ZonedDateTime fResolucion;

  @Column(name = "f_fallo")
  ZonedDateTime fFallo;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "f_registro")
  ZonedDateTime fRegistro;

  @ManyToOne
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_secuencia", referencedColumnName = "n_secuencia", insertable = false,
          updatable = false)})
  private ParteEntity parteEntity;

  @ManyToOne
  @JoinColumn(name = "c_motivo_ingreso", referencedColumnName = "c_motivo_ingreso")
  private MotivoIngresoMaestroEntity motivoIngresoMaestroEntity;

  @ManyToOne
  @JoinColumn(name = "c_acto_procesal", referencedColumnName = "c_acto_procesal")
  private ActoProcesalEntity actoProcesalEntity;

  @ManyToOne
  @JoinColumn(name = "c_fallo", referencedColumnName = "c_fallo")
  private FalloEntity fallo;
}
