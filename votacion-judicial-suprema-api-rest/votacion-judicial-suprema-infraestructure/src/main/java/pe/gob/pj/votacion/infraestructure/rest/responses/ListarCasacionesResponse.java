package pe.gob.pj.votacion.infraestructure.rest.responses;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListarCasacionesResponse extends GlobalResponse implements Serializable {

    static final long serialVersionUID = 1L;

    List<CasacionResponse> data;

    public ListarCasacionesResponse(String codigoOperacion, List<CasacionResponse> data) {
        super(codigoOperacion);
        this.data = data;
    }

}