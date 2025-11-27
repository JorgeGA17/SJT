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
@Table(name = "fallo", schema = EsquemaConstants.DBO)
public class FalloEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_fallo")
  Integer codigoFallo;

  @Column(name = "x_desc_fallo", length = 255)
  String descripcion;

  @Column(name = "l_indicador_eleva", length = 1)
  String indicadorEleva;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "c_clase_fallo", length = 1)
  String claseFallo;

  @Column(name = "c_fallo_main")
  Integer falloMain;

  @Column(name = "c_jurisprudencia", length = 4)
  String jurisprudencia;
  
}
