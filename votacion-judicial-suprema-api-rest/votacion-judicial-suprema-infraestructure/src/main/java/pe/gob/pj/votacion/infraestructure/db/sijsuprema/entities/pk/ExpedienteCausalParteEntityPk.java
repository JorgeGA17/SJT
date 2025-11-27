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
public class ExpedienteCausalParteEntityPk implements Serializable {

    static final long serialVersionUID = 1L;

    @Column(name = "n_unico", nullable = false)
    Long nUnico;

    @Column(name = "n_incidente", nullable = false)
    Integer nIncidente;

    @Column(name = "c_programacion", length = 10, nullable = false)
    String cProgramacion;

    @Column(name = "n_secuencia", nullable = false)
    Integer nSecuencia;

    @Column(name = "n_secuencia_parte", nullable = false)
    Integer nSecuenciaParte;
}
