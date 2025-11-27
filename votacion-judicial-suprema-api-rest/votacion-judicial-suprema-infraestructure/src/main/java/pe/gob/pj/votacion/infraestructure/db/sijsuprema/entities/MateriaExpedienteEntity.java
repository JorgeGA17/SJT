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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.MateriaExpedienteEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "materia_expediente", schema = EsquemaConstants.DBO)
public class MateriaExpedienteEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  MateriaExpedienteEntityPk id;

  @Column(name = "l_servidor", length = 1)
  String lServidor;

  @Column(name = "f_ingreso")
  ZonedDateTime fIngreso;

  @Column(name = "f_otro")
  ZonedDateTime fechaOtro;

  @Column(name = "l_union", length = 1)
  String lUnion;

  @Column(name = "l_primero", length = 1)
  String lPrimero = "N";

  @Column(name = "l_activo", length = 1)
  String activo = "S";

  @Column(name = "f_registro")
  ZonedDateTime fechaRegistro;

  @Column(name = "c_materia_sub", length = 3)
  String cMateriaSub;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_materia", referencedColumnName = "c_materia")
  MateriaMaestroEntity materiaMaestroEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false)})
  ExpedienteEntity expediente;

}
