package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Embeddable
public class TipoProgramaAudienciaOrganoEntityPk implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "num_tipo_audiencia")
  Integer numeroTipoAudiencia;

  @Column(name = "c_org_jurisd", length = 2)
  String codigoOrganoJurisdiccional;

  @Column(name = "c_especialidad", length = 2)
  String codigoEspecialidad;

}
