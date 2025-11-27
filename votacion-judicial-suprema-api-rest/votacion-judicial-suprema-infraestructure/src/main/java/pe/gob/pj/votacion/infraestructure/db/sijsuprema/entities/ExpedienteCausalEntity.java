package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteCausalEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "expediente_causal", schema = EsquemaConstants.DBO)
public class ExpedienteCausalEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  ExpedienteCausalEntityPk id;

  @Column(name = "f_registro", nullable = false)
  ZonedDateTime fRegistro = ZonedDateTime.now();

  @Column(name = "l_activo", length = 1, nullable = false)
  String activo = "S";

  @Column(name = "l_migrado", length = 1, nullable = false)
  String lMigrado = "N";

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false)})
  ExpedienteEntity expediente;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_programacion", referencedColumnName = "c_programacion", insertable = false,
      updatable = false)
  ProgramacionInstanciaEntity programacionInstancia;

}
