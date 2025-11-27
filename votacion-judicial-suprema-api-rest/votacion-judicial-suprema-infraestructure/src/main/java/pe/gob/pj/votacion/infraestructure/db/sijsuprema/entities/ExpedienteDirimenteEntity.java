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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteDirimenteEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "expediente_dirimente", schema = EsquemaConstants.DBO)
public class ExpedienteDirimenteEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  ExpedienteDirimenteEntityPk id;

  @Column(name = "c_usuario_vocal", length = 15)
  String usuarioVocal;

  @Column(name = "c_distrito_vocal", length = 3)
  String distritoVocal;

  @Column(name = "c_provincia_vocal", length = 4)
  String provinciaVocal;

  @Column(name = "c_instancia_vocal", length = 3)
  String instanciaVocal;

  @Column(name = "c_distrito", length = 3)
  String distrito;

  @Column(name = "c_provincia", length = 4)
  String provincia;

  @Column(name = "c_instancia", length = 3)
  String instancia;

  @Column(name = "f_ingreso")
  ZonedDateTime fechaIngreso;

  @Column(name = "x_oficio", length = 30)
  String oficio;

  @Column(name = "l_aceptacion", length = 1)
  String aceptacion;

  @Column(name = "f_oficio")
  ZonedDateTime fechaOficio;

  @Column(name = "f_vista_causa")
  ZonedDateTime fechaVistaCausa;

  @Column(name = "x_observacion", length = 80)
  String observacion;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "f_ingreso_acto")
  ZonedDateTime ingresoActo;

  @Column(name = "c_acto_procesal", length = 3)
  String actoProcesal;

  @Column(name = "n_sentido")
  Integer nSentido;

  @Column(name = "l_revisado", length = 1)
  String revisado;

  @Column(name = "n_orden")
  Integer orden;

  @Column(name = "l_verificado", length = 1)
  String verificado;

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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(
      value = {@JoinColumn(name = "c_programacion", referencedColumnName = "c_programacion"),
          @JoinColumn(name = "n_grupo", referencedColumnName = "n_grupo"),
          @JoinColumn(name = "n_secuencia", referencedColumnName = "n_secuencia"),
          @JoinColumn(name = "n_conformacion", referencedColumnName = "n_conformacion")})
  ConformacionGrupoEntity conformacionGrupoEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_sentido", referencedColumnName = "n_sentido", insertable = false,
          updatable = false)})
  ExpedienteSentidoEntity expedienteSentidoEntity;

}
