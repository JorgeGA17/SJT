package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "MovVotoJurisp", schema = EsquemaConstants.DBO)
public class MovVotoJurispEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "n_id")
  Integer id;

  @Column(name = "n_unico", precision = 20, scale = 0, nullable = false)
  BigDecimal nUnico;

  @Column(name = "n_incidente", nullable = false)
  Integer nIncidente;

  @Column(name = "c_programacion", length = 10)
  String cProgramacion;

  @Column(name = "x_entidad", length = 2, nullable = false)
  String xEntidad;

  @Column(name = "x_recurso", length = 82, nullable = false)
  String xRecurso;

  @Column(name = "x_uuid", length = 36, nullable = false)
  String xUuid;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "f_registro")
  ZonedDateTime fRegistro;

}
