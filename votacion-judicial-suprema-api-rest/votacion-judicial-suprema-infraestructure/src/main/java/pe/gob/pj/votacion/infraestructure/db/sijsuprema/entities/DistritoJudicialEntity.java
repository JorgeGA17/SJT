package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;

@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "distrito_judicial", schema = EsquemaConstants.DBO)
public class DistritoJudicialEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_distrito", length = 3, nullable = false)
  String codigoDistrito;

  @Column(name = "x_nom_distrito", length = 30)
  String nombreDistrito;

  @Column(name = "c_ubigeo", length = 10)
  String codigoUbigeo;

  @Column(name = "c_jurisprudencia", length = 4)
  String codigoJurisprudencia;

  @Column(name = "l_ind_activo", length = 1)
  String indicadorActivo;

}
