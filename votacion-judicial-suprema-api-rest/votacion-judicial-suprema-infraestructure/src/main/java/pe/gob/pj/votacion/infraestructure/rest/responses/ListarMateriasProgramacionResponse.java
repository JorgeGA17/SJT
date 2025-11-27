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
public class ListarMateriasProgramacionResponse extends GlobalResponse implements Serializable {

    static final long serialVersionUID = 1L;

    List<MateriaProgramacionResponse> data;

    public ListarMateriasProgramacionResponse(String codigoOperacion,
                                                 List<MateriaProgramacionResponse> data) {
        super(codigoOperacion);
        this.data = data;
    }

}
