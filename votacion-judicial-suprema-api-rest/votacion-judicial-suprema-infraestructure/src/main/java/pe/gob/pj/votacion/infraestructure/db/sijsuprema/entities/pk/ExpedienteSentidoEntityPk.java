package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
@ToString
public class ExpedienteSentidoEntityPk implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "n_unico", nullable = false, precision = 20, scale = 0)
  BigDecimal numeroUnico;

  @Column(name = "n_incidente", nullable = false)
  Integer numeroIncidente;

  @Column(name = "n_sentido", nullable = false)
  Integer numeroSentido;

}
