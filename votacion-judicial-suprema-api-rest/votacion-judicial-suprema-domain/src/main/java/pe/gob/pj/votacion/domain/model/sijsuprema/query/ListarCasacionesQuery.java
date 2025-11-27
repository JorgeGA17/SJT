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
public class ListarCasacionesQuery {

    String codigoDistrito;
    String codigoProvincia;
    String codigoInstancia;
    String fechaInicio;
    String fechaFin;

}