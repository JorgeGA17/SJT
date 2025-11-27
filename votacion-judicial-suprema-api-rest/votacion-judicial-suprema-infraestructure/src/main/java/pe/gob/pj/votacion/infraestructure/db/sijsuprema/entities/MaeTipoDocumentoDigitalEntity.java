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
@Table(name = "MaeTipoDocumentoDigital", schema = EsquemaConstants.DBO)
public class MaeTipoDocumentoDigitalEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "l_tipo_doc", length = 3, nullable = false)
  String lTipoDoc;

  @Column(name = "x_tipo_doc", length = 64)
  String xTipoDoc;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "l_tipoCentral", length = 1)
  String lTipoCentral;

}
