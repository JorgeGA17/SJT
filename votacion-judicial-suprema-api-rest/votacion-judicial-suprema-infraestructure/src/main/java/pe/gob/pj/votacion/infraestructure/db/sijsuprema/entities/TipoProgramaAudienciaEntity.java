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
@Table(name = "tipo_programa_audiencia", schema = EsquemaConstants.DBO)
public class TipoProgramaAudienciaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "num_tipo_audiencia", nullable = false)
  Integer numTipoAudiencia;

  @Column(name = "des_tipo_audiencia", nullable = false, length = 100)
  String desTipoAudiencia;

  @Column(name = "ind_activo", length = 1)
  String indActivo = "S";

  @Column(name = "x_campo_fecha", length = 30)
  String xCampoFecha;

  @Column(name = "x_abrev", length = 10)
  String xAbrev;

  @Column(name = "x_descripcion", length = 150)
  String xDescripcion;

}
