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
public class ExpedienteCausalEntityPk implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "n_unico", nullable = false)
  Long nUnico;

  @Column(name = "n_incidente", nullable = false)
  Integer nIncidente;

  @Column(name = "c_programacion", length = 10, nullable = false)
  String cProgramacion;

  @Column(name = "n_secuencia", nullable = false)
  Integer nSecuencia;
}
