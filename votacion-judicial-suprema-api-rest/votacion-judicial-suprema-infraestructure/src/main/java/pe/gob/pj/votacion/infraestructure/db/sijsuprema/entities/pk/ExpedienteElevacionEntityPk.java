package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
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
public class ExpedienteElevacionEntityPk implements Serializable {

  /**
   * 
   */
  static final long serialVersionUID = 1L;

  @Column(name = "c_distrito", length = 3, nullable = false)
  String codigoDistrito;

  @Column(name = "c_provincia", length = 4, nullable = false)
  String codigoProvincia;

  @Column(name = "c_instancia", length = 3, nullable = false)
  String codigoInstancia;

  @Column(name = "n_unico", precision = 20, scale = 0, nullable = false)
  BigDecimal numeroUnico;

  @Column(name = "n_incidente", nullable = false)
  Integer numeroIncidente;

  @Column(name = "f_ingreso", nullable = false)
  ZonedDateTime fechaIngreso;
}
