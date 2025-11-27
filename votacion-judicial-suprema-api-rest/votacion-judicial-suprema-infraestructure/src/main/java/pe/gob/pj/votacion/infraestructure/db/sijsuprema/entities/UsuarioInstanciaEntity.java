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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.UsuarioInstanciaEntityPk;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "usuario_instancia", schema = EsquemaConstants.DBO)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsuarioInstanciaEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  UsuarioInstanciaEntityPk id;

  @Column(name = "l_activo", length = 1, nullable = false)
  String activo;

  @Column(name = "l_especialista_asignado", length = 1)
  String especialistaAsignado;

  @Column(name = "l_titular", length = 1)
  String titular;

  @Column(name = "c_usuario_remplazante", length = 15)
  String codigoUsuarioRemplazante;

  @Column(name = "l_ind_vis_reasig", length = 1)
  String indVisReasig;

  @Column(name = "l_recibe_carga", length = 1)
  String recibeCarga;

  @Column(name = "l_visualiza_ponente", length = 1, nullable = false)
  String visualizaPonente;

  @Column(name = "l_cambio_tipo_audiencia", length = 1, nullable = false)
  String cambioTipoAudiencia;

  @Column(name = "l_sumilla_revisor", length = 1, nullable = false)
  String sumillaRevisor;

  @Column(name = "l_cronica_reapetura", length = 1, nullable = false)
  String cronicaReapetura;

  @Column(name = "l_recibe_carga_nlpt", length = 1)
  String recibeCargaNlpt;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumns(value = {
      @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito", insertable = false, updatable = false),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia", insertable = false, updatable = false),
      @JoinColumn(name = "c_sede", referencedColumnName = "c_sede", insertable = false, updatable = false),
      @JoinColumn(name = "c_usuario", referencedColumnName = "c_usuario", insertable = false, updatable = false)
  })
  UsuarioEntity usuarioEntity;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumns(value = {
      @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito", insertable = false, updatable = false),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia", insertable = false, updatable = false),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia", insertable = false, updatable = false)
  })
  InstanciaEntity instanciaEntity;
  
}