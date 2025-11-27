package pe.gob.pj.votacion.infraestructure.rest.responses;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrarResponse extends GlobalResponse implements Serializable {

    static final long serialVersionUID = 1L;

    RegistroResponse data;

    public RegistrarResponse(String codigoOperacion,
                             RegistroResponse data) {
        super(codigoOperacion);
        this.data = data;
    }

}