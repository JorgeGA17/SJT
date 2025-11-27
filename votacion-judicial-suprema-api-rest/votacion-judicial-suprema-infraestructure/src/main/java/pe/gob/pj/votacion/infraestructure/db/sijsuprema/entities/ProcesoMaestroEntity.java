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
@Table(name = "proceso_maestro", schema = EsquemaConstants.DBO)
public class ProcesoMaestroEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_proceso", length = 3, nullable = false)
  String codigoProceso;

  @Column(name = "x_desc_proceso", length = 30, nullable = false)
  String descripcionProceso;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "c_proceso_gen", length = 3)
  String cProcesoGen;

  @Column(name = "l_inst_directa", length = 2)
  String lInstDirecta;

  @Column(name = "c_incidente", length = 3)
  String cIncidente;

  @Column(name = "c_req_pago", length = 2)
  String cReqPago;

  @Column(name = "l_indice", length = 1)
  String lIndice;

  @Column(name = "n_indest")
  Integer nIndest;

}
