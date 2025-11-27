package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.HistoriaEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "historia", schema = EsquemaConstants.DBO)
public class HistoriaEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  HistoriaEntityPk id;

  @Column(name = "c_usuario", length = 15)
  String cUsuario;

  @Column(name = "l_tipo_parte", length = 3)
  String lTipoParte;

  @Column(name = "f_emision")
  ZonedDateTime fEmision;

  @Column(name = "f_remision")
  ZonedDateTime fRemision;

  @Column(name = "f_recepcion")
  ZonedDateTime fRecepcion;

  @Column(name = "f_respuesta")
  ZonedDateTime fRespuesta;

  @Column(name = "f_diligencia")
  ZonedDateTime fDiligencia;

  @Column(name = "x_sumilla", length = 2000)
  String xSumilla;

  @Column(name = "x_resolucion", length = 120)
  String xResolucion;

  @Column(name = "x_destinatario", length = 100)
  String xDestinatario;

  @Column(name = "n_fojas")
  Integer nFojas;

  @Column(name = "n_fojas_orig")
  Integer nFojasOrig;

  @Column(name = "n_fojas_copia")
  Integer nFojasCopia;

  @Column(name = "x_observacion", length = 700)
  String xObservacion;

  @Column(name = "l_visualizacion", length = 1)
  String lVisualizacion;

  @Column(name = "l_ultimo", length = 1)
  String lUltimo;

  @Column(name = "n_ventanilla")
  Integer nVentanilla;

  @Column(name = "n_sec_ingreso")
  Integer nSecIngreso;

  @Column(name = "n_ano_ingreso")
  Integer nAnoIngreso;

  @Column(name = "f_devuelto")
  ZonedDateTime fDevuelto;

  @Column(name = "l_servidor", length = 1)
  String lServidor;

  @Column(name = "x_receptor_consignacion", length = 30)
  String xReceptorConsignacion;

  @Column(name = "l_consignacion", length = 1)
  String lConsignacion;

  @Column(name = "f_entrega_consignacion")
  ZonedDateTime fEntregaConsignacion;

  @Column(name = "f_plazo_aprox")
  ZonedDateTime fPlazoAprox;

  @Column(name = "l_con_notificacion", length = 1)
  String lConNotificacion;

  @Column(name = "c_usuario_actual", length = 15)
  String cUsuarioActual;

  @Column(name = "l_compaginado", length = 1)
  String lCompaginado;

  @Column(name = "c_usuario_compaginado", length = 15)
  String cUsuarioCompaginado;

  @Column(name = "f_compaginado")
  ZonedDateTime fCompaginado;

  @Column(name = "l_envioftp", length = 1)
  String lEnvioftp;

  @Column(name = "x_desc_consignaciones", length = 700)
  String xDescConsignaciones;

  @Column(name = "x_descargo_res", length = 700)
  String xDescargoRes;

  @Column(name = "c_tipo_entrega", length = 3)
  String cTipoEntrega;

  @Column(name = "l_estado", length = 1)
  String lEstado;

  @Column(name = "c_sec_consignacion", length = 10)
  String cSecConsignacion;

  @Column(name = "c_ubicacion", length = 2)
  String cUbicacion;

  @Column(name = "f_resolucion_editor")
  ZonedDateTime fResolucionEditor;

  @Column(name = "n_dias_trans")
  Integer nDiasTrans;

  @Column(name = "f_real_acto")
  ZonedDateTime fRealActo;

  @Column(name = "l_plazo", length = 1)
  String lPlazo;

  @Column(name = "c_sede", length = 4)
  String cSede;

  @Column(name = "n_resolucion")
  Integer nResolucion;

  @Column(name = "num_his", precision = 10, scale = 0)
  BigDecimal numHis;

  @Column(name = "ano_his", precision = 4, scale = 0)
  BigDecimal anoHis;

  @Column(name = "n_ano_grupo_notif")
  Integer nAnoGrupoNotif;

  @Column(name = "n_sec_grupo_notif")
  Integer nSecGrupoNotif;

  @Column(name = "l_union", length = 1)
  String lUnion;

  @Column(name = "c_juez", length = 15)
  String cJuez;

  @Column(name = "c_especialista", length = 15)
  String cEspecialista;

  @Column(name = "c_dni_especialista", length = 8)
  String cDniEspecialista;

  @Column(name = "c_dni_juez", length = 8)
  String cDniJuez;

  @Column(name = "c_estado_audiencia", length = 2)
  String cEstadoAudiencia;

  @Column(name = "l_tramite", length = 1)
  String lTramite;

  @Column(name = "c_infraccion", length = 3)
  String cInfraccion;

  @Column(name = "c_infracDebProc", length = 3)
  String cInfracDebProc;

  @Column(name = "c_infracNormaSust", length = 3)
  String cInfracNormaSust;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_programacion", referencedColumnName = "c_programacion"),
      @JoinColumn(name = "n_grupo", referencedColumnName = "n_grupo"),
      @JoinColumn(name = "n_secuencia", referencedColumnName = "n_secuencia"),
      @JoinColumn(name = "n_conformacion", referencedColumnName = "n_conformacion")})
  ConformacionGrupoEntity conformacionGrupoEntity;

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
  InstanciaExpedienteEntity instanciaExpedienteEntity;
}
