package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.TipoParteEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tipo_parte", schema = EsquemaConstants.DBO)
public class TipoParteEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  TipoParteEntityPk id;

  @Column(name = "x_desc_parte", length = 200, nullable = false)
  String xDescParte;

  @Column(name = "l_ingresar", length = 1)
  String lIngresar;

  @Column(name = "l_otro_detalle", length = 1)
  String lOtroDetalle;

  @Column(name = "l_coercitiva", length = 1)
  String lCoercitiva;

  @Column(name = "l_defecto", length = 1)
  String lDefecto;

  @Column(name = "n_orden")
  Integer nOrden;

  @Column(name = "l_sentencia", length = 1)
  String lSentencia;

  @Column(name = "eq_codg_archivo", length = 1)
  String eqCodgArchivo;

  @Column(name = "l_visualiza_ingreso", length = 1)
  String lVisualizaIngreso;

  @Column(name = "l_sujeto_procesal", length = 1)
  String lSujetoProcesal;

  @Column(name = "l_ind_multado", length = 1)
  String lIndMultado;

  @Column(name = "l_prioridad")
  Integer lPrioridad;

  @Column(name = "c_tipo", length = 3)
  String cTipo;

  @Column(name = "l_ind_delito", length = 1)
  String lIndDelito;

  @Column(name = "l_ind_guia", length = 1)
  String lIndGuia;

  @Column(name = "n_orden_old")
  Integer nOrdenOld;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "l_apersonado", length = 1)
  String lApersonado;

  @Column(name = "x_abreviatura", length = 12)
  String xAbreviatura;

  @Column(name = "n_suborden")
  Integer nSuborden;

  @Column(name = "c_jurisprudencia", length = 4)
  String cJurisprudencia;

  @Column(name = "c_superior", length = 4)
  String cSuperior;

  @Column(name = "l_tipo_parte_prov", length = 3)
  String lTipoParteProv;

  @Column(name = "l_nuevo", length = 1)
  String lNuevo;

  @Column(name = "l_flg_adulto_mayor", length = 1)
  String lFlgAdultoMayor;

}
