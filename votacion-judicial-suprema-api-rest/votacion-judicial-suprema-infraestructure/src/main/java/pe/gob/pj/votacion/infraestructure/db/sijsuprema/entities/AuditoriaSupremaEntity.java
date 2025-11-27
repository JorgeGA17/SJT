package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public abstract class AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Column(name = "f_aud")
  ZonedDateTime fechaAuditoria;

  @Column(name = "b_aud", length = 1)
  String bitacoraAuditoria = "I";

  @Column(name = "c_aud_uid", length = 30)
  String usuarioAuditoria;

  @Column(name = "c_aud_uidred", length = 30)
  String usuarioRedAuditoria;

  @Column(name = "c_aud_pc", length = 30)
  String pcAuditoria;

  @Column(name = "n_aud_ip", length = 15)
  String ipAuditoria;

  @Column(name = "c_aud_mcaddr", length = 17)
  String macAuditoria;
  
}
