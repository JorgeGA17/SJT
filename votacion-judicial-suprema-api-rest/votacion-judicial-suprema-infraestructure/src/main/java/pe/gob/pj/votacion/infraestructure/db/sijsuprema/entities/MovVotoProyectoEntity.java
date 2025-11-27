package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "MovVotoProyecto", schema = EsquemaConstants.DBO)
public class MovVotoProyectoEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "n_id", nullable = false)
  Integer nId;

  @Column(name = "n_votacion", nullable = false)
  Integer numeroVotacion;

  @Column(name = "x_uuid_alfresco", length = 50)
  String uuidAlfresco;

  @Column(name = "x_extension", length = 4)
  String extension;

  @Column(name = "n_envio")
  Integer numeroEnvio;

  @Column(name = "f_envio")
  ZonedDateTime fechaEnvio;

  @Column(name = "l_ultimo", length = 1)
  String ultimo;

  @Column(name = "c_usuarioResp", length = 15, nullable = false)
  String codigoUsuarioResponsable;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "f_registro")
  ZonedDateTime fechaRegistro;

  @Column(name = "l_revisado", length = 1)
  String revisado;

  @Column(name = "l_proyecto", length = 1)
  String proyecto;

  @Column(name = "l_subsana", length = 1)
  String subsana;

  @Column(name = "l_subsanado", length = 1)
  String subsanado;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico"),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente"),
      @JoinColumn(name = "n_sentido", referencedColumnName = "n_sentido")})
  ExpedienteSentidoEntity expedienteSentidoEntity;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "n_idEstado", referencedColumnName = "n_id")
  MaeVotoProyEstadoEntity maeVotoProyEstadoEntity;

}
