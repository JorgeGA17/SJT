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
@Table(name = "causal_recurso_maestro", schema = EsquemaConstants.DBO)
public class CausalRecursoMaestroEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_causal_recurso", length = 3)
  String codigoCausalRecurso;

  @Column(name = "x_descripcion", length = 250)
  String xDescripcion;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "x_desc_articulo", length = 20)
  String xDescArticulo;

  @Column(name = "l_derogado", length = 1)
  String lDerogado;

}
