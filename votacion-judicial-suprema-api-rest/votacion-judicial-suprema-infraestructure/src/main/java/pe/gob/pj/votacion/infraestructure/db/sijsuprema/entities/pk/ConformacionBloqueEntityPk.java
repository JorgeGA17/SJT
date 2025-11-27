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
public class ConformacionBloqueEntityPk implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "c_programacion_blo", length = 10, nullable = false)
  String cProgramacionBlo;

  @Column(name = "n_grupo_blo", nullable = false)
  Integer nGrupoBlo;

  @Column(name = "n_secuencia_blo", nullable = false)
  Integer nSecuenciaBlo;

  @Column(name = "n_conformacion_blo", nullable = false)
  Integer nConformacionBlo;
}
