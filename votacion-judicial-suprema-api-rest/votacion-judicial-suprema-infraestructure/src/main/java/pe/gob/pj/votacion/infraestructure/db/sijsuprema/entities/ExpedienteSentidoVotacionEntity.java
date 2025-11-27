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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteSentidoVotacionEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "expediente_sentido_votacion", schema = EsquemaConstants.DBO)
public class ExpedienteSentidoVotacionEntity extends AuditoriaSupremaEntity
    implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  ExpedienteSentidoVotacionEntityPk id;

  @Column(name = "l_activo", length = 1, nullable = false)
  String activo = "S";

  @Column(name = "l_ultimo", length = 1, nullable = false)
  String ultimo = "S";

  @Column(name = "c_estado", length = 3)
  String estado;

  @Column(name = "l_cronica", length = 1)
  String cronica = "N";

  @Column(name = "f_cronica")
  ZonedDateTime fechaCronica;

  @Column(name = "c_usuario_relator", length = 15)
  String usuarioRelator;

  @Column(name = "l_impresion", length = 1, nullable = false)
  String impresion = "N";

  @Column(name = "n_dirimente")
  Integer dirimente;

  @Column(name = "f_registro")
  ZonedDateTime fechaRegistro;

  @Column(name = "l_voto_adicional", length = 1, nullable = false)
  String votoAdicional = "N";

  @Column(name = "l_realizado", length = 1)
  String realizado;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_sentido", referencedColumnName = "n_sentido", insertable = false,
          updatable = false)})
  ExpedienteSentidoEntity expedienteSentido;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_dirimente", referencedColumnName = "n_dirimente", insertable = false,
          updatable = false)})
  ExpedienteDirimenteEntity expedienteDirimente;


    @Override
    public String toString() {
        return "ExpedienteSentidoVotacionEntity{" +
                "id=" + id +
                '}';
    }
}
