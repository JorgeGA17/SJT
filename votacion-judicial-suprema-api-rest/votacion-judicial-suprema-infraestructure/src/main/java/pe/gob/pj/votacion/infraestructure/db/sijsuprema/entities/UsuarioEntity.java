package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.UsuarioEntityPk;

@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "usuario", schema = EsquemaConstants.DBO)
public class UsuarioEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  UsuarioEntityPk id;

  @Column(name = "l_tipo_usuario", length = 1)
  String tipoUsuario;

  @Column(name = "x_nom_usuario", length = 50)
  String nombreUsuario;

  @Column(name = "n_nro_exp")
  Integer numeroExpediente;

  @Column(name = "c_equipo", length = 2)
  String codigoEquipo;

  @Column(name = "l_conectado", length = 1)
  String conectado;

  @Column(name = "c_clave", length = 10)
  String clave;

  @Column(name = "c_foto_check", length = 12)
  String fotoCheck;

  @Column(name = "n_temporizador")
  Integer temporizador;

  @Column(name = "fec_cambio_clave")
  LocalDateTime fechaCambioClave;

  @Column(name = "num_dias_clave")
  Integer diasClave;

  @Column(name = "c_clave_vocal", length = 15)
  String claveVocal;

  @Column(name = "l_tipo_redist", length = 1)
  String tipoRedistribucion;

  @Column(name = "l_activo", length = 1, nullable = false)
  String activo;

  @Column(name = "l_recibe_carga", length = 1)
  String recibeCarga;

  @Column(name = "l_ind_print", length = 1)
  String indicadorImpresion;

  @Column(name = "n_carga_procesal")
  Integer cargaProcesal;

  @Column(name = "n_grupo")
  Integer grupo;

  @Column(name = "l_antiguedad")
  Integer antiguedad;

  @Column(name = "l_ultimo_asig", length = 1)
  String ultimoAsignado;

  @Column(name = "l_externo", length = 1)
  String externo;

  @Column(name = "x_detalle_nombre", length = 60)
  String detalleNombre;

  @Column(name = "x_iniciales", length = 5)
  String iniciales;

  @Column(name = "x_grado", length = 8)
  String grado;

  @Column(name = "clave_usuario_agenda_movil", length = 32)
  String claveAgendaMovil;

  @Column(name = "x_nom_firmante", length = 60)
  String nombreFirmante;

  @Column(name = "x_car_firmante", length = 40)
  String cargoFirmante;

  @Column(name = "c_dni", length = 8)
  String dni;

  @Column(name = "c_ape_paterno", length = 40)
  String apellidoPaterno;

  @Column(name = "c_ape_materno", length = 40)
  String apellidoMaterno;

  @Column(name = "c_nombres", length = 60)
  String nombres;

  @Column(name = "f_nac")
  LocalDate fechaNacimiento;

  @Column(name = "c_flag_valid_reniec", length = 1, nullable = false)
  String flagValidReniec;

  @Column(name = "l_sij_reniec", length = 1, nullable = false)
  String sijReniec;

  @Column(name = "f_reg_reniec")
  LocalDateTime fechaRegistroReniec;

  @Column(name = "l_ofic_reniec", length = 1, nullable = false)
  String oficinaReniec;

  @Column(name = "x_so_usuario", length = 50)
  String soUsuario;

  @Column(name = "l_ver_alerta", length = 1)
  String verAlerta;

  @Column(name = "n_intervalo_alerta")
  Integer intervaloAlerta;

  @Column(name = "l_correo", length = 1)
  String correo;

  @Column(name = "x_desc_correo", length = 40)
  String correoDescripcion;

  @Column(name = "l_novisible", length = 1)
  String noVisible;

  @Column(name = "l_tipo_secretario", length = 1)
  String tipoSecretario;

  @Column(name = "l_ind_tipo_secretario", length = 1)
  String indTipoSecretario;

  @Column(name = "l_logeado_sij", length = 1)
  String logeadoSij;

  @Column(name = "l_sesion_multiple", length = 1)
  String sesionMultiple;

  @Column(name = "x_ip_address_log", length = 30)
  String ipAddressLog;

  @Column(name = "x_mc_address_log", length = 30)
  String mcAddressLog;

  @Column(name = "x_usuario_red_log", length = 30)
  String usuarioRedLog;

  @Column(name = "x_nro_telefono", length = 16)
  String numeroTelefono;

  @Column(name = "l_usuario_grj", length = 1, nullable = false)
  String usuarioGrj;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "c_perfil", referencedColumnName = "codper", nullable = false)
  PerfilEntity perfilEntity;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito", insertable = false,
      updatable = false)
  DistritoJudicialEntity distritoJudicialEntity;

  @OneToMany(mappedBy = "usuarioEntity", fetch = FetchType.LAZY)
  List<UsuarioInstanciaEntity> usuarioInstancias = new ArrayList<>();
  
}
