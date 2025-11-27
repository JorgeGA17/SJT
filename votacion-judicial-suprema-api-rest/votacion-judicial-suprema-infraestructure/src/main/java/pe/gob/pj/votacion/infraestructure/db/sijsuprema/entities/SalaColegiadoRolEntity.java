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
@Table(name = "sala_colegiado_rol", schema = EsquemaConstants.DBO)
public class SalaColegiadoRolEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_rol", length = 2)
  String codigoRol;

  @Column(name = "x_desc_rol", length = 100)
  String descripcion;

  @Column(name = "l_activo", length = 1)
  String activo = "S";
  
}
