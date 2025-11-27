package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class ExpedienteSentidoVotacionEntityPk implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "n_unico", nullable = false, precision = 20, scale = 0)
  BigDecimal numeroUnico;

  @Column(name = "n_incidente", nullable = false)
  Integer numeroIncidente;

  @Column(name = "n_sentido", nullable = false)
  Integer numeroSentido;

  @Column(name = "n_votacion", nullable = false)
  Integer numeroVotacion;

    @Override
    public String toString() {
        return "ExpedienteSentidoVotacionEntityPk{" +
                "numeroUnico=" + numeroUnico +
                ", numeroIncidente=" + numeroIncidente +
                ", numeroSentido=" + numeroSentido +
                ", numeroVotacion=" + numeroVotacion +
                '}';
    }
}
