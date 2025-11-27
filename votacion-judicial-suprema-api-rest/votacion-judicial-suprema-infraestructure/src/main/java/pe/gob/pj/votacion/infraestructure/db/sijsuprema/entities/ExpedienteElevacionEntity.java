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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteElevacionEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "expediente_elevacion", schema = EsquemaConstants.DBO)
public class ExpedienteElevacionEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  ExpedienteElevacionEntityPk id;

  @Column(name = "n_resolucion_ape", length = 120)
  String resolucionApe;

  @Column(name = "f_apelacion")
  ZonedDateTime apelacion;

  @Column(name = "n_resolucion_con", length = 120)
  String resolucionCon;

  @Column(name = "f_consesorio")
  ZonedDateTime consesorio;

  @Column(name = "f_admisorio")
  ZonedDateTime admisorio;

  @Column(name = "l_tipo_elevacion", length = 1)
  String tipoElevacion;

  @Column(name = "n_nro_exp_juz", precision = 16, scale = 0)
  BigDecimal nroExpJuz;

  @Column(name = "n_ano_exp_juz", length = 4)
  String anoExpJuz;

  @Column(name = "n_inc_exp_juz")
  Integer incExpJuz;

  @Column(name = "c_distrito_juz", length = 3)
  String distritoJuz;

  @Column(name = "c_provincia_juz", length = 4)
  String provinciaJuz;

  @Column(name = "c_instancia_juz", length = 3)
  String instanciaJuz;

  @Column(name = "x_juez", length = 180)
  String juez;

  @Column(name = "x_secretario", length = 100)
  String secretario;

  @Column(name = "c_provincia_orig", length = 4)
  String provinciaOrig;

  @Column(name = "c_instancia_orig", length = 3)
  String instanciaOrig;

  @Column(name = "n_unico_orig", precision = 20, scale = 0)
  BigDecimal unicoOrig;

  @Column(name = "n_incidente_orig")
  Integer incidenteOrig;

  @Column(name = "f_ingreso_orig")
  ZonedDateTime ingresoOrig;

  @Column(name = "f_registro_orig")
  ZonedDateTime registroOrig;

  @Column(name = "c_estado", length = 3)
  String estado;

  @Column(name = "x_sumilla", length = 200)
  String sumilla;

  @Column(name = "n_unico_juz", precision = 20, scale = 0)
  BigDecimal unicoJuz;

  @Column(name = "c_especialidad_juz", length = 2)
  String especialidadJuz;

  @Column(name = "n_nro_exp_orig")
  Integer nroExpOrig;

  @Column(name = "n_ano_exp_orig", length = 4)
  String anoExpOrig;

  @Column(name = "n_resol_apl_juz", length = 40)
  String resolAplJuz;

  @Column(name = "f_resol_apl_juz")
  ZonedDateTime resolAplJuzFecha;

  @Column(name = "f_apertorio_juz")
  ZonedDateTime apertorioJuz;

  @Column(name = "l_tipo_apl_juz", length = 1)
  String tipoAplJuz;

  @Column(name = "x_parte_referencia", length = 100)
  String parteReferencia;

  @Column(name = "c_delito", length = 3)
  String delito;

  @Column(name = "c_item", length = 3)
  String item;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "f_apel_sala")
  ZonedDateTime apelSala;

  @Column(name = "c_tipo_apela_sala", length = 3)
  String tipoApelaSala;

  @Column(name = "c_org_jurisd_orig", length = 2)
  String orgJurisdOrig;

  @Column(name = "c_org_jurisd_juz", length = 2)
  String orgJurisdJuz;

  @Column(name = "f_califica_orig")
  ZonedDateTime calificaOrig;

  @Column(name = "f_resolucion_orig")
  ZonedDateTime resolucionOrig;

  @Column(name = "n_fojas_orig")
  Integer numeroFojasOrigen;

  @Column(name = "f_demanda_juz")
  ZonedDateTime demandaJuz;

  @Column(name = "f_auto_juz")
  ZonedDateTime autoJuz;

  @Column(name = "c_acto_procesal_ini", length = 3)
  String actoProcesalIni;

  @Column(name = "f_acto_procesal_ini")
  ZonedDateTime actoProcesalIniFecha;

  @Column(name = "f_resolucion_juz")
  ZonedDateTime resolucionJuz;

  @Column(name = "n_fojas_juz")
  Integer numeroFojasJuzgado;

  @Column(name = "n_nro_exp_sup")
  Integer nroExpSup;

  @Column(name = "n_ano_exp_sup", length = 4)
  String anoExpSup;

  @Column(name = "x_desc_motivo_sup", length = 100)
  String descMotivoSup;

  @Column(name = "c_distrito_orig", length = 3)
  String distritoOrig;

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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_acto_procesal_orig", referencedColumnName = "c_acto_procesal")
  ActoProcesalEntity actoProcesalOrigenEntity;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "c_acto_procesal_juz", referencedColumnName = "c_acto_procesal")
  ActoProcesalEntity actoProcesalJuzgadoEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_distrito_juz", referencedColumnName = "c_distrito", insertable = false,
      updatable = false)
  DistritoJudicialEntity distritoJudicialJuzgadoEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_distrito_orig", referencedColumnName = "c_distrito", insertable = false,
      updatable = false)
  DistritoJudicialEntity distritoJudicialOrigenEntity;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_fallo_sala", referencedColumnName = "c_fallo")
  FalloEntity falloSalaEntity;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "c_fallo_juz", referencedColumnName = "c_fallo")
  FalloEntity falloJuzgadoEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {@JoinColumn(name = "c_distrito_sup", referencedColumnName = "c_distrito"),
      @JoinColumn(name = "c_provincia_sup", referencedColumnName = "c_provincia"),
      @JoinColumn(name = "c_instancia_sup", referencedColumnName = "c_instancia")})
  InstanciaEntity instanciaEntity;

}
