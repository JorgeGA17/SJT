package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "organo_jurisdiccional", schema = EsquemaConstants.DBO)
public class OrganoJurisdiccionalEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_org_jurisd", length = 2, nullable = false)
  String codigoOrganoJurisdiccional;

  @Column(name = "x_nom_org_jurisd", length = 30)
  String nombreOrganoJurisdiccional;

  @Column(name = "n_prioridad")
  Integer numeroPrioridad;

  @Column(name = "x_nom_org_jurisd_corto", length = 2)
  String nombreOrganoJurisdiccionalCorto;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "eq_codg_central", length = 2)
  String eqCodgCentral;

  @Column(name = "c_jurisprudencia", length = 4)
  String cJurisprudencia;

  @Column(name = "COD_TIPO_DEP_SIS")
  Integer codTipoDepSis;

  @Column(name = "IDOFITIPDEPCOD")
  Integer idOfiTipDepCod;

  @Column(name = "n_nivel")
  Integer nNivel;

  @Column(name = "costo_edicto", precision = 13, scale = 2)
  BigDecimal costoEdicto;

  @Column(name = "c_superior", length = 4)
  String cSuperior;

}
