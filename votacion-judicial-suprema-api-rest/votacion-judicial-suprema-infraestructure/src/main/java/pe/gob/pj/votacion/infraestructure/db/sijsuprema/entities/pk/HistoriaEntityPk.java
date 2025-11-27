package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class HistoriaEntityPk implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "c_distrito", length = 3)
  String cDistrito;

  @Column(name = "c_provincia", length = 4)
  String cProvincia;

  @Column(name = "c_instancia", length = 3)
  String cInstancia;

  @Column(name = "n_unico", precision = 20, scale = 0)
  BigDecimal nUnico;

  @Column(name = "n_incidente")
  Integer nIncidente;

  @Column(name = "f_ingreso")
  LocalDateTime fIngreso;

  @Column(name = "f_ingreso_acto")
  LocalDateTime fIngresoActo;

  @Column(name = "c_acto_procesal", length = 3)
  String cActoProcesal;

}
