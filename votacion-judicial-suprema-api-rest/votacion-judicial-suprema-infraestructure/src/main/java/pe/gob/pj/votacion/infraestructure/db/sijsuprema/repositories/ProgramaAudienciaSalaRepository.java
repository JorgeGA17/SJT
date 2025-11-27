package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.ProgramaAudienciaSalaEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.ProgramaAudienciaSalaEntityPk;

public interface ProgramaAudienciaSalaRepository
    extends JpaRepository<ProgramaAudienciaSalaEntity, ProgramaAudienciaSalaEntityPk> {

  @Modifying
  @Query("""
      UPDATE ProgramaAudienciaSalaEntity p
      SET p.nSentido = :nSentido WHERE p.id.nUnico = :nUnico
      AND p.id.nIncidente = :nIncidente
      AND p.id.cInstancia = :cInstancia
      AND p.fechaIngreso = :fIngreso
      AND p.fProgramaAudiencia = :fProgramacion
      """)
  int actualizarProgramaAudienciaSala(@Param("nSentido") Integer nSentido,
      @Param("nUnico") BigDecimal nUnico, @Param("nIncidente") Integer nIncidente,
      @Param("cInstancia") String cInstancia, @Param("fIngreso") ZonedDateTime fIngreso,
      @Param("fProgramacion") ZonedDateTime fProgramacion);


}
