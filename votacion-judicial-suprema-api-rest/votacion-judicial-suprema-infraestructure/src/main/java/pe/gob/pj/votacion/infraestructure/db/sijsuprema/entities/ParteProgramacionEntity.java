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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ParteProgramacionEntityPk;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "parte_programacion", schema = EsquemaConstants.DBO)
public class ParteProgramacionEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  ParteProgramacionEntityPk id;

  @Column(name = "l_informe_oral", length = 1)
  String informeOral;

  @Column(name = "l_publicado_tablilla", length = 1)
  String publicadoTablilla;

  @Column(name = "f_registro")
  ZonedDateTime registro;

  @Column(name = "f_aud")
  ZonedDateTime fechaAuditoria;

  @Column(name = "b_aud", length = 1)
  String estadoAuditoria;

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

  @Column(name = "l_informe_hecho", length = 1, nullable = false)
  String informeHecho;

  @Column(name = "l_informe_oral_discordia", length = 1)
  String informeOralDiscordia;

  @Column(name = "l_informe_hecho_discordia", length = 1)
  String informeHechoDiscordia;

  @Column(name = "n_suborden")
  Integer suborden;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_programacion", referencedColumnName = "c_programacion", insertable = false,
      updatable = false)
  ProgramacionInstanciaEntity programacionInstancia;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(value = {
      @JoinColumn(name = "n_unico", referencedColumnName = "n_unico", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_incidente", referencedColumnName = "n_incidente", insertable = false,
          updatable = false),
      @JoinColumn(name = "n_secuencia", referencedColumnName = "n_secuencia", insertable = false,
          updatable = false)})
  ParteEntity parte;
  
}
