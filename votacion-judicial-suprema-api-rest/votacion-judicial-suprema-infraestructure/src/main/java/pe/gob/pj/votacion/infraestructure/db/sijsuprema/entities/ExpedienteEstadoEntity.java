package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteEstadoEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "expediente_estado", schema = EsquemaConstants.DBO)
public class ExpedienteEstadoEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  ExpedienteEstadoEntityPk id;

  @Column(name = "l_ultimo", length = 1)
  String ultimo;

  @Column(name = "l_servidor", length = 1)
  String servidor;

  @Column(name = "l_union", length = 1)
  String lUnion;

  @Column(name = "l_anulado", length = 1)
  String lAnulado;

  @Column(name = "c_id_registro_origen", length = 14)
  String cIdRegistroOrigen;

  @Column(name = "c_usuario", length = 15)
  String cUsuario;

  @Column(name = "l_modinv", length = 1)
  String lModinv;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_estado", insertable = false, updatable = false)
  EstadoMaestroEntity estadoMaestroEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "f_ingreso", referencedColumnName = "f_ingreso", insertable = false,
          updatable = false)})
  InstanciaExpedienteEntity instanciaExpediente;

}
