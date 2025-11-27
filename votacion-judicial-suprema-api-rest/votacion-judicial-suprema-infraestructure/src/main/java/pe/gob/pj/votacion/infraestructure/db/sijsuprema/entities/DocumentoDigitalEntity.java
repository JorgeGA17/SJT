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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.DocumentoDigitalEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "documento_digital", schema = EsquemaConstants.DBO)
public class DocumentoDigitalEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  DocumentoDigitalEntityPk id;

  @Column(name = "x_nombre_archivo", length = 100, nullable = false)
  String xNombreArchivo;

  @Column(name = "x_ruta_archivo", length = 150, nullable = false)
  String xRutaArchivo;

  @Column(name = "x_descripcion", length = 150)
  String xDescripcion;

  @Column(name = "n_peso", precision = 18, scale = 0)
  Long nPeso;

  @Column(name = "n_pagina")
  Integer nPagina;

  @Column(name = "n_pagini")
  Integer nPagIni;

  @Column(name = "n_pagfin")
  Integer nPagFin;

  @Column(name = "f_registro")
  ZonedDateTime fRegistro;

  @Column(name = "x_fecha", length = 50)
  String xFecha;

  @Column(name = "x_hora", length = 15)
  String xHora;

  @Column(name = "l_ind_ftp", length = 1)
  String lIndFtp;

  @Column(name = "l_estado", length = 1, nullable = false)
  String lEstado;

  @Column(name = "c_sede_ftp", length = 4)
  String cSedeFtp;

  @Column(name = "n_servicio_ftp")
  Integer nServicioFtp;

  @Column(name = "n_correlativo_ftp")
  Integer nCorrelativoFtp;

  @Column(name = "n_origen")
  Integer nOrigen;

  @Column(name = "l_tipo_doc", length = 3)
  String lTipoDoc;

  @Column(name = "id_oficio", precision = 19, scale = 0)
  BigDecimal idOficio;

  @Column(name = "l_ind_acompanado", length = 1)
  String lIndAcompanado;

  @Column(name = "f_ingreso_acto")
  ZonedDateTime fIngresoActo;

  @Column(name = "c_acto_procesal", length = 3)
  String cActoProcesal;

  @Column(name = "x_desc_acto_procesal", length = 60)
  String xDescActoProcesal;

  @Column(name = "f_ingreso")
  ZonedDateTime fIngreso;

  @Column(name = "f_real_acto")
  ZonedDateTime fRealActo;

  @Column(name = "n_ano")
  Integer nAno;

  @Column(name = "n_sec_notif")
  Integer nSecNotif;

  @Column(name = "x_destinatario", length = 100)
  String xDestinatario;

  @Column(name = "f_movimiento")
  ZonedDateTime fMovimiento;

  @Column(name = "x_sumilla", length = 250)
  String xSumilla;

  @Column(name = "l_ind_proveido", length = 1)
  String lIndProveido;

  @Column(name = "l_ind_doc", length = 1)
  String lIndDoc;

  @Column(name = "sec_dependencia")
  Integer secDependencia;

  @Column(name = "n_sec_ingreso", precision = 10, scale = 0)
  BigDecimal nSecIngreso;

  @Column(name = "x_nombre_archivo_visor", length = 100)
  String xNombreArchivoVisor;

  @Column(name = "f_genera")
  ZonedDateTime fGenera;

  @Column(name = "f_notificacion")
  ZonedDateTime fNotificacion;

  @Column(name = "n_sec_grupo_notif")
  Integer nSecGrupoNotif;

  @Column(name = "cod_tipo_presentacion", length = 3)
  String codTipoPresentacion;

  @Column(name = "l_app_origen", length = 1)
  String lAppOrigen;

  @Column(name = "l_ind_notele_fisica", length = 1)
  String lIndNoteleFisica;

  @Column(name = "n_inc_origen")
  Integer nIncOrigen;

  @Column(name = "n_sec_origen", precision = 10, scale = 0)
  BigDecimal nSecOrigen;

  @Column(name = "l_ind_firma", length = 1)
  String lIndFirma;

  @Column(name = "l_visualiza", length = 1)
  String lVisualiza;

  @Column(name = "x_nombre_archivo_visor_cons", length = 100)
  String xNombreArchivoVisorCons;

  @Column(name = "f_genera_visor_cons")
  ZonedDateTime fGeneraVisorCons;

  @Column(name = "l_ind_envio_web", length = 1)
  String lIndEnvioWeb;

  @Column(name = "n_color", length = 14)
  String nColor;

  @Column(name = "l_ind_envio_adm", length = 1)
  String lIndEnvioAdm;

  @Column(name = "f_actualiza_adm")
  ZonedDateTime fActualizaAdm;

  @Column(name = "uuid_pdf", length = 40)
  String uuidPdf;

  @Column(name = "n_inc_visor")
  Integer nIncVisor;

  @Column(name = "n_codigocms", precision = 10, scale = 0)
  BigDecimal nCodigoCms;

  @Column(name = "c_uuid_pdf", length = 36)
  String cUuidPdf;

  @Column(name = "l_ind_migra", length = 3)
  String lIndMigra;

  @Column(name = "x_motivo_anulacion", length = 180)
  String xMotivoAnulacion;

  @Column(name = "c_usuario_registro", length = 15)
  String cUsuarioRegistro;

  @Column(name = "l_url", length = 1)
  String lUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico"),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente")})
  ExpedienteEntity expediente;

}
