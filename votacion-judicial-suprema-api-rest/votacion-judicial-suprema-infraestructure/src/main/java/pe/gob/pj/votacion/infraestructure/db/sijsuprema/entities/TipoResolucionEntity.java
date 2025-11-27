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
@Table(name = "tipo_resolucion", schema = EsquemaConstants.DBO)
public class TipoResolucionEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "l_tipo_resolucion", length = 1)
  String lTipoResolucion;

  @Column(name = "x_tipo_resolucion", length = 60, nullable = false)
  String xTipoResolucion;
  
}
