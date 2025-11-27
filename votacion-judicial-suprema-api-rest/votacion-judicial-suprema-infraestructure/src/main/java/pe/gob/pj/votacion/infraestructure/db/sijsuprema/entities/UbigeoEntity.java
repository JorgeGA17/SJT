package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ubigeo", schema = EsquemaConstants.DBO)
public class UbigeoEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_ubigeo", length = 10, nullable = false)
  String cUbigeo;

  @Column(name = "x_ubigeo", length = 140)
  String xUbigeo;

  @Column(name = "c_ubigeo_sup", length = 10)
  String cUbigeoSup;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "c_pais", length = 3)
  String cPais;

}
