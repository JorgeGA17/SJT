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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteSentidoEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "expediente_sentido", schema = EsquemaConstants.DBO)
public class ExpedienteSentidoEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  ExpedienteSentidoEntityPk id;

  @Column(name = "f_sentido", nullable = false)
  ZonedDateTime fechaSentido;

  @Column(name = "l_ultimo", length = 1)
  String ultimo;

  @Column(name = "c_fallo")
  Integer fallo;

  @Column(name = "l_calculo_automatico", length = 1)
  String calculoAutomatico;

  @Column(name = "x_desc_motivo", length = 200)
  String descripcionMotivo;

  @Column(name = "c_distrito", length = 3)
  String distrito;

  @Column(name = "c_provincia", length = 4)
  String provincia;

  @Column(name = "c_instancia", length = 3)
  String instancia;

  @Column(name = "f_ingreso")
  ZonedDateTime fechaIngreso;

  @Column(name = "f_ejecutoria")
  ZonedDateTime fechaEjecutoria;
  
  @Column(name = "l_realizado", length = 1)
  String realizado;

  @Column(name = "c_estado", length = 3)
  String codigoEstado;

  @Column(name = "f_estado")
  ZonedDateTime fechaEstado;

  @Column(name = "c_id_registro", length = 14)
  String idRegistro;

  @Column(name = "x_observacion", length = 600)
  String observacion;

  @Column(name = "l_ampliacion", length = 1)
  String ampliacion;

  @Column(name = "f_ingreso_acto")
  ZonedDateTime fechaIngresoActo;

  @Column(name = "c_acto_procesal", length = 3)
  String actoProcesal;

  @Column(name = "l_cronica", length = 1)
  String cronica = "N";

  @Column(name = "f_cronica")
  ZonedDateTime fechaCronica;

  @Column(name = "l_activo", length = 1)
  String activo = "S";

  @Column(name = "c_usuario_relator", length = 15)
  String usuarioRelator;

  @Column(name = "l_impresion", length = 1)
  String impresion;

  @Column(name = "ip_pc_modifica", length = 20)
  String ipPcModifica;

  @Column(name = "fec_modifica")
  ZonedDateTime fechaModifica;

  @Column(name = "usr_modifica", length = 20)
  String usuarioModifica;

  @Column(name = "nom_usr_modif", length = 50)
  String nombreUsuarioModifica;

  @Column(name = "nom_pc_modifica", length = 20)
  String pcModifica;

  @Column(name = "observacion_fallo", length = 512)
  String observacionFallo;

  @Column(name = "l_prorroga", length = 1, nullable = false)
  String prorroga = "N";

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_sentido", referencedColumnName = "c_sentido")
  SentidoFalloEntity sentidoFalloEntity;


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
  @JoinColumns(value = {
      @JoinColumn(name = "c_programacion", referencedColumnName = "c_programacion"),
      @JoinColumn(name = "n_grupo", referencedColumnName = "n_grupo"),
      @JoinColumn(name = "n_secuencia", referencedColumnName = "n_secuencia"),
      @JoinColumn(name = "n_conformacion", referencedColumnName = "n_conformacion")})
  ConformacionGrupoEntity conformacionGrupoEntity;


    @Override
    public String toString() {
        return "ExpedienteSentidoEntity{" +
                "id=" + id +
                '}';
    }
}
