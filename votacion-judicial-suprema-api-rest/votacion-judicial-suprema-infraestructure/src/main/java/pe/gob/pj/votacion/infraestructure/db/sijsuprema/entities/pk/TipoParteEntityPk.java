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
public class TipoParteEntityPk implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "l_tipo_parte", length = 3, nullable = false)
  String lTipoParte;

  @Column(name = "c_especialidad", length = 2, nullable = false)
  String cEspecialidad;

}
