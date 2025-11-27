package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.CfgTipoAudienciaFalloEntityPk;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "cfg_tipo_audiencia_fallo", schema = EsquemaConstants.DBO)
public class CfgTipoAudienciaFalloEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  CfgTipoAudienciaFalloEntityPk id;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "l_regresa", length = 1)
  String lRegresa;

  @Column(name = "c_jurisprudencia", length = 4)
  String cJurisprudencia;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "num_tipo_audiencia", referencedColumnName = "num_tipo_audiencia", insertable = false, updatable = false)
  TipoProgramaAudienciaEntity tipoProgramaAudienciaEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_motivo_ingreso", referencedColumnName = "c_motivo_ingreso", insertable = false, updatable = false)
  MotivoIngresoMaestroEntity motivoIngresoMaestroEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_fallo", referencedColumnName = "c_fallo", insertable = false, updatable = false)
  FalloEntity falloEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_estado", referencedColumnName = "c_estado", insertable = false, updatable = false)
  EstadoMaestroEntity estadoMaestroEntity;
}
