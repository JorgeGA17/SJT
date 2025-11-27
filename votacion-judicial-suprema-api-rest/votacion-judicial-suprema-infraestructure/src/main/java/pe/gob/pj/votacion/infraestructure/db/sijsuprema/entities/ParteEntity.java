package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ParteEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "parte", schema = EsquemaConstants.DBO)
public class ParteEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  ParteEntityPk id;

  @Column(name = "c_org_jurisd_penal", length = 2)
  String orgJurisdPenal;

  @Column(name = "c_distrito", length = 3)
  String distrito;

  @Column(name = "c_condicion", length = 2)
  String condicion;

  @Column(name = "c_tipo_persona", length = 1)
  String tipoPersona;

  @Column(name = "l_tipo_parte", length = 3, nullable = false)
  String tipoParte;

  @Column(name = "l_sentenciado", length = 1)
  String sentenciado;

  @Column(name = "x_ape_paterno", length = 200, nullable = false)
  String apePaterno;

  @Column(name = "x_ape_materno", length = 100)
  String apeMaterno;

  @Column(name = "x_nombres", length = 40)
  String nombres;

  @Column(name = "d_domicilio", length = 200)
  String domicilio;

  @Column(name = "d_legal", length = 200)
  String domicilioLegal;

  @Column(name = "x_doc_id", length = 20)
  String docId;

  @Column(name = "x_nom_rpte", length = 60)
  String nomRpte;

  @Column(name = "d_domicilio_rpte", length = 60)
  String domicilioRpte;

  @Column(name = "x_doc_id_rpte", length = 20)
  String docIdRpte;

  @Column(name = "x_apod", length = 100)
  String apod;

  @Column(name = "x_abog", length = 100)
  String abog;

  @Column(name = "x_alias", length = 100)
  String alias;

  @Column(name = "f_parte_civil")
  ZonedDateTime fechaParteCivil;

  @Column(name = "l_tercero", length = 1)
  String tercero;

  @Column(name = "l_participacion", length = 1)
  String participacion;

  @Column(name = "l_confeso", length = 1)
  String confeso;

  @Column(name = "f_ausente")
  ZonedDateTime fechaAusente;

  @Column(name = "l_servidor", length = 1)
  String servidor;

  @Column(name = "f_corte")
  ZonedDateTime fechaCorte;

  @Column(name = "f_nacimiento")
  ZonedDateTime fechaNacimiento;

  @Column(name = "n_edad")
  Integer edad;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "c_usuario_modifica", length = 15)
  String usuarioModifica;

  @Column(name = "c_especialidad", length = 2)
  String especialidad;

  @Column(name = "x_lugar_hechos", length = 100)
  String lugarHechos;

  @Column(name = "x_parte_check", length = 400)
  String parteCheck;

  @Column(name = "l_recurrente", length = 1)
  String recurrente;

  @Column(name = "n_casilla")
  Integer casilla;

  @Column(name = "x_password", length = 10)
  String password;

  @Column(name = "c_tipo_doc", length = 1)
  String tipoDoc;

  @Column(name = "c_distrito_jud", length = 2)
  String distritoJud;

  @Column(name = "c_oficina_casilla", length = 8)
  String oficinaCasilla;

  @Column(name = "id_tipo_direccion", length = 2)
  String tipoDireccion;

  @Column(name = "c_provincia", length = 4)
  String provincia;

  @Column(name = "c_sede", length = 4)
  String sede;

  @Column(name = "l_ind_sexo", length = 1)
  String sexo;

  @Column(name = "c_persona", precision = 10, scale = 0)
  Long persona;

  @Column(name = "l_rehabilitado", length = 1)
  String rehabilitado;

  @Column(name = "n_casilla_electronica", length = 10)
  String casillaElectronica;

  @Column(name = "ind_dinoj", length = 1)
  String indDinoj;

  @Column(name = "l_union", length = 1)
  String union;

  @Column(name = "f_detencion")
  ZonedDateTime fechaDetencion;

  @Column(name = "n_persona_revic")
  Integer personaRevic;

  @Column(name = "n_parte_revic")
  Integer parteRevic;

  @Column(name = "l_visualiza_doc", length = 1)
  String visualizaDoc;

  @Column(name = "x_colegiatura", length = 15)
  String colegiatura;

  @Column(name = "l_parte_civil", length = 1)
  String parteCivil;

  @Column(name = "l_apersonado", length = 1, nullable = false)
  String apersonado;

  @Column(name = "l_informe_oral", length = 1)
  String informeOral;

  @Column(name = "n_secuencia_recursivo")
  Integer secuenciaRecursivo;

  @Column(name = "n_id_persona")
  Integer idPersona;

  @Column(name = "c_relacion_laboral", length = 2)
  String relacionLaboral;

  @Column(name = "c_cargo_laboral", length = 2)
  String cargoLaboral;

  @Column(name = "f_hecho")
  ZonedDateTime fechaHecho;

  @Column(name = "l_publicado_tablilla", length = 1)
  String publicadoTablilla;

  @Column(name = "f_fallecimiento")
  ZonedDateTime fechaFallecimiento;

  @Column(name = "l_ind_discapacidad", length = 1)
  String discapacidad;

  @Column(name = "x_nombre_padre", length = 150)
  String nombrePadre;

  @Column(name = "x_nombre_madre", length = 150)
  String nombreMadre;

  @Column(name = "l_conductor", length = 1, nullable = false)
  String conductor;

  @Column(name = "l_propietario", length = 1, nullable = false)
  String propietario;

  @Column(name = "x_nro_licencia", length = 20)
  String nroLicencia;

  @Column(name = "x_clase", length = 20)
  String clase;

  @Column(name = "x_categoria", length = 20)
  String categoria;

  @Column(name = "l_validado", length = 1)
  String validado;

  @Column(name = "n_edad_cronologica")
  Integer edadCronologica;

  @Column(name = "x_correo_electronico", length = 50)
  String correoElectronico;

  @Column(name = "x_telefono", length = 10)
  String telefono;

  @Column(name = "l_etnicidad", length = 1)
  String etnicidad;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_ubigeo_nacimiento", referencedColumnName = "c_ubigeo")
  UbigeoEntity ubigeoNacimientoEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_ubigeo_dlegal", referencedColumnName = "c_ubigeo")
  UbigeoEntity ubigeoLegalEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_ubigeo_dreal", referencedColumnName = "c_ubigeo")
  UbigeoEntity ubigeoRealEntity;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "c_colegio", referencedColumnName = "c_colegio")
  ColegioAbogadosEntity colegioEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "l_tipo_parte", referencedColumnName = "l_tipo_parte", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_especialidad", referencedColumnName = "c_especialidad",
          insertable = false, updatable = false)})
  TipoParteEntity tipoParteEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "n_id_persona", referencedColumnName = "n_id_persona", insertable = false,
      updatable = false)
  PersonaMaestroEntity personaMaestro;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_cargo_laboral", referencedColumnName = "c_cargo_laboral",
      insertable = false, updatable = false)
  CargoLaboralEntity cargoLaboralEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_relacion_laboral", referencedColumnName = "c_cargo_laboral",
      insertable = false, updatable = false)
  CargoLaboralEntity relacionLaboralEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_tipo_doc", referencedColumnName = "c_tipo", insertable = false,
      updatable = false)
  TipoDocumentoIdentidadEntity tipoDocEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false)})
  ExpedienteEntity expedienteEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_tipo_persona", referencedColumnName = "c_tipo_persona", insertable = false,
      updatable = false)
  TipoPersonaEntity tipoPersonaEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito", insertable = false,
      updatable = false)
  DistritoJudicialEntity distritoJudicialEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_especialidad", referencedColumnName = "c_especialidad", insertable = false,
      updatable = false)
  EspecialidadEntity especialidadEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_sede", referencedColumnName = "c_sede", insertable = false,
      updatable = false)
  SedeEntity sedeEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_org_jurisd_penal", referencedColumnName = "c_org_jurisd",
      insertable = false, updatable = false)
  OrganoJurisdiccionalEntity orgJurisdEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_condicion", referencedColumnName = "c_condicion", insertable = false,
      updatable = false)
  CondicionEntity condicionEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_tipo_direccion", referencedColumnName = "id_tipo_direccion",
      insertable = false, updatable = false)
  TipoDirecANotificarEntity tipoDireccionEntity;

}
