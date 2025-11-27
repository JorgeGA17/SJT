package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "cargo_laboral", schema = EsquemaConstants.DBO)
public class CargoLaboralEntity implements Serializable{
	
	static final long serialVersionUID = 1L;

	@Id
    @Column(name = "c_cargo_laboral", length = 2, nullable = false)
    String cCargoLaboral;

    @Column(name = "x_desc_cargo", length = 50)
    String xDescCargo;

    @Column(name = "l_activo", length = 1)
    String lActivo;
}
