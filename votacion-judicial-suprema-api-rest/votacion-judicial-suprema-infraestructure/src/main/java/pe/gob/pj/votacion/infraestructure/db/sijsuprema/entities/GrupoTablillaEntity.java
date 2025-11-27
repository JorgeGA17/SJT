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
@Table(name = "grupo_tablilla", schema = EsquemaConstants.DBO)
public class GrupoTablillaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "n_grupo", nullable = false)
  Integer nGrupo;

  @Column(name = "x_desc_grupo", length = 15)
  String xDescGrupo;

  @Column(name = "l_activo", length = 1)
  String activo;
  
}
