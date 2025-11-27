package pe.gob.pj.votacion.domain.model.sijsuprema.query;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Accessors(fluent = true)
@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListarFallosQuery {

    String codigoEspecialidad;
    String codigoAbreviatura;
    String codigoMotivoIngreso;

}
