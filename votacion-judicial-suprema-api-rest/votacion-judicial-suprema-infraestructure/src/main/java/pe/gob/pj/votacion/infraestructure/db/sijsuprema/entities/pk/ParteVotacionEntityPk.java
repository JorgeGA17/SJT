package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class ParteVotacionEntityPk implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "n_unico", nullable = false, precision = 20, scale = 0)
  BigDecimal unico;

  @Column(name = "n_incidente", nullable = false)
  Integer incidente;

  @Column(name = "n_secuencia_parte", nullable = false)
  Integer secuenciaParte;

}
