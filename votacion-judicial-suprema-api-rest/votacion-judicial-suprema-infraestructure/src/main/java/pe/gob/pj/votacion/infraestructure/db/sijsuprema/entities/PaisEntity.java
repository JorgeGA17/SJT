package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "pais", schema = EsquemaConstants.DBO)
public class PaisEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_pais", length = 3, nullable = false)
  String codigoPais;

  @Column(name = "x_pais", length = 50)
  String nombrePais;

  @Column(name = "l_estado", length = 1)
  String estado;

}
