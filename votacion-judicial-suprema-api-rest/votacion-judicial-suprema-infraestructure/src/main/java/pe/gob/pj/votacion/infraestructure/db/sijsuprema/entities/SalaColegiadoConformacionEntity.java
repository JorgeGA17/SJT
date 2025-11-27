package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.SalaColegiadoConformacionEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "sala_colegiado_conformacion", schema = EsquemaConstants.DBO)
public class SalaColegiadoConformacionEntity extends AuditoriaSupremaEntity
    implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  SalaColegiadoConformacionEntityPk id;

  @Column(name = "c_usuario", length = 15, nullable = false)
  String codigoUsuario;

  @Column(name = "l_activo", length = 1)
  String activo = "S";

  @Column(name = "cant_expedientes")
  Integer cantExpedientes;

  @Column(name = "l_ind_nivel_instruccion", length = 1)
  String indNivelInstruccion;

  @Column(name = "l_ind_nivel_intrucccion", length = 1)
  String indNivelIntrucccion;

  @Column(name = "cant_unipersonal")
  Integer cantUnipersonal;

  @Column(name = "c_org_jurisd_suprema", length = 2)
  String orgJurisdSuprema;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_rol", referencedColumnName = "c_rol")
  SalaColegiadoRolEntity salaColegiadoRolEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_colegiado", referencedColumnName = "n_colegiado", insertable = false,
          updatable = false)})
  SalaColegiadoEntity salaColegiadoEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_perfil", referencedColumnName = "codper")
  PerfilEntity perfil;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_tipo_vocal", referencedColumnName = "c_tipo_vocal")
  TipoVocalEntity tipoVocalEntity;

}
