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
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ColegiadoInstanciaDetalleEntityPk;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "colegiado_instancia_detalle", schema = EsquemaConstants.DBO)
public class ColegiadoInstanciaDetalleEntity extends AuditoriaSupremaEntity
    implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  ColegiadoInstanciaDetalleEntityPk id;

  @Column(name = "c_usuario", length = 15)
  String cUsuario;

  @Column(name = "c_perfil", length = 2)
  String cPerfil;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "f_registro")
  ZonedDateTime fRegistro;

  @Column(name = "l_ind_nivel_instruccion", length = 1)
  String lIndNivelInstruccion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia", insertable = false,
          updatable = false),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_colegiado_sec", referencedColumnName = "n_colegiado_sec",
          insertable = false, updatable = false)})
  ColegiadoInstanciaEntity colegiadoInstancia;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_rol", referencedColumnName = "c_rol")
  SalaColegiadoRolEntity salaColegiadoRol;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_tipo_vocal", referencedColumnName = "c_tipo_vocal")
  TipoVocalEntity tipoVocal;
}
