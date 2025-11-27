package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.domain.model.sijsuprema.Fallo;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.FalloEntity;

import java.util.List;

public interface FalloRepository extends JpaRepository<FalloEntity, Integer> {

    @Query("""
        SELECT DISTINCT new pe.gob.pj.votacion.domain.model.sijsuprema.Fallo(f.codigoFallo, f.descripcion)
        FROM FalloEntity f
        LEFT JOIN CfgTipoAudienciaFalloEntity cfg ON cfg.falloEntity.codigoFallo = f.codigoFallo
        WHERE
            (
                :especialidad = 'DC' AND :motivoIngreso IN ('018','034')
                AND f.codigoFallo IN (91,1821,120,880,1,157,44,96,113,1727,17,127)
            )
            OR (
                :especialidad = 'DC' AND :motivoIngreso = '780' AND :abrev = 'C'
                AND f.codigoFallo IN (157,17,3,81,113,880,1727,127,44,291)
            )
             OR (
                :especialidad = 'DC' AND :motivoIngreso = '780' AND :abrev <> 'C'
                AND f.codigoFallo IN (113,1,2,69,291,880,127,1727)
            )
            OR (
                :especialidad = 'DC' AND :motivoIngreso = '709'
                AND f.codigoFallo IN (78,880,1727)
            )
            OR (
                :especialidad = 'DC' AND :motivoIngreso = '055'
                AND f.codigoFallo IN (64,65,880,1727)
            )
             OR (
                :especialidad = 'DC' AND :motivoIngreso = 'ZZK'
                AND f.codigoFallo IN (81,3,1,2,17,127,1727)
            )
            OR (
                :especialidad = 'DC' AND :motivoIngreso NOT IN ('018','034','780','709','055','ZZK')
                AND f.codigoFallo IN (1,2,17,3,880,1727)
            )
        OR (
            :especialidad = 'CI'
            AND (
                (
                    :abrev = 'VC'
                    AND cfg.id.cEspecialidad = :especialidad
                )
                OR (
                    :abrev IN ('FC','VF')
                    AND cfg.id.cEspecialidad = :especialidad
                    AND cfg.id.cMotivoIngreso = :motivoIngreso
                    AND (
                        (:abrev = 'FC' AND cfg.id.nTipoAudiencia = 8)
                        OR (:abrev = 'VF' AND cfg.id.nTipoAudiencia = 10)
                    )
                    AND cfg.lActivo = 'S'
                )
            )
        )
    ORDER BY f.descripcion ASC
""")
    List<Fallo> listarFallos(@Param(value = "especialidad") String codigoEspecialidad,
                             @Param(value = "abrev") String codigoAbreviatura,
                             @Param(value = "motivoIngreso") String codigoMotivoIngreso);
}
