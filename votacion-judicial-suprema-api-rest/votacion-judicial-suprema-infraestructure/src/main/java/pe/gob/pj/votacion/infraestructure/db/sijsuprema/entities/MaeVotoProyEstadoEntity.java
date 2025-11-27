package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "MaeVotoProyEstado", schema = EsquemaConstants.DBO)
public class MaeVotoProyEstadoEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "n_id", nullable = false)
  Integer id;

  @Column(name = "x_descripcion", length = 30, nullable = false)
  String descripcion;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "f_registro")
  ZonedDateTime registro;

  @Column(name = "x_icono", length = 20)
  String icono;

}
