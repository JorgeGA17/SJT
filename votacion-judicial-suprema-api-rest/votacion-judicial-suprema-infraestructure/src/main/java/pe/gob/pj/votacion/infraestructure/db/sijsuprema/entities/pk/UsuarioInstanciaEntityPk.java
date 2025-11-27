package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class UsuarioInstanciaEntityPk implements Serializable{
  
  static final long serialVersionUID = 1L;

  @Column(name = "c_distrito", length = 3, nullable = false)
  String codigoDistrito;

  @Column(name = "c_provincia", length = 4, nullable = false)
  String codigoProvincia;

  @Column(name = "c_instancia", length = 3, nullable = false)
  String codigoInstancia;

  @Column(name = "c_usuario", length = 15, nullable = false)
  String codigoUsuario;

  @Column(name = "f_asignacion", nullable = false)
  LocalDateTime fechaAsignacion;

  @Column(name = "c_sede", length = 4, nullable = false)
  String codigoSede;
}
