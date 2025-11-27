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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.DocumentoDigitalEscritoEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "documento_digital_escrito", schema = EsquemaConstants.DBO)
public class DocumentoDigitalEscritoEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  DocumentoDigitalEscritoEntityPk id;

  @Column(name = "n_unico", precision = 20, scale = 0)
  BigDecimal nUnico;

  @Column(name = "n_incidente")
  Integer nIncidente;

  @Column(name = "c_distrito", length = 3)
  String cDistrito;

  @Column(name = "c_provincia", length = 4)
  String cProvincia;

  @Column(name = "c_instancia", length = 3)
  String cInstancia;

  @Column(name = "n_ano")
  Integer nAno;

  @Column(name = "n_secuencia")
  Integer nSecuencia;

  @Column(name = "f_asociacion")
  ZonedDateTime fAsociacion;

  @Column(name = "l_estado", length = 1)
  String lEstado;

  @Column(name = "n_pagina_ini_doc")
  Integer nPaginaIniDoc;

  @Column(name = "n_pagina_fin_doc")
  Integer nPaginaFinDoc;

  @Column(name = "n_orden")
  Integer nOrden;

  @Column(name = "l_tipo_doc", length = 3)
  String lTipoDoc;

  @Column(name = "l_ind_parte", length = 1, nullable = false)
  String lIndParte;

  @Column(name = "c_usuario", length = 30)
  String cUsuario;

  @Column(name = "x_motivo_anulacion", length = 180)
  String xMotivoAnulacion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_sede", referencedColumnName = "c_sede", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_documento", referencedColumnName = "n_documento", insertable = false,
          updatable = false)})
  DocumentoDigitalEntity documentoDigitalEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_sede", referencedColumnName = "c_sede", insertable = false,
      updatable = false)
  SedeEntity sede;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_especialidad", referencedColumnName = "c_especialidad", insertable = false,
      updatable = false)
  EspecialidadEntity especialidad;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_org_jurisd", referencedColumnName = "c_org_jurisd", insertable = false,
      updatable = false)
  OrganoJurisdiccionalEntity organoJurisdiccionalEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia", insertable = false,
          updatable = false)})
  InstanciaEntity instanciaEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_ano", referencedColumnName = "n_ano", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_secuencia", referencedColumnName = "n_secuencia", insertable = false,
          updatable = false)})
  PedidoEspecialEntity pedidoEspecialEntity;
}
