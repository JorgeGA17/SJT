package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ParteVotacionEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "parte_votacion", schema = EsquemaConstants.DBO)
public class ParteVotacionEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  ParteVotacionEntityPk id;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "f_registro")
  ZonedDateTime fRegistro;

  @Column(name = "x_observacion", length = 255)
  String xObservacion;

  @ManyToOne
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_secuencia_parte", referencedColumnName = "n_secuencia",
          insertable = false, updatable = false)})
  ParteEntity parte;

  @OneToMany(mappedBy = "parteVotacionEntity")
  List<ExpedienteVotacionParteEntity> expedienteVotacionPartes;
}
