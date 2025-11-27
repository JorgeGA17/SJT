package pe.gob.pj.votacion.domain.model.sijsuprema.query;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Accessors(fluent = true)
@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ObtenerApuntesProgramacionQuery {

    BigDecimal numeroUnico;
    Integer numeroIncidente;
    Integer numeroSentido;
    String codigoVocalUsuario;

}

