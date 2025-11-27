package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
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
@Table(name = "sala_colegiado_maestro", schema = EsquemaConstants.DBO)
public class SalaColegiadoMaestroEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "n_colegiado")
  Integer nColegiado;

  @Column(name = "x_desc_colegiado", length = 50)
  String xDescColegiado;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @OneToMany(mappedBy = "salaColegiadoMaestroEntity", fetch = FetchType.LAZY)
  List<ColegiadoInstanciaEntity> colegiadoInstancias;
  
}
