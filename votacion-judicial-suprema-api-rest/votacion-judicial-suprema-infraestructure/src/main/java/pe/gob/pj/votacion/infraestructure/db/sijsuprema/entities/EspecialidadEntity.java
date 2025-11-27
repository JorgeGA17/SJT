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
@Table(name = "especialidad", schema = EsquemaConstants.DBO)
public class EspecialidadEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_especialidad", length = 2, nullable = false)
  String codigoEspecialidad;

  @Column(name = "x_desc_especialidad", length = 30)
  String descripcion;

  @Column(name = "c_cod_especialidad", length = 2)
  String codigoAdicional;

  @Column(name = "eq_codg_central", length = 3)
  String codigoCentral;

  @Column(name = "c_jurisprudencia", length = 4)
  String jurisprudencia;

  @Column(name = "c_especialida_main", length = 2)
  String especialidadPrincipal;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "l_visualiza", length = 1)
  String visualiza;

}
