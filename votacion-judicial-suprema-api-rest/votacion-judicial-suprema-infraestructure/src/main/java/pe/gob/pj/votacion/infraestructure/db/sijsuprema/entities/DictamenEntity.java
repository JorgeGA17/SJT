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
@Table(name = "dictamen", schema = EsquemaConstants.DBO)
public class DictamenEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "n_dictamen", nullable = false)
  Integer nDictamen;

  @Column(name = "x_dictamen", length = 64)
  String xDictamen;

  @Column(name = "l_req_sentido_fallo", length = 1, nullable = false)
  String lReqSentidoFallo = "N";

  @Column(name = "l_activo", length = 1, nullable = false)
  String activo = "S";
  
}
