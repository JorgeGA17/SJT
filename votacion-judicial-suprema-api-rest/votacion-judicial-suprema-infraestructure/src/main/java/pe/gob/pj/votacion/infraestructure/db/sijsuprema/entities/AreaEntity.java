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
@Table(name = "area", schema = EsquemaConstants.DBO)
public class AreaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_area", length = 2, nullable = false)
  String codigoArea;

  @Column(name = "x_desc_area", length = 100, nullable = false)
  String descripcion;

  @Column(name = "l_activo", length = 1, nullable = false)
  String activo;

  @Column(name = "l_mpartes", length = 1, nullable = false)
  String mpartes = "N";
  
}
