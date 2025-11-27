package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Embeddable
public class CfgTipoAudienciaFalloEntityPk implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "c_motivo_ingreso", length = 3)
  String cMotivoIngreso;

  @Column(name = "num_tipo_audiencia")
  Integer nTipoAudiencia;

  @Column(name = "c_fallo")
  Integer cInstancia;

  @Column(name = "c_estado", length = 3)
  String cEstado;

  @Column(name = "c_org_jurisd", length = 3)
  String cOrgJurisd;

  @Column(name = "c_especialidad", length = 3)
  String cEspecialidad;

}
