package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "materia_maestro", schema = EsquemaConstants.DBO)
public class MateriaMaestroEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_materia", length = 3)
  String cMateria;

  @Column(name = "x_desc_materia", length = 150, nullable = false)
  String xDescMateria;

  @Column(name = "l_activo", length = 1)
  String lActivo = "S";

  @Column(name = "l_viaje_menor", length = 1)
  String lViajeMenor = "N";

  @Column(name = "eq_codg_central", length = 6)
  String eqCodgCentral;

  @Column(name = "l_menor", length = 1)
  String lMenor = "N";

  @Column(name = "n_orden_tablilla")
  Integer nOrdenTablilla;

  @Column(name = "f_creacion")
  ZonedDateTime fCreacion;

  @OneToMany(mappedBy = "materiaMaestroEntity", fetch = FetchType.LAZY)
  List<MateriaExpedienteEntity> materiaExpedientes;

}
