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
@Table(name = "motivo_ingreso_maestro", schema = EsquemaConstants.DBO)
public class MotivoIngresoMaestroEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_motivo_ingreso", length = 3, nullable = false)
  String codigoMotivoIngreso;

  @Column(name = "x_desc_motivo_ingreso", length = 60, nullable = false)
  String descripcionMotivoIngreso;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "l_ind_visual_corte", length = 1)
  String lIndVisualCorte;

  @Column(name = "l_ind_exhorto", length = 1)
  String lIndExhorto;

  @Column(name = "c_motivo_pre", length = 3)
  String cMotivoPre;

  @Column(name = "c_tipo_formato", length = 7)
  String cTipoFormato;

  @Column(name = "c_incidente", length = 3)
  String cIncidente;

  @Column(name = "c_motivo_pre_ncpp", length = 3)
  String cMotivoPreNcpp;

  @Column(name = "c_incidente_ncpp", length = 3)
  String cIncidenteNcpp;

  @Column(name = "l_ind_apela", length = 1)
  String lIndApela;

  @Column(name = "l_ind_eleva", length = 1, nullable = false)
  String lIndEleva;

  @Column(name = "l_ind_formato_oficio", length = 1, nullable = false)
  String lIndFormatoOficio;

  @Column(name = "l_impedimento", length = 1)
  String lImpedimento;

  @Column(name = "x_abreviatura", length = 5)
  String xAbreviatura;

  @Column(name = "l_requiere_causal", length = 1, nullable = false)
  String lRequiereCausal;

  @Column(name = "num_peso")
  Integer numPeso;

  @Column(name = "n_nivel_instancia")
  Integer nNivelInstancia;

  @Column(name = "l_indice", length = 1)
  String lIndice;

  @Column(name = "n_indest")
  Integer nIndest;

}
