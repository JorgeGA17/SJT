package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
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
@Table(name = "dispositivo_legal", schema = EsquemaConstants.DBO)
public class DispositivoLegalEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_dispositivo_legal", length = 3, nullable = false)
  String codigoDispositivoLegal;

  @Column(name = "x_desc_dispositivo", length = 255)
  String descripcionDispositivo;

  @Column(name = "l_activo", length = 1)
  String activo;

}
