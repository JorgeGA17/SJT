package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;


import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.LocalTime;
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
@Table(name = "programacion_instancia", schema = EsquemaConstants.DBO)
public class ProgramacionInstanciaEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_programacion", length = 10, nullable = false)
  String codigoProgramacion;

  @Column(name = "n_programacion")
  Integer numeroProgramacion;

  @Column(name = "f_inicio")
  ZonedDateTime fechaInicio;

  @Column(name = "f_fin")
  ZonedDateTime fechaFin;

  @Column(name = "c_semana", length = 8)
  String codigoSemana;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "f_registro")
  ZonedDateTime fechaRegistro;

  @Column(name = "c_usuario", length = 15)
  String codigoUsuario;

  @Column(name = "n_total_grupo")
  Integer nTotalGrupo;

  @Column(name = "l_migrado", length = 1)
  String lMigrado;

  @Column(name = "l_pub_automatico", length = 1)
  String lPubAutomatico;

  @Column(name = "t_hora_vista_default")
  LocalTime tHoraVistaDefault;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumns(value = {
      @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito"),
      @JoinColumn(name = "c_provincia", referencedColumnName = "c_provincia"),
      @JoinColumn(name = "c_instancia", referencedColumnName = "c_instancia"),
  })
  InstanciaEntity instanciaEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_estado_prog", referencedColumnName = "c_estado_prog")
  EstadoProgramacionEntity estadoProgramacionEntity;
}
