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
@Table(name = "incidente_maestro", schema = EsquemaConstants.DBO)
public class IncidenteMaestroEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_incidente", length = 3, nullable = false)
  String codigoIncidente;

  @Column(name = "x_desc_incidente", length = 150, nullable = false)
  String descripcion;

  @Column(name = "l_cod_cautelar", length = 1)
  String codCautelar;

  @Column(name = "c_ind_visualizacion", length = 1)
  String indVisualizacion;

  @Column(name = "l_ind_mc", length = 1)
  String indMc;

  @Column(name = "l_ind_multa", length = 1)
  String indMulta;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "l_ind_beneficio", length = 1)
  String indBeneficio;
  
}
