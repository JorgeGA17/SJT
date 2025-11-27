package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.EstadoAudienciaEntityPk;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "estado_audiencia", schema = EsquemaConstants.DBO)
public class EstadoAudienciaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  EstadoAudienciaEntityPk id;

  @Column(name = "n_orden")
  Integer nOrden;

  @Column(name = "l_activo", length = 1)
  String activo;

  @ManyToOne
  @JoinColumn(name = "c_motivo_ingreso", insertable = false, updatable = false)
  MotivoIngresoMaestroEntity motivoIngresoMaestroEntity;

  @ManyToOne
  @JoinColumn(name = "num_tipo_audiencia", insertable = false, updatable = false)
  TipoProgramaAudienciaEntity tipoProgramaAudiencia;

  @ManyToOne
  @JoinColumn(name = "num_tipo_aud_prox")
  TipoProgramaAudienciaEntity tipoProgramaAudienciaProx;

  @ManyToOne
  @JoinColumn(name = "c_estado")
  EstadoMaestroEntity estadoMaestro;
}
