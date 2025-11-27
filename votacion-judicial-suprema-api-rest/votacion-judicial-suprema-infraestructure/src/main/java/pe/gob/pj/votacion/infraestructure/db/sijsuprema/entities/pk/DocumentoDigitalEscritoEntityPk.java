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
public class DocumentoDigitalEscritoEntityPk implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "n_sec_ingreso", nullable = false, precision = 10, scale = 0)
  BigDecimal nSecIngreso;

  @Column(name = "n_ano_ingreso", nullable = false)
  Integer nAnoIngreso;

  @Column(name = "n_documento", nullable = false)
  Integer nDocumento;

  @Column(name = "c_sede", length = 4, nullable = false)
  String cSede;

  @Column(name = "c_org_jurisd", length = 2, nullable = false)
  String cOrgJurisd;

  @Column(name = "c_especialidad", length = 2, nullable = false)
  String cEspecialidad;

}
