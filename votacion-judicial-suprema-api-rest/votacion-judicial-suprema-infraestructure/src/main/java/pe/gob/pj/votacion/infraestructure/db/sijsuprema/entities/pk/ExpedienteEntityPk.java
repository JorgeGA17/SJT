package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class ExpedienteEntityPk implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "n_unico", precision = 20, scale = 0, nullable = false)
  BigDecimal nUnico;

  @Column(name = "n_incidente", nullable = false)
  Integer nIncidente;

}
