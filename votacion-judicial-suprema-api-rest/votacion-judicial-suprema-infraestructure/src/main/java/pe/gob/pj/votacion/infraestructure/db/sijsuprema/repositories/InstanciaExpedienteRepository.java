package pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.pj.votacion.domain.model.sijsuprema.DocumentoDigitalProgramacion;
import pe.gob.pj.votacion.domain.model.sijsuprema.DocumentoProgramacion;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.InstanciaExpedienteEntity;
import pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities.pk.InstanciaExpedienteEntityPk;

public interface InstanciaExpedienteRepository
    extends JpaRepository<InstanciaExpedienteEntity, InstanciaExpedienteEntityPk> {

  @Modifying
  @Query("""
      UPDATE InstanciaExpedienteEntity ie
      SET ie.lConformado = "S", ie.fechaAuditoria  =:fAud, ie.bitacoraAuditoria=:bAud, ie.usuarioAuditoria =:cAudUid
      WHERE ie.id.numeroUnico = :nUnico
      AND ie.id.numeroIncidente = :nIncidente
      AND ie.id.codigoInstancia = :cInstancia
      AND ie.id.fechaIngreso = :fIngreso
      """)
  int marcarConformado(@Param("nUnico") BigDecimal nUnico, @Param("nIncidente") Integer nIncidente,
      @Param("cInstancia") String cInstancia, @Param("fIngreso") ZonedDateTime fIngreso,
      @Param("fAud") ZonedDateTime fAud, @Param("bAud") String bAud,
      @Param("cAudUid") String cAudUid);


  @Query("""
      SELECT ie
      FROM InstanciaExpedienteEntity ie
      WHERE ie.id.numeroUnico = :numeroUnico
      AND ie.id.numeroIncidente = :numeroIncidente
      AND ie.id.codigoInstancia = :codigoInstancia
      AND ie.id.codigoProvincia = :codigoProvincia
      AND ie.id.codigoDistrito = :codigoDistrito
      AND ie.id.fechaIngreso = :fechaIngreso
      """)
  Optional<InstanciaExpedienteEntity> findInstanciaExpediente(
      @Param("codigoDistrito") String codigoDistrito,
      @Param("codigoProvincia") String codigoProvincia,
      @Param("codigoInstancia") String codigoInstancia,
      @Param("numeroUnico") BigDecimal numeroUnico,
      @Param("numeroIncidente") Integer numeroIncidente,
      @Param("fechaIngreso") ZonedDateTime fechaIngreso);

  @Query("""
    SELECT new pe.gob.pj.votacion.domain.model.sijsuprema.DocumentoDigitalProgramacion(
        dd.id.nDocumento,
        dd.lTipoDoc,
        t.xTipoDoc,
        dd.xNombreArchivo,
        COALESCE(dd.xDescripcion, dd.xNombreArchivo),
        dd.xRutaArchivo,
        s.xIp,
        s.cClave,
        s.cUsuario
    )
    FROM InstanciaExpedienteEntity ie
    JOIN ie.expedienteEntity e
    JOIN ExpedienteEstadoEntity ee
        ON ee.id.numeroUnico = ie.id.numeroUnico
        AND ee.id.numeroIncidente = ie.id.numeroIncidente
        AND ee.id.fechaIngreso = ie.id.fechaIngreso
        AND ee.id.codigoDistrito = ie.id.codigoDistrito
        AND ee.id.codigoProvincia = ie.id.codigoProvincia
        AND ee.id.codigoInstancia = ie.id.codigoInstancia
        AND ee.ultimo = 'S'
    JOIN DocumentoDigitalEntity dd
        ON dd.expediente.id.nUnico = ie.id.numeroUnico
        AND dd.expediente.id.nIncidente = ie.id.numeroIncidente
    LEFT JOIN DocumentoDigitalEscritoEntity dde
        ON dde.id.cSede = dd.id.cSede 
        AND dde.id.nDocumento = dd.id.nDocumento
    JOIN MaeTipoDocumentoDigitalEntity t
        ON t.lTipoDoc = dd.lTipoDoc
    JOIN ServidorFtpEntity s
        ON s.id.nCorrelativoFtp = dd.nCorrelativoFtp
       AND s.id.cSede = dd.cSedeFtp
       AND s.id.nItem = dd.nServicioFtp
    WHERE ie.id.numeroUnico = :nUnico
      AND ie.id.numeroIncidente = :nIncidente
      AND t.lTipoDoc IN ('EXP', 'V', 'ANX')
""")
  List<DocumentoDigitalProgramacion> findDocumentosDigitalesByUnico(
      @Param("nUnico") BigDecimal nUnico, @Param("nIncidente") Integer nIncidente);

}
