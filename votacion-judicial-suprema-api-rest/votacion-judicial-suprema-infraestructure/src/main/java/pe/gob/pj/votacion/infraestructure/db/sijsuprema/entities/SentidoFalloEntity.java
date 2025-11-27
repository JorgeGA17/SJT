package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "sentido_fallo", schema = EsquemaConstants.DBO)
public class SentidoFalloEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_sentido", length = 2, nullable = false)
  String codigoSentido;

  @Column(name = "x_desc_sentido", length = 30)
  String descripcionSentido;

  @Column(name = "l_activo", length = 1)
  String activo;
  
}
