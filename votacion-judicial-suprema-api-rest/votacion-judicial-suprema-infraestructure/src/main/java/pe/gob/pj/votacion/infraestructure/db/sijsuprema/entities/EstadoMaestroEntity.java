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
@Table(name = "estado_maestro", schema = EsquemaConstants.DBO)
public class EstadoMaestroEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_estado", length = 3, nullable = false)
  String codigoEstado;

  @Column(name = "x_desc_estado", length = 60, nullable = false)
  String descripcion;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "l_defecto", length = 1)
  String defecto;

  @Column(name = "l_conclusion", length = 1)
  String conclusion;

  @Column(name = "l_incidente", length = 1)
  String incidente;

}
