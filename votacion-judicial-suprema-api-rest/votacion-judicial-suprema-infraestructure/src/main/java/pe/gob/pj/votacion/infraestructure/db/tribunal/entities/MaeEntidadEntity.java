package pe.gob.pj.votacion.infraestructure.db.tribunal.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "mae_entidad", schema = EsquemaConstants.ESQUEMA_JURIS)
public class MaeEntidadEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "n_entidad", nullable = false)
  Integer nEntidad;

  @Column(name = "x_entidad", length = 250)
  String xEntidad;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "F_REGISTRO", nullable = false)
  Date fechaRegistro = new Date();

  // bi-directional many-to-one association to MaeAplicativo
  @OneToMany(mappedBy = "maeEntidad")
  List<MovDocumentosDigitalesEntity> movDocumentosDigitales;

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
