package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ServidorFtpEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "servidor_ftp", schema = EsquemaConstants.DBO)
public class ServidorFtpEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  ServidorFtpEntityPk id;

  @Column(name = "c_usuario", length = 15)
  String cUsuario;

  @Column(name = "x_ip", length = 15)
  String xIp;

  @Column(name = "c_clave", length = 18)
  String cClave;

  @Column(name = "l_activo", length = 1)
  String lActivo = "S";

  @Column(name = "c_distrito", length = 3)
  String cDistrito;

  @Column(name = "c_bd", length = 3)
  String cBd;

  @Column(name = "n_carga", nullable = false)
  Boolean nCarga = false;

  @Column(name = "f_actualizaSij")
  ZonedDateTime fActualizaSij;

  @Column(name = "f_actualizaWeb")
  ZonedDateTime fActualizaWeb;

  @Column(name = "x_puerto_ftp", length = 5)
  String xPuertoFtp;

  @Column(name = "x_nom_cola", length = 150)
  String xNomCola;

  @Column(name = "x_ruta_local", length = 100)
  String xRutaLocal;

  @Column(name = "x_url_servidor_visor", length = 100)
  String xUrlServidorVisor;

  @Column(name = "x_puerto_streaming", length = 5)
  String xPuertoStreaming = "3000";
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "n_item", insertable = false, updatable = false)
  ServicioFtpEntity servicioFtp;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_sede", insertable = false, updatable = false)
  SedeEntity sede;
}
