package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "MovVotoValidarProy", schema = EsquemaConstants.DBO)
public class MovVotoValidarProyEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "n_id")
  Integer id;

  @Column(name = "c_usuarioVal", length = 15, nullable = false)
  String usuarioVal;

  @Column(name = "x_observacion", length = 255)
  String observacion;

  @Column(name = "n_validado")
  Integer numeroValidado;

  @Column(name = "l_activo", length = 1)
  String activo = "S";

  @Column(name = "f_registro")
  ZonedDateTime fechaRegistro;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "n_idProyecto", referencedColumnName = "n_id")
  MovVotoProyectoEntity proyecto;

}
