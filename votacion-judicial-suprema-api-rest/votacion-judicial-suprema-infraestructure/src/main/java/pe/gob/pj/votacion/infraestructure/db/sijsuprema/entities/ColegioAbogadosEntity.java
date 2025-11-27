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
@Table(name = "colegio_abogados", schema = EsquemaConstants.DBO)
public class ColegioAbogadosEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_colegio", length = 2, nullable = false)
  String codigo;

  @Column(name = "x_desc_colegio", length = 80)
  String descripcion;

  @Column(name = "x_abreviatura", length = 10)
  String abreviatura;

  @Column(name = "l_activo", length = 1)
  String activo;
  
}
