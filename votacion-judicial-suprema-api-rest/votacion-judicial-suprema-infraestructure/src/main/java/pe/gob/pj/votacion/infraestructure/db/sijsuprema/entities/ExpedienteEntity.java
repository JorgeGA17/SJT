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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ExpedienteEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "expediente", schema = EsquemaConstants.DBO)
public class ExpedienteEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @EmbeddedId
  ExpedienteEntityPk id;

  @Column(name = "c_usuario", length = 15)
  String cUsuario;

  @Column(name = "f_inicio")
  ZonedDateTime fInicio;

  @Column(name = "f_termino")
  ZonedDateTime fTermino;

  @Column(name = "n_unico_relacion", precision = 20, scale = 0, nullable = false)
  BigDecimal nUnicoRelacion;

  @Column(name = "l_anulado", length = 1, nullable = false)
  String lAnulado;

  @Column(name = "l_acumulado", length = 1, nullable = false)
  String lAcumulado;

  @Column(name = "n_nro_exp_origen")
  Integer nNroExpOrigen;

  @Column(name = "n_ano_exp_origen")
  Integer nAnoExpOrigen;

  @Column(name = "f_auto_apertura")
  ZonedDateTime fAutoApertura;

  @Column(name = "f_ing_origen")
  ZonedDateTime fIngOrigen;

  @Column(name = "f_incidente")
  ZonedDateTime fIncidente;

  @Column(name = "x_sumilla", length = 600)
  String xSumilla;

  @Column(name = "f_denuncia")
  ZonedDateTime fDenuncia;

  @Column(name = "n_denuncia", length = 150)
  String nDenuncia;

  @Column(name = "n_atestado", length = 150)
  String nAtestado;

  @Column(name = "f_anulado")
  ZonedDateTime fAnulado;

  @Column(name = "x_anulado", length = 180)
  String xAnulado;

  @Column(name = "l_parte_interpone", length = 1)
  String lParteInterpone;

  @Column(name = "n_ventanilla")
  Integer nVentanilla;

  @Column(name = "n_sec_ingreso")
  Integer nSecIngreso;

  @Column(name = "n_ano_ingreso")
  Integer nAnoIngreso;

  @Column(name = "l_servidor", length = 1)
  String lServidor;

  @Column(name = "l_exp_agrario", length = 1)
  String lExpAgrario;

  @Column(name = "l_devolver_ejec", length = 1)
  String lDevolverEjec;

  @Column(name = "c_instancia_ejec", length = 3)
  String cInstanciaEjec;

  @Column(name = "f_devuelto_ejec")
  ZonedDateTime fDevueltoEjec;

  @Column(name = "n_cedulas")
  Integer nCedulas;

  @Column(name = "x_tasa_judicial", length = 255)
  String xTasaJudicial;

  @Column(name = "c_ind_modifica", length = 1)
  String cIndModifica;

  @Column(name = "c_cod_archivo", length = 15)
  String cCodArchivo;

  @Column(name = "c_cod_visualiza", length = 1)
  String cCodVisualiza;

  @Column(name = "c_cod_medida_cautelar", length = 6)
  String cCodMedidaCautelar;

  @Column(name = "l_archivo", length = 1)
  String lArchivo;

  @Column(name = "x_observacion", length = 200)
  String xObservacion;

  @Column(name = "x_nombre_fiscal", length = 70)
  String xNombreFiscal;

  @Column(name = "x_fiscalia", length = 70)
  String xFiscalia;

  @Column(name = "x_oficina_fiscal", length = 15)
  String xOficinaFiscal;

  @Column(name = "f_hecho")
  ZonedDateTime fHecho;

  @Column(name = "l_tipo_registro", length = 1)
  String lTipoRegistro;

  @Column(name = "n_juz_exp_origen")
  Integer nJuzExpOrigen;

  @Column(name = "f_registro")
  ZonedDateTime fRegistro;

  @Column(name = "x_secretario", length = 100)
  String xSecretario;

  @Column(name = "f_ult_modificacion")
  ZonedDateTime fUltModificacion;

  @Column(name = "c_usu_ult_modi", length = 15)
  String cUsuUltModi;

  @Column(name = "l_confrontado", length = 1)
  String lConfrontado;

  @Column(name = "f_confrontacion")
  ZonedDateTime fConfrontacion;

  @Column(name = "c_usu_confrontacion", length = 15)
  String cUsuConfrontacion;

  @Column(name = "x_ape_secretario_antiguo", length = 50)
  String xApeSecretarioAntiguo;

  @Column(name = "x_secretario_antiguo", length = 60)
  String xSecretarioAntiguo;

  @Column(name = "l_cond", length = 1)
  String lCond;

  @Column(name = "n_exp_sala")
  Integer nExpSala;

  @Column(name = "n_ano_sala", length = 4)
  String nAnoSala;

  @Column(name = "c_c_codprovincia")
  Integer cCCodprovincia;

  @Column(name = "c_c_coddistrito")
  Integer cCCoddistrito;

  @Column(name = "c_etapa", length = 3)
  String cEtapa;

  @Column(name = "c_estado", length = 3)
  String cEstado;

  @Column(name = "c_materia", length = 3)
  String cMateria;

  @Column(name = "c_ubicacion", length = 2)
  String cUbicacion;

  @Column(name = "c_pos_usuario", length = 15)
  String cPosUsuario;

  @Column(name = "n_exp_sala_ant")
  Integer nExpSalaAnt;

  @Column(name = "n_ano_sala_ant", length = 4)
  String nAnoSalaAnt;

  @Column(name = "f_origen_sala")
  ZonedDateTime fOrigenSala;

  @Column(name = "c_secretario", length = 15)
  String cSecretario;

  @Column(name = "n_unico_juz")
  BigDecimal nUnicoJuz;

  @Column(name = "n_incidente_juz")
  Integer nIncidenteJuz;

  @Column(name = "x_desc_cedula", length = 255)
  String xDescCedula;

  @Column(name = "c_ubigeo_fiscalia", length = 10)
  String cUbigeoFiscalia;

  @Column(name = "n_ano_exp_juz", length = 4)
  String nAnoExpJuz;

  @Column(name = "n_procedencia_juz")
  Integer nProcedenciaJuz;

  @Column(name = "n_tip_procedencia_juz", length = 2)
  String nTipProcedenciaJuz;

  @Column(name = "c_distrito_juz", length = 3)
  String cDistritoJuz;

  @Column(name = "c_provincia_juz", length = 4)
  String cProvinciaJuz;

  @Column(name = "n_cuantia", precision = 18, scale = 2)
  BigDecimal nCuantia;

  @Column(name = "moneda_cuantia", length = 2)
  String monedaCuantia;

  @Column(name = "n_inc_exp_juz")
  Integer nIncExpJuz;

  @Column(name = "n_nro_exp_salas", precision = 16, scale = 0)
  BigDecimal nNroExpSalas;

  @Column(name = "n_ano_exp_salas", length = 4)
  String nAnoExpSalas;

  @Column(name = "n_inc_exp_salas")
  Integer nIncExpSalas;

  @Column(name = "n_procedencia_salas")
  Integer nProcedenciaSalas;

  @Column(name = "n_tip_procedencia_salas", length = 2)
  String nTipProcedenciaSalas;

  @Column(name = "c_distrito_salas", length = 3)
  String cDistritoSalas;

  @Column(name = "c_provincia_salas", length = 4)
  String cProvinciaSalas;

  @Column(name = "f_apelado_salas")
  ZonedDateTime fApeladoSalas;

  @Column(name = "x_fallo_salas", length = 500)
  String xFalloSalas;

  @Column(name = "n_oficio_elevado_salas", length = 80)
  String nOficioElevadoSalas;

  @Column(name = "l_es_carcel", length = 1)
  String lEsCarcel;

  @Column(name = "x_desc_depositoj", length = 255)
  String xDescDepositoj;

  @Column(name = "x_juez", length = 180)
  String xJuez;

  @Column(name = "n_nro_exp_juz", precision = 16, scale = 0)
  BigDecimal nNroExpJuz;

  @Column(name = "n_nro_en_juzgado")
  Integer nNroEnJuzgado;

  @Column(name = "n_ano_en_juzgado")
  Integer nAnoEnJuzgado;

  @Column(name = "n_tasas")
  Integer nTasas;

  @Column(name = "n_deposito")
  Integer nDeposito;

  @Column(name = "f_etapa")
  ZonedDateTime fEtapa;

  @Column(name = "f_estado")
  ZonedDateTime fEstado;

  @Column(name = "c_usuario_asignadoa", length = 15)
  String cUsuarioAsignadoa;

  @Column(name = "f_usuario_asignadoa")
  ZonedDateTime fUsuarioAsignadoa;

  @Column(name = "f_materia")
  ZonedDateTime fMateria;

  @Column(name = "f_ubicacion")
  ZonedDateTime fUbicacion;

  @Column(name = "c_usuario_actual", length = 15)
  String cUsuarioActual;

  @Column(name = "f_usuario_actual")
  ZonedDateTime fUsuarioActual;

  @Column(name = "x_motivo_reasignacion", length = 200)
  String xMotivoReasignacion;

  @Column(name = "c_sede", length = 4, nullable = false)
  String cSede;

  @Column(name = "c_ubigeo_hecho", length = 10)
  String cUbigeoHecho;

  @Column(name = "c_fiscalia", length = 5)
  String cFiscalia;

  @Column(name = "x_exp_duplicados", length = 700)
  String xExpDuplicados;

  @Column(name = "l_autoriza_prev", length = 1, nullable = false)
  String lAutorizaPrev;

  @Column(name = "n_copias")
  Integer nCopias;

  @Column(name = "c_perfil", length = 2)
  String cPerfil;

  @Column(name = "c_org_delito", length = 2)
  String cOrgDelito;

  @Column(name = "c_delito", length = 3)
  String cDelito;

  @Column(name = "c_ubigeo", length = 10)
  String cUbigeo;

  @Column(name = "c_org_jurisd", length = 2)
  String cOrgJurisd;

  @Column(name = "c_instancia_ori", length = 3)
  String cInstanciaOri;

  @Column(name = "l_elevacion", length = 1, nullable = false)
  String lElevacion;

  @Column(name = "c_distrito_ori", length = 3)
  String cDistritoOri;

  @Column(name = "c_provincia_ori", length = 4)
  String cProvinciaOri;

  @Column(name = "l_ind_previo", length = 1)
  String lIndPrevio;

  @Column(name = "c_incidente_old", length = 3)
  String cIncidenteOld;

  @Column(name = "c_nivel_instancia", length = 1)
  String cNivelInstancia;

  @Column(name = "l_union", length = 1)
  String lUnion;

  @Column(name = "l_tipo_juzgado_exp", length = 1)
  String lTipoJuzgadoExp;

  @Column(name = "l_info_oral_suprema", length = 1)
  String lInfoOralSuprema;

  @Column(name = "c_letra", length = 2)
  String codigoLetra;

  @Column(name = "x_oficio", length = 60)
  String xOficio;

  @Column(name = "c_ind_ncpp", length = 1)
  String cIndNcpp;

  @Column(name = "l_previsional", length = 1)
  String lPrevisional;

  @Column(name = "f_oficio")
  ZonedDateTime fOficio;

  @Column(name = "l_migrado", length = 1, nullable = false)
  String lMigrado;

  @Column(name = "l_ind_casacion_directa", length = 1, nullable = false)
  String lIndCasacionDirecta;

  @Column(name = "fecha_recepcion")
  ZonedDateTime fechaRecepcion;

  @Column(name = "c_tipo_alerta", length = 1)
  String cTipoAlerta;

  @Column(name = "l_judicial_digital")
  Integer lJudicialDigital;

  @Column(name = "n_paginas")
  Integer nPaginas;

  @Column(name = "x_anaquel", length = 60)
  String xAnaquel;

  @Column(name = "l_ind_cuantia_completo", length = 1)
  String lIndCuantiaCompleto;

  @Column(name = "l_ind_notifi_completo", length = 1)
  String lIndNotifiCompleto;

  @Column(name = "l_ind_sin_arancel", length = 1)
  String lIndSinArancel;

  @Column(name = "l_ind_sin_notif", length = 1)
  String lIndSinNotif;

  @Column(name = "n_tipo_cambio", precision = 12, scale = 3)
  BigDecimal nTipoCambio;

  @Column(name = "c_tipo_persona", length = 1)
  String cTipoPersona;

  @Column(name = "c_tipo_doc", length = 1)
  String cTipoDoc;

  @Column(name = "l_tipo_parte", length = 3)
  String lTipoParte;

  @Column(name = "x_doc_id", length = 20)
  String xDocId;

  @Column(name = "x_destinatario", length = 200)
  String xDestinatario;

  @Column(name = "n_ano_vinculado")
  Integer nAnoVinculado;

  @Column(name = "n_exp_vinculado")
  Integer nExpVinculado;

  @Column(name = "l_ind_cuantia_indeterminado", length = 1)
  String lIndCuantiaIndeterminado;

  @Column(name = "c_es_remate_jud", length = 1)
  String cEsRemateJud;

  @Column(name = "c_tipo_remate_jud", length = 2)
  String cTipoRemateJud;

  @Column(name = "c_es_exhorto", length = 1)
  String cEsExhorto;

  @Column(name = "c_tipo_exhorto", length = 2)
  String cTipoExhorto;

  @Column(name = "n_edad_cronologica")
  Integer nEdadCronologica;

  @Column(name = "l_ind_apoyo", length = 1)
  String lIndApoyo;

  @Column(name = "l_ind_inv", length = 1)
  String lIndInv;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito"),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia"),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia")})
  InstanciaEntity instanciaEntity;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_motivo_ingreso", referencedColumnName = "c_motivo_ingreso")
  MotivoIngresoMaestroEntity motivoIngresoMaestroEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_especialidad", referencedColumnName = "c_especialidad")
  EspecialidadEntity especialidadEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_incidente", referencedColumnName = "c_incidente")
  IncidenteMaestroEntity incidenteMaestroEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_dispositivo_legal", referencedColumnName = "c_dispositivo_legal")
  DispositivoLegalEntity dispositivoLegalEntity;

}
