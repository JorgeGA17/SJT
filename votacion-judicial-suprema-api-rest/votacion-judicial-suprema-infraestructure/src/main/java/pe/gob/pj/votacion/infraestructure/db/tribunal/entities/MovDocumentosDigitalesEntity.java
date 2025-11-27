package pe.gob.pj.votacion.infraestructure.db.tribunal.entities;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.domain.common.enums.Estado;
import pe.gob.pj.votacion.infraestructure.common.enums.OperacionBaseDatos;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.common.utils.InformacionRedUtils;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "mov_documentos_digitales", schema = EsquemaConstants.ESQUEMA_JURIS)
public class MovDocumentosDigitalesEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "n_documento_digital", nullable = false)
  Integer nDocumentoDigital;

  @Column(name = "x_expediente", length = 15, nullable = false)
  String xExpediente;

  @Column(name = "x_demandante", length = 400)
  String xDemandante;

  @Column(name = "x_demandado", length = 400)
  String xDemandado;

  @Column(name = "x_materia", length = 100)
  String xMateria;

  @Column(name = "x_sub_materia", length = 150)
  String xSubMateria;

  @Column(name = "x_especifica", length = 150)
  String xEspecifica;

  @Column(name = "x_fallo", length = 150)
  String xFallo;

  @Column(name = "x_ruta_archivo", length = 300, nullable = false)
  String xRutaArchivo;

  @Column(name = "x_uuid_alfresco", length = 50)
  String xUuidAlfresco;

  // bi-directional many-to-one association to MaeTipoAplicativo
  @ManyToOne
  @JoinColumn(name = "n_entidad")
  MaeEntidadEntity maeEntidad;

  @Column(name = "x_estado", length = 1)
  String xEstado;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "f_procesado")
  Date fechaProcesado = new Date();

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "F_REGISTRO", nullable = false)
  Date fechaRegistro = new Date();

  @Column(name = "x_error", length = 2500)
  String xError;

  // Auditoria
  @Column(name = "F_AUD")
  LocalDateTime fAud = LocalDateTime.now();
  @Column(name = "B_AUD")
  String bAud = OperacionBaseDatos.INSERTAR.getNombre();
  @Column(name = "C_AUD_UID")
  String cAudId;
  @Column(name = "C_AUD_UIDRED")
  String cAudIdRed = InformacionRedUtils.getNombreRed();
  @Column(name = "C_AUD_PC")
  String cAudPc = InformacionRedUtils.getPc();
  @Column(name = "C_AUD_IP")
  String cAudIp = InformacionRedUtils.getIp();
  @Column(name = "C_AUD_MCADDR")
  String cAudMcAddr = InformacionRedUtils.getMac();
  @Column(name = "L_ACTIVO", length = 1, nullable = false)
  String activo = Estado.ACTIVO_NUMERICO.getNombre();

}
