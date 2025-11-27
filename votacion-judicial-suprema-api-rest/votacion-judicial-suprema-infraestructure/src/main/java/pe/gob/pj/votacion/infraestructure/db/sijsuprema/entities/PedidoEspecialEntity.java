package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.PedidoEspecialEntityPk;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "pedido_especial", schema = EsquemaConstants.DBO)
public class PedidoEspecialEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  PedidoEspecialEntityPk id;

  @Column(name = "f_ingreso_pedido")
  ZonedDateTime fIngresoPedido;

  @Column(name = "x_nro_expediente", length = 25)
  String xNroExpediente;

  @Column(name = "c_tipo_pedido", length = 2)
  String cTipoPedido;

  @Column(name = "x_nom_solicitante", length = 80)
  String xNomSolicitante;

  @Column(name = "x_parte_demandante", length = 80)
  String xParteDemandante;

  @Column(name = "x_parte_demandada", length = 80)
  String xParteDemandada;

  @Column(name = "c_usuario", length = 15)
  String cUsuario;

  @Column(name = "x_observacion", length = 700)
  String xObservacion;

  @Column(name = "n_ventanilla")
  Integer nVentanilla;

  @Column(name = "n_cedulas")
  Integer nCedulas;

  @Column(name = "c_especialidad", length = 2)
  String cEspecialidad;

  @Column(name = "x_desc_cedula", length = 200)
  String xDescCedula;

  @Column(name = "x_tasa_judicial", length = 200)
  String xTasaJudicial;

  @Column(name = "x_desc_depositoj", length = 200)
  String xDescDepositoJ;

  @Column(name = "n_fojas")
  Integer nFojas;

  @Column(name = "n_tasas")
  Integer nTasas;

  @Column(name = "n_depositoj")
  Integer nDepositoJ;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "usuario_anulo", length = 15)
  String usuarioAnulo;

  @Column(name = "f_anulacion")
  ZonedDateTime fAnulacion;

  @Column(name = "usuario_desanulo", length = 15)
  String usuarioDesanulo;

  @Column(name = "f_ult_desanulo")
  ZonedDateTime fUltDesanulo;

  @Column(name = "c_sede", length = 4)
  String cSede;

  @Column(name = "c_ubicacion", length = 2)
  String cUbicacion;

  @Column(name = "c_org_jurisd", length = 2)
  String cOrgJurisd;

  @Column(name = "n_sec_ingreso")
  Integer nSecIngreso;

  @Column(name = "n_ano_ingreso")
  Integer nAnoIngreso;

  @Column(name = "c_procedencia", length = 2)
  String cProcedencia;

  @Column(name = "des_destino", length = 100)
  String desDestino;

  @Column(name = "num_doc_pedido", length = 100)
  String numDocPedido;

  @Column(name = "x_sumilla", length = 700)
  String xSumilla;

}
