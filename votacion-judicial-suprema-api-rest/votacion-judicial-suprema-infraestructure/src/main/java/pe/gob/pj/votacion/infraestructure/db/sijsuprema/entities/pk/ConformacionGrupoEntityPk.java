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
public class ConformacionGrupoEntityPk implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "c_programacion", length = 10, nullable = false)
  String codigoProgramacion;

  @Column(name = "n_grupo", nullable = false)
  Integer numeroGrupo;

  @Column(name = "n_secuencia", nullable = false)
  Integer numeroSecuencia;

  @Column(name = "n_conformacion", nullable = false)
  Integer numeroConformacion;
}
