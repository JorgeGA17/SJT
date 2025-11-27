package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "persona_maestro", schema = EsquemaConstants.DBO)
public class PersonaMaestroEntity extends AuditoriaSupremaEntity implements Serializable {

  static final long serialVersionUID = 1L;

  @Id
  @Column(name = "n_id_persona", nullable = false)
  Integer nIdPersona;

  @Column(name = "x_ape_paterno", length = 200)
  String xApePaterno;

  @Column(name = "x_ape_materno", length = 100)
  String xApeMaterno;

  @Column(name = "x_nombres", length = 40)
  String xNombres;

  @Column(name = "l_activo", length = 1)
  String lActivo;

  @Column(name = "f_registro")
  LocalDateTime fRegistro;

  @Column(name = "c_colegio")
  String cColegio;

  @Column(name = "x_colegiatura", length = 15)
  String xColegiatura;

  @Column(name = "l_identificado_reniec", length = 1)
  String lIdentificadoReniec;

  @Column(name = "f_nacimiento")
  LocalDateTime fNacimiento;

  @Column(name = "c_tipo_persona")
  String cTipoPersona;

  @Column(name = "c_tipo_doc")
  String cTipoDoc;

  @Column(name = "x_doc_id", length = 20)
  String xDocId;

  @ManyToOne
  @JoinColumn(name = "c_colegio", referencedColumnName = "c_colegio", insertable = false,
      updatable = false)
  ColegioAbogadosEntity colegio;

  @ManyToOne
  @JoinColumn(name = "c_tipo_doc", referencedColumnName = "c_tipo", insertable = false,
      updatable = false)
  TipoDocumentoIdentidadEntity tipoDocumento;

  @ManyToOne
  @JoinColumn(name = "c_tipo_persona", referencedColumnName = "c_tipo_persona", insertable = false,
      updatable = false)
  TipoPersonaEntity tipoPersona;

}
