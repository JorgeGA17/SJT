package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.GrupoProgramacionEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "grupo_programacion", schema = EsquemaConstants.DBO)
public class GrupoProgramacionEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  GrupoProgramacionEntityPk id;

  @Column(name = "f_programacion", nullable = false)
  LocalDateTime fProgramacion;

  @Column(name = "c_usuario_vocal", length = 15)
  String cUsuarioVocal;

  @Column(name = "l_ultimo", length = 1)
  String lUltimo;

  @Column(name = "x_observacion", length = 250)
  String xObservacion;

  @Column(name = "l_prevencion", length = 1)
  String lPrevencion;

  @Column(name = "l_adicional", length = 1)
  String lAdicional;

  @Column(name = "c_usuario_bloque", length = 15)
  String cUsuarioBloque;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_programacion", referencedColumnName = "c_programacion", insertable = false,
      updatable = false)
  ProgramacionInstanciaEntity programacionInstanciaEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "n_grupo", referencedColumnName = "n_grupo", insertable = false,
      updatable = false)
  GrupoTablillaEntity grupoTablillaEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_area", referencedColumnName = "c_area")
  AreaEntity areaEntity;
  
}
