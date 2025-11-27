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
@Table(name = "tipo_documento_identidad", schema = EsquemaConstants.DBO)
public class TipoDocumentoIdentidadEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_tipo", length = 1, nullable = false)
  String cTipo;

  @Column(name = "x_tipo_doc", length = 70)
  String xTipoDoc;

  @Column(name = "x_abrevi", length = 20)
  String xAbrevi;

  @Column(name = "l_estado", length = 1)
  String lEstado;

  @Column(name = "l_reniec", length = 1)
  String lReniec;

  @Column(name = "n_longitud")
  Integer nLongitud;

  @Column(name = "c_equiv_bn", length = 1)
  String cEquivBn;

  @Column(name = "x_equiv_bn", length = 20)
  String xEquivBn;

  @Column(name = "c_equiv_sunarp", length = 2)
  String cEquivSunarp;

  @Column(name = "x_equiv_sunarp", length = 20)
  String xEquivSunarp;

  @Column(name = "c_tipo_dato", length = 1, nullable = false)
  String cTipoDato;

}
