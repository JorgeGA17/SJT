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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ConformacionBloqueEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "conformacion_bloque", schema = EsquemaConstants.DBO)
public class ConformacionBloqueEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  ConformacionBloqueEntityPk id;

  @Column(name = "l_ultimo", length = 1)
  String ultimo = "S";

  @Column(name = "c_usuario_vocal", length = 15)
  String codigoUsuarioVocal;

  @Column(name = "l_reprogramado", length = 1)
  String reprogramado = "N";

  @Column(name = "l_ultimo_audiencia", length = 1)
  String ultimoAudiencia = "S";

  @Column(name = "x_observacion", length = 250)
  String xObservacion;

  @Column(name = "l_audiencia_reservada", length = 1)
  String lAudienciaReservada = "N";

  @Column(name = "l_publicado", length = 1)
  String lPublicado = "N";

  @Column(name = "c_id_registro", length = 14)
  String cIdRegistro;

  @Column(name = "f_programacion")
  ZonedDateTime fProgramacion;

  @Column(name = "l_adicional", length = 1)
  String lAdicional;

  @Column(name = "l_no_vista", length = 1)
  String lNoVista = "N";

  @Column(name = "n_cant_acompanado")
  Integer nCantAcompanado;

  @Column(name = "f_registro")
  ZonedDateTime fRegistro;

  @Column(name = "l_transferido", length = 1)
  String lTransferido = "N";

  @Column(name = "l_tipo_apelacion", length = 1)
  String lTipoApelacion;

  @Column(name = "x_desc_dictamen", length = 64)
  String xDescDictamen;

  @Column(name = "l_revisado", length = 1, nullable = false)
  String lRevisado = "N";

  @Column(name = "n_orden")
  Integer nOrden;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_programacion_blo", referencedColumnName = "c_programacion",
          insertable = false, updatable = false),
      @JoinColumn(name = "n_grupo_blo", referencedColumnName = "n_grupo", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_secuencia_blo", referencedColumnName = "n_secuencia",
          insertable = false, updatable = false)})
  GrupoProgramacionEntity grupoProgramacionEntity;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {@JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito"),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia"),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia"),
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico"),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente"),
      @JoinColumn(name = "f_ingreso", referencedColumnName = "f_ingreso")})
  InstanciaExpedienteEntity instanciaExpedienteEntity;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "num_tipo_audiencia", referencedColumnName = "num_tipo_audiencia")
  TipoProgramaAudienciaEntity tipoProgramaAudienciaEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "n_dictamen", referencedColumnName = "n_dictamen")
  DictamenEntity dictamen;


}
