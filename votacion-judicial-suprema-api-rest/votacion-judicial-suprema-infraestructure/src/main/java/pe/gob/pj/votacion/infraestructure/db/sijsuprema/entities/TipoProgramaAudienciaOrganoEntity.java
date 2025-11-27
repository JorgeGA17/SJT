package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.TipoProgramaAudienciaOrganoEntityPk;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tipo_programa_audiencia_organo", schema = EsquemaConstants.DBO)
public class TipoProgramaAudienciaOrganoEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  TipoProgramaAudienciaOrganoEntityPk id;

  @Column(name = "n_plazo_dias")
  Integer nPlazoDias;

  @Column(name = "l_tipo_dias", length = 1)
  String lTipoDias;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "x_abrev", length = 10)
  String abreviatura;

  @Column(name = "x_descripcion", length = 150)
  String descripcion;
  
  @ManyToOne
  @JoinColumn(name = "c_org_jurisd", referencedColumnName = "c_org_jurisd", insertable = false, updatable = false)
  OrganoJurisdiccionalEntity organoJurisdiccionalEntity;
  
  @ManyToOne
  @JoinColumn(name = "c_especialidad", referencedColumnName = "c_especialidad", insertable = false, updatable = false)
  EspecialidadEntity especialidadEntity;

  @ManyToOne
  @JoinColumn(name = "num_tipo_audiencia", insertable = false, updatable = false)
  TipoProgramaAudienciaEntity tipoProgramaAudiencia;

}
