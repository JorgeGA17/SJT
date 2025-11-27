package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "acto_procesal", schema = EsquemaConstants.DBO)
public class ActoProcesalEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_acto_procesal", length = 3)
  String codigoActoProcesal;

  @Column(name = "c_especialidad", length = 2)
  String codigoEspecialidad;

  @Column(name = "c_org_jurisd", length = 2)
  String cOrgJurisd;

  @Column(name = "x_desc_acto_procesal", length = 60)
  String descripcionActoProcesal;

  @Column(name = "l_notificacion", length = 1)
  String lNotificacion;

  @Column(name = "l_parte", length = 1)
  String lParte;

  @Column(name = "l_pedido_tramite", length = 1)
  String lPedidoTramite;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "l_nivel_acto_procesal", length = 1)
  String lNivelActoProcesal;

  @Column(name = "c_incidente", length = 3)
  String cIncidente;

  @Column(name = "l_defecto", length = 1)
  String lDefecto;

  @Column(name = "l_permiso_descargo", length = 1)
  String lPermisoDescargo;

  @Column(name = "l_sentencia", length = 1)
  String lSentencia;

  @Column(name = "l_redistribucion", length = 1)
  String lRedistribucion;

  @Column(name = "l_des_automatico", length = 3)
  String lDesAutomatico;

  @Column(name = "l_resolutoria", length = 1)
  String lResolutoria;

  @Column(name = "l_findeinstancia", length = 1)
  String lFindEinstancia;

  @Column(name = "l_ind_acumulado", length = 1)
  String lIndAcumulado;

  @Column(name = "l_ind_visual_corte", length = 1)
  String lIndVisualCorte;

  @Column(name = "l_ind_nota", length = 1)
  String lIndNota;

  @Column(name = "l_ind_flow", length = 1)
  String lIndFlow;

  @Column(name = "l_plazo", length = 1)
  String lPlazo;

  @Column(name = "l_ind_programacion", length = 1)
  String lIndProgramacion;

  @Column(name = "l_ind_vista", length = 1)
  String lIndVista;

  @Column(name = "l_oficio", length = 1)
  String lOficio;

  @Column(name = "l_accion", length = 1)
  String lAccion;

  @Column(name = "l_ind_electronico", length = 1)
  String lIndElectronico;

  @Column(name = "c_acto_procesal_gen", length = 3)
  String cActoProcesalGen;

  @Column(name = "l_indica_apertura", length = 1)
  String lIndicaApertura;

  @Column(name = "c_jurisprudencia", length = 4)
  String cJurisprudencia;

  @Column(name = "c_tipo_formato", length = 7)
  String cTipoFormato;

  @Column(name = "l_sentencia_vista", length = 1)
  String lSentenciaVista;

  @Column(name = "c_ind_ncpp", length = 1)
  String cIndNcpp;

  @Column(name = "l_audiencia", length = 1)
  String lAudiencia;

  @Column(name = "l_ind_excarcela", length = 1)
  String lIndExcarcela;

  @Column(name = "l_documento_fiscal", length = 1)
  String lDocumentoFiscal;

  @Column(name = "l_visual_ejecutoria", length = 1)
  String lVisualEjecutoria;

  @Column(name = "id_tipo_resolucion")
  Integer idTipoResolucion;

  @Column(name = "x_conf_editor", length = 3)
  String xConfEditor;

  @Column(name = "l_cant_firmante")
  Integer lCantFirmante;

  @Column(name = "l_cargaCasillero", length = 1)
  String lCargaCasillero;

  @Column(name = "l_sentido", length = 1)
  String lSentido;

  @Column(name = "l_like_hito", length = 60)
  String lLikeHito;

  @Column(name = "l_clasif_est", length = 10)
  String lClasifEst;

  @Column(name = "c_acto_hito", length = 3)
  String cActoHito;

  @Column(name = "l_envio_sunarp", length = 1)
  String lEnvioSunarp;

  @ManyToOne
  @JoinColumn(name = "l_tipo_resolucion", referencedColumnName = "l_tipo_resolucion")
  TipoResolucionEntity tipoResolucion;

}
