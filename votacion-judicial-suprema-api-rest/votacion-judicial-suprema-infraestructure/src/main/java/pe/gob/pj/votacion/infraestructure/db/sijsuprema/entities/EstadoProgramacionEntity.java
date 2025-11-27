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
@Table(name = "estado_programacion", schema = EsquemaConstants.DBO)
public class EstadoProgramacionEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_estado_prog", length = 3, nullable = false)
  String cEstadoProg;

  @Column(name = "x_desc_estado", length = 30)
  String xDescEstado;

  @Column(name = "l_activo", length = 1)
  String activo;

}
