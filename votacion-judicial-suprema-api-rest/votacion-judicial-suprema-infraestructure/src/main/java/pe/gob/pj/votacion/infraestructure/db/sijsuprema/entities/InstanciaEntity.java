package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.InstanciaEntityPk;

@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "instancia", schema = EsquemaConstants.DBO)
public class InstanciaEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  InstanciaEntityPk id;

  @Column(name = "x_nom_instancia", length = 60, nullable = false)
  String nombreInstancia;

  @Column(name = "n_instancia", nullable = false)
  Integer numeroInstancia;

  @Column(name = "l_munica", length = 1)
  String indicadorMunica;

  @Column(name = "l_asig", length = 1)
  String indicadorAsignacion;

  @Column(name = "n_modulo")
  Integer numeroModulo;

  @Column(name = "l_modulo_ejecucion", length = 1)
  String moduloEjecucion;

  @Column(name = "l_servidor_atachado", length = 1)
  String servidorAtachado;

  @Column(name = "x_ubicacion_fisica", length = 50)
  String ubicacionFisica;

  @Column(name = "cod_unico_instancia", length = 6)
  String codigoUnicoInstancia;

  @Column(name = "l_independiente", length = 1)
  String indicadorIndependiente;

  @Column(name = "n_carga_procesal", nullable = false)
  Integer cargaProcesal;

  @Column(name = "l_carcel", length = 1)
  String indicadorCarcel;

  @Column(name = "x_corto", length = 4)
  String nombreCorto;

  @Column(name = "n_juzgado")
  Integer numeroJuzgado;

  @Column(name = "n_distrito")
  Integer numeroDistrito;

  @Column(name = "provincia")
  Integer provincia;

  @Column(name = "l_par_impar", length = 1)
  String parImpar;

  @Column(name = "eq_codg_archivo", length = 6)
  String codigoArchivo;

  @Column(name = "eq_codg_central", length = 3)
  String codigoCentral;

  @Column(name = "n_maximo")
  Integer numeroMaximo;

  @Column(name = "contador_ing")
  Integer contadorIngreso;

  @Column(name = "n_carga_redistribucion")
  Integer cargaRedistribucion;

  @Column(name = "n_dependencia")
  Integer dependencia;

  @Column(name = "l_ind_ingreso")
  Integer indicadorIngreso;

  @Column(name = "l_ind_ped")
  Integer indicadorPed;

  @Column(name = "l_ind_bd", length = 1)
  String indicadorBaseDatos;

  @Column(name = "c_sede", length = 4)
  String codigoSede;

  @Column(name = "l_ind_barcode", length = 1)
  String indicadorBarcode;

  @Column(name = "c_ubigeo", length = 10)
  String codigoUbigeo;

  @Column(name = "l_ind_baja", length = 1)
  String indicadorBaja;

  @Column(name = "l_ind_turno", length = 1)
  String indicadorTurno;

  @Column(name = "n_carga_max", nullable = false)
  Integer cargaMaxima;

  @Column(name = "n_carga_envio", nullable = false)
  Integer cargaEnvio;

  @Column(name = "l_ind_electronico", length = 1)
  String indicadorElectronico;

  @Column(name = "c_jurisprudencia", length = 4)
  String codigoJurisprudencia;

  @Column(name = "n_ratio")
  Integer ratio;

  @Column(name = "l_sij", length = 1)
  String indicadorSij;

  @Column(name = "n_carga_proceso_eleva")
  Integer cargaProcesoEleva;

  @Column(name = "n_carga_guia_tp")
  Integer cargaGuiaTp;

  @Column(name = "c_org_jurisd_suprema", length = 2)
  String codigoOrganoJurisdiccionalSuprema;

  @Column(name = "n_tiempo_cronica")
  Integer tiempoCronica;

  @Column(name = "l_visualiza_grupo_adicional", length = 1, nullable = false)
  String visualizaGrupoAdicional;

  @Column(name = "l_visualiza_edicion_tablilla", length = 1, nullable = false)
  String visualizaEdicionTablilla;

  @Column(name = "l_visualiza_bloque", length = 1, nullable = false)
  String visualizaBloque;

  @Column(name = "l_ind_orden_recurso", length = 1, nullable = false)
  String indicadorOrdenRecurso;

  @Column(name = "l_evalua_exp_duplicado", length = 1, nullable = false)
  String evaluaExpDuplicado;

  @Column(name = "l_filtrar_urgente_nlpt", length = 1, nullable = false)
  String filtrarUrgenteNlpt;

  @Column(name = "l_agrega_a_grupo_adicional", length = 1, nullable = false)
  String agregaGrupoAdicional;

  @Column(name = "t_hora_vista_default")
  LocalTime horaVistaDefault;

  @Column(name = "l_codbarra_descargo_m", length = 1)
  String codBarraDescargoM;

  @Column(name = "l_impresion_multiple_tablilla", length = 1, nullable = false)
  String impresionMultipleTablilla;

  @Column(name = "l_tipo_orden", length = 1)
  String tipoOrden;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "l_filtra_especialidad", length = 1)
  String filtraEspecialidad;

  @Column(name = "l_busqueda_prog_x_recurso", length = 1, nullable = false)
  String busquedaProgRecurso;

  @Column(name = "l_ind_cms", length = 1, nullable = false)
  String indicadorCms;

  @Column(name = "x_corto_jpub", length = 10)
  String cortoJpub;

  @Column(name = "n_instancia_id")
  Integer instanciaId;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "c_org_jurisd", referencedColumnName = "c_org_jurisd")
  OrganoJurisdiccionalEntity organoJurisdiccionalEntity;

}
