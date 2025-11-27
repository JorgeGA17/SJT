package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ColegiadoInstanciaEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "colegiado_instancia", schema = EsquemaConstants.DBO)
public class ColegiadoInstanciaEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  ColegiadoInstanciaEntityPk id;

  @Column(name = "f_colegiado")
  ZonedDateTime fechaColegiado;

  @Column(name = "f_registro")
  ZonedDateTime fechaRegistro;

  @Column(name = "x_observacion", length = 255)
  String observacion;

  @Column(name = "l_ultimo", length = 1)
  String ultimo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "n_colegiado", referencedColumnName = "n_colegiado")
  SalaColegiadoMaestroEntity salaColegiadoMaestroEntity;
}
