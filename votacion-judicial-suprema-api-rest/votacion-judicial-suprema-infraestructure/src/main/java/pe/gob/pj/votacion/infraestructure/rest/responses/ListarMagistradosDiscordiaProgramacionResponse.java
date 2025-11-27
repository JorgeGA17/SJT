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
public class ListarMagistradosDiscordiaProgramacionResponse extends GlobalResponse implements Serializable {

    static final long serialVersionUID = 1L;

    List<MagistradoDiscordiaProgramacionResponse> data;

    public ListarMagistradosDiscordiaProgramacionResponse(String codigoOperacion,
                                                 List<MagistradoDiscordiaProgramacionResponse> data) {
        super(codigoOperacion);
        this.data = data;
    }

}
