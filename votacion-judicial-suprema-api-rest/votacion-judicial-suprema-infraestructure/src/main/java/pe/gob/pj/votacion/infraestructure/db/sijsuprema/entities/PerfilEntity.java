package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "perfil", schema = EsquemaConstants.DBO)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PerfilEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "codper", length = 2, nullable = false)
  String codigoPerfil;

  @Column(name = "desper", length = 40, nullable = false)
  String descripcion;

  @Column(name = "perfil_sup", length = 2)
  String perfilSuperior;

  @Column(name = "c_ubicacion_procedencia", length = 40)
  String ubicacionProcedencia;

  @Column(name = "l_administra", length = 1, nullable = false)
  String administra;

  @Column(name = "l_visualiza", length = 1, nullable = false)
  String visualiza;

  @Column(name = "l_nivel_acto_procesal", length = 1, nullable = false)
  String nivelActoProcesal;

  @Column(name = "l_permiso_conclusion", length = 1, nullable = false)
  String permisoConclusion;

  @Column(name = "l_dentry", length = 1, nullable = false)
  String dentry;

  @Column(name = "l_ind_print", length = 1)
  String indicadorImpresion;

  @Column(name = "l_cfg_visualiza_ponente", length = 1, nullable = false)
  String cfgVisualizaPonente;

  @Column(name = "l_cfg_hora_vista_dia", length = 1, nullable = false)
  String cfgHoraVistaDia;

  @Column(name = "l_cfg_cambio_tipo_audiencia", length = 1, nullable = false)
  String cfgCambioTipoAudiencia;

  @Column(name = "l_nivel_editor_sumilla", length = 1)
  String nivelEditorSumilla;

  @Column(name = "sumilla_perfil_sup", length = 2)
  String sumillaPerfilSuperior;

  @Column(name = "historial_reniec_sw", length = 1)
  String historialReniec;

  @Column(name = "l_nivel_editor")
  Integer nivelEditor;

  @Column(name = "l_ind_visualiza_alerta", length = 1, nullable = false)
  String indVisualizaAlerta;

  @Column(name = "l_perfil_depositoj")
  Integer perfilDepositoj;

  @Column(name = "l_ind_sesion_multiple", length = 1)
  String indSesionMultiple;

  @Column(name = "l_ind_obliga_correo", length = 1)
  String indObligaCorreo;

  @Column(name = "l_ind_visualiza_visor", length = 1)
  String indVisualizaVisor;
}
