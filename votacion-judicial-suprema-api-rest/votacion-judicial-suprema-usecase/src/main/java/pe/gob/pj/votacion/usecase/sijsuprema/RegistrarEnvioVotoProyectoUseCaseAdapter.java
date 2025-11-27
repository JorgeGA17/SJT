package pe.gob.pj.votacion.usecase.sijsuprema;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.common.utils.ProjectUtils;
import pe.gob.pj.votacion.domain.exceptions.general.CargaArchivoAlfrescoFallidoException;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.properties.AlfrescoConfigData;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarEnvioProyectoCommand;
import pe.gob.pj.votacion.domain.port.files.GestionArchivosAlfrescoPort;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.ProyectosWritePersistencePort;
import pe.gob.pj.votacion.domain.port.properties.AlfrescoConfigurationPort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.RegistrarEnvioVotoProyectoUseCasePort;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RegistrarEnvioVotoProyectoUseCaseAdapter
    implements RegistrarEnvioVotoProyectoUseCasePort {

  GestionArchivosAlfrescoPort cmisPort;
  AlfrescoConfigurationPort alfrescoConfigurationPort;
  ProyectosWritePersistencePort proyectosWritePersistencePort;

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRES_NEW, readOnly = false,
      rollbackFor = {Exception.class, SQLException.class})
  public void registrarEnvioVoto(PeticionServicios peticion,
      RegistrarEnvioProyectoCommand command) {

    AlfrescoConfigData alfrescoConfig = alfrescoConfigurationPort.getJurisprudenciaConfig();

    try {

      String ruta = construirRutaEvidencia(LocalDateTime.now());
      cmisPort.crearCarpetasRecursivo(ruta, alfrescoConfig);

      String extension = ProjectUtils.extensionArchivo(command.nombreDocumento());
      String mimeType = obtenerMimeType(extension);

      Map<String, Object> properties = new HashMap<>();
      properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
      properties.put(PropertyIds.NAME, command.nombreDocumento());
      properties.put(PropertyIds.CONTENT_STREAM_MIME_TYPE, mimeType);
      properties.put(PropertyIds.CREATED_BY, alfrescoConfig.usuario());

      String uuid = cmisPort.subirArchivo(properties, command.archivo(), command.nombreDocumento(),
          ruta, mimeType, alfrescoConfig);

      command.extension(extension);
      command.uuid(uuid);

    } catch (Exception e) {
      throw new CargaArchivoAlfrescoFallidoException(e.getMessage());
    }

    proyectosWritePersistencePort.registrarEnvioVoto(peticion, command);
  }

  private String construirRutaEvidencia(LocalDateTime fechaActual) {
    return String.format("/JURISPRUDENCIA/%04d/%02d/%02d/%02d", fechaActual.getYear(),
        fechaActual.getMonthValue(), fechaActual.getDayOfMonth(), fechaActual.getHour());
  }

  private String obtenerMimeType(String extension) {
    return switch (extension.toLowerCase()) {
      case "pdf" -> "application/pdf";
      case "doc" -> "application/msword";
      case "xls" -> "application/vnd.ms-excel";
      case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
      case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      default -> "application/octet-stream";
    };
  }


}
