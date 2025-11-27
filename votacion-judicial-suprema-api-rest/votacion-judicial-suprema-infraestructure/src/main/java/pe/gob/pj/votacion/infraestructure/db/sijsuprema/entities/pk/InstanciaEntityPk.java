package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class InstanciaEntityPk implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "c_distrito", length = 3, nullable = false)
  String codigoDistrito;

  @Column(name = "c_provincia", length = 4, nullable = false)
  String codigoProvincia;

  @Column(name = "c_instancia", length = 3, nullable = false)
  String codigoInstancia;
  
}
