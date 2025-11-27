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
@Table(name = "condicion", schema = EsquemaConstants.DBO)
public class CondicionEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_condicion", length = 2, nullable = false)
  String cCondicion;

  @Column(name = "c_sit_juridica", length = 2)
  String cSitJuridica;

  @Column(name = "x_desc_condicion", length = 60)
  String xDescCondicion;

  @Column(name = "l_defecto", length = 1)
  String indicadorDefecto;

  @Column(name = "l_carcel", length = 1)
  String indicadorCarcel;

  @Column(name = "l_activo", length = 1)
  String indicadorActivo;

  @Column(name = "l_visualiza_parte", length = 1)
  String indicadorVisualizaParte;

  @Column(name = "l_detenido", length = 1)
  String indicadorDetenido;
  
}
