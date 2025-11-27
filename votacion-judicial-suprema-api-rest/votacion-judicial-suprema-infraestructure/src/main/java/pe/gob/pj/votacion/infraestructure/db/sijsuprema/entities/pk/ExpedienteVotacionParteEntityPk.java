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
public class ExpedienteVotacionParteEntityPk implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "n_unico", precision = 20, scale = 0, nullable = false)
  BigDecimal unico;

  @Column(name = "n_incidente", nullable = false)
  Integer nIncidente;

  @Column(name = "n_sentido", nullable = false)
  Integer nSentido;

  @Column(name = "n_secuencia_parte", nullable = false)
  Integer nSecuenciaParte;

  @Column(name = "n_votacion", nullable = false)
  Integer nVotacion;

}
