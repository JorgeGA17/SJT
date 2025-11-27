package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.SecuenciaDocumentoEntityPk;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "secuencia_documento", schema = EsquemaConstants.DBO)
public class SecuenciaDocumentoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    SecuenciaDocumentoEntityPk id;

    @Column(name = "n_num_sec")
    Integer numeroSecuencia;

    @Column(name = "n_num_digital", precision = 10, scale = 0, nullable = false)
    BigDecimal numeroDigital;

}
