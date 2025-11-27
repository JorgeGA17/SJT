
package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ProgramacionInstanciaVocalEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "programacion_instancia_vocal", schema = EsquemaConstants.DBO)
public class ProgramacionInstanciaVocalEntity extends AuditoriaSupremaEntity
    implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  ProgramacionInstanciaVocalEntityPk id;

  @Column(name = "c_usuario_vocal", length = 15, nullable = false)
  String codigoUsuarioVocal;

  @Column(name = "l_ind_nivel_instruccion", length = 1)
  String lIndNivelInstruccion;

  @Column(name = "l_activo", length = 1, nullable = false)
  String activo;

  @Column(name = "l_adicional", length = 1, nullable = false)
  String adicional;

  @Column(name = "n_colegiado")
  Integer numeroColegiado;

  @ManyToOne
  @JoinColumn(name = "c_programacion", insertable = false, updatable = false)
  ProgramacionInstanciaEntity programacionInstancia;

}
