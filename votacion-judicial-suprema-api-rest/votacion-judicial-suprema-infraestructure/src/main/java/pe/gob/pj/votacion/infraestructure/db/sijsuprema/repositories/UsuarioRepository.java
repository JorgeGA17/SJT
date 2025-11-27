package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.UsuarioEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.UsuarioEntityPk;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UsuarioEntityPk> {

  Optional<UsuarioEntity> findByIdCodigoUsuario(String codigoUsuario);

}
