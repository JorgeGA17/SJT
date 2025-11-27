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
@Table(name = "ipt_cfg", schema = EsquemaConstants.DBO)
public class IptCfgEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "cfg_id")
  Integer cfgId;

  @Column(name = "cfg_des", length = 50, nullable = false)
  String cfgDes;

  @Column(name = "cfg_codpar", length = 500, nullable = false)
  String cfgCodpar;

  @Column(name = "cfg_valpar", length = 500, nullable = false)
  String cfgValpar;

  @Column(name = "cfg_c_sede_prin", length = 1)
  String cfgCSedePrin;

  @Column(name = "cfg_c_distrito", length = 3)
  String cfgCDistrito;
  
}
