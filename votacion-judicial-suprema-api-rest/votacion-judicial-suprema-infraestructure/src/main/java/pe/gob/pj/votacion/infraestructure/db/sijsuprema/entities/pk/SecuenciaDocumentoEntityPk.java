package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Embeddable
public class SecuenciaDocumentoEntityPk implements Serializable {

    static final long serialVersionUID = 1L;

    @Column(name = "n_ano", length = 4)
    String numeroAño;

    @Column(name = "c_sede", length = 4)
    String codigoSede;

    @Column(name = "c_org_jurisd", length = 3)
    String codigoOrganoJuris;

    @Column(name = "c_especialidad", length = 3)
    String codigoEspecialidad;

    @Column(name = "c_tipo", length = 20)
    String codigoTipo;
}
