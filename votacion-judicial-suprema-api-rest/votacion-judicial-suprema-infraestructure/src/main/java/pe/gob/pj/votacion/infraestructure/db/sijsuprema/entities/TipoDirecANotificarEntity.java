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
@Table(name = "tipo_direc_a_notificar", schema = EsquemaConstants.DBO)
public class TipoDirecANotificarEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id_tipo_direccion", length = 2, nullable = false)
  String idTipoDireccion;

  @Column(name = "x_desc_direccion", length = 50)
  String xDescDireccion;

  @Column(name = "l_estado", length = 1)
  String lEstado;

}
