package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Embeddable
public class UsuarioEntityPk implements Serializable {
  
  static final long serialVersionUID = 1L;

  @Column(name = "c_usuario", length = 15, nullable = false)
  String codigoUsuario;

  @Column(name = "c_distrito", length = 3, nullable = false)
  String codigoDistrito;

  @Column(name = "c_provincia", length = 4, nullable = false)
  String codigoProvincia;

  @Column(name = "c_sede", length = 4, nullable = false)
  String codigoSede;
}
