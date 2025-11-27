package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import jakarta.persistence.*;
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
@Table(name = "MovVotoDiscordia", schema = EsquemaConstants.DBO)
public class MovVotoDiscordiaEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "n_id")
  Integer id;

  @Column(name = "n_unico", precision = 20, scale = 0, nullable = false)
  BigDecimal numeroUnico;

  @Column(name = "n_incidente", nullable = false)
  Integer numeroIncidente;

  @Column(name = "n_sentido", nullable = false)
  Integer numeroSentido;

  @Column(name = "n_secuenciaParte", nullable = false)
  Integer numeroSecuenciaParte;

  @Column(name = "n_votacion", nullable = false)
  Integer numeroVotacion;

  @Column(name = "c_usuarioDiscordia", length = 15, nullable = false)
  String codigoUsuarioDiscordia;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "f_registro")
  ZonedDateTime fRegistro;

}
