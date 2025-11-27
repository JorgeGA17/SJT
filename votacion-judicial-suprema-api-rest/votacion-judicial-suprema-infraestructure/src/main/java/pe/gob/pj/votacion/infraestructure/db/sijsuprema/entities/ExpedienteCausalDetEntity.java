package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteCausalDetEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "expediente_causal_det", schema = EsquemaConstants.DBO)
public class ExpedienteCausalDetEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  ExpedienteCausalDetEntityPk id;

  @Column(name = "f_registro", nullable = false)
  LocalDateTime fRegistro = LocalDateTime.now();

  @Column(name = "x_observacion", length = 800)
  String xObservacion;

  @Column(name = "x_articulo", length = 600)
  String xArticulo;

  @Column(name = "l_activo", length = 1, nullable = false)
  String lActivo = "S";

  @Column(name = "l_migrado", length = 1, nullable = false)
  String lMigrado = "N";

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_programacion", referencedColumnName = "c_programacion",
          insertable = false, updatable = false),
      @JoinColumn(name = "n_secuencia", referencedColumnName = "n_secuencia", insertable = false,
          updatable = false)})
  ExpedienteCausalEntity expedienteCausal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_causal_recurso", referencedColumnName = "c_causal_recurso")
  CausalRecursoMaestroEntity causalRecursoMaestro;

}
