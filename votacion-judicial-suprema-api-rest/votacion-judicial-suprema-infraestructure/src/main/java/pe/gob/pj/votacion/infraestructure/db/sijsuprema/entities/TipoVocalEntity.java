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
@Table(name = "tipo_vocal", schema = EsquemaConstants.DBO)
public class TipoVocalEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_tipo_vocal")
  Integer id;

  @Column(name = "x_desc_tipo_vocal", length = 255, nullable = false)
  String descripcion;

  @Column(name = "l_activo", length = 1)
  String activo;
}
