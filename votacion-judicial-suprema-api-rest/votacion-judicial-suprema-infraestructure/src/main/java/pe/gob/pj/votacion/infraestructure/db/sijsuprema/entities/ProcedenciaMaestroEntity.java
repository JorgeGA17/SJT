package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "procedencia_maestro", schema = EsquemaConstants.DBO)
public class ProcedenciaMaestroEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_procedencia", length = 2, nullable = false)
  String codigoProcedencia;

  @Column(name = "x_desc_procedencia", length = 70)
  String descripcionProcedencia;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "l_tipo", length = 1)
  String tipo;

  @Column(name = "l_valida_sala_recurso", length = 1, nullable = false)
  String validaSalaRecurso;

  @Column(name = "l_ind_req_origen_procedencia", length = 1, nullable = false)
  String requiereOrigenProcedencia;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito")
  DistritoJudicialEntity distrito;

}
