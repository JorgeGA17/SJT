package pe.gob.pj.votacion.infraestructure.files;

import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.model.properties.AlfrescoConfigData;
import pe.gob.pj.votacion.domain.port.files.GestionArchivosAlfrescoPort;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class GestionArchivosAlfrescoAdapter implements GestionArchivosAlfrescoPort {

  // --- MÉTODOS PÚBLICOS (Implementación del Puerto) ---

  @Override
  public Optional<AlfrescoDocument> descargarDocumentoPorUuid(String uuid,
      AlfrescoConfigData config) {
    Session session = createSession(config);
    try {
      String objectId = uuid;
      if (!uuid.contains("://")) {
        objectId = "workspace://SpacesStore/" + uuid;
      }
      CmisObject objeto = session.getObject(objectId);
      if (objeto instanceof Document documento) {
        ContentStream cs = documento.getContentStream();
        if (cs != null && cs.getStream() != null) {
          log.info("Documento encontrado [{}]: {} (Tipo: {})", 
              uuid, documento.getName(), documento.getType().getId());
          return Optional.of(new AlfrescoDocument(documento.getName(), cs.getMimeType(),
              cs.getLength(), cs.getStream()));
        }
      }
      log.warn("El objeto con UUID '{}' no es un documento o no tiene contenido.", uuid);
      return Optional.empty();
    } catch (CmisObjectNotFoundException e) {
      log.warn("No se encontró documento con UUID '{}'", uuid);
      return Optional.empty();
    } catch (Exception e) {
      log.error("Error descargando documento con UUID '{}': {}", uuid, e.getMessage(), e);
      return Optional.empty();
    } finally {
      session.clear();
    }
  }

  @Override
  public String subirArchivo(Map<String, Object> propiedades, InputStream inputStream,
      String nombreArchivo, String rutaDestino, String mimeType, AlfrescoConfigData config) {
    Session session = createSession(config);
    try {
      Folder carpetaDestino = obtenerCarpeta(rutaDestino, session);
      ContentStream contentStream =
          session.getObjectFactory().createContentStream(nombreArchivo, -1, mimeType, inputStream);
      Document documento =
          carpetaDestino.createDocument(propiedades, contentStream, VersioningState.MAJOR);
      log.info("Archivo '{}' subido a '{}'. UUID: {}", nombreArchivo, rutaDestino,
          documento.getId());
      return documento.getId();
    } catch (Exception e) {
      log.error("Error subiendo archivo '{}' a '{}'", nombreArchivo, rutaDestino, e);
      throw new RuntimeException("Error subiendo archivo", e);
    } finally {
      session.clear();
    }
  }

  @Override
  public String subirArchivo(Map<String, Object> propiedades, byte[] contenido,
      String nombreArchivo, String rutaDestino, String mimeType, AlfrescoConfigData config) {
    try (InputStream inputStream = new ByteArrayInputStream(contenido)) {
      return subirArchivo(propiedades, inputStream, nombreArchivo, rutaDestino, mimeType, config);
    } catch (IOException e) {
      throw new RuntimeException("Error al procesar contenido del archivo para subir", e);
    }
  }

  @Override
  public boolean verificarConexion(AlfrescoConfigData config) {
    Session session = createSession(config);
    try {
      return session.getRepositoryInfo() != null;
    } catch (Exception e) {
      log.error("Error verificando conexión con Alfresco en {}:{}", config.host(), config.puerto(),
          e);
      return false;
    } finally {
      session.clear();
    }
  }

  @Override
  public Optional<String> crearCarpeta(String rutaPadre, String nombreCarpeta,
      AlfrescoConfigData config) {
    Session session = createSession(config);
    try {
      Folder carpetaPadre = obtenerCarpeta(rutaPadre, session);
      Map<String, Object> propiedades =
          Map.of(PropertyIds.OBJECT_TYPE_ID, "cmis:folder", PropertyIds.NAME, nombreCarpeta);
      Folder nuevaCarpeta = carpetaPadre.createFolder(propiedades);
      log.info("Carpeta '{}' creada con ID: {}", nuevaCarpeta.getName(), nuevaCarpeta.getId());
      return Optional.of(nuevaCarpeta.getId());
    } catch (CmisContentAlreadyExistsException e) {
      log.warn("La carpeta '{}' ya existe en '{}'", nombreCarpeta, rutaPadre);
      return Optional.empty();
    } catch (Exception e) {
      log.error("Error creando carpeta '{}' en '{}'", nombreCarpeta, rutaPadre, e);
      throw new RuntimeException("Error al crear carpeta en Alfresco", e);
    } finally {
      session.clear();
    }
  }

  @Override
  public void crearCarpetasRecursivo(String rutaCompleta, AlfrescoConfigData config) {
    if (rutaCompleta == null || rutaCompleta.trim().isEmpty() || rutaCompleta.equals("/")) {
      log.warn("Se intentó crear carpetas para una ruta vacía o raíz. Operación omitida.");
      return;
    }
    Session session = createSession(config);
    try {
      String rutaNormalizada =
          rutaCompleta.startsWith("/") ? rutaCompleta.substring(1) : rutaCompleta;
      if (rutaNormalizada.endsWith("/")) {
        rutaNormalizada = rutaNormalizada.substring(0, rutaNormalizada.length() - 1);
      }

      Folder carpetaPadre = session.getRootFolder();

      for (String segmento : rutaNormalizada.split("/")) {
        if (segmento.isEmpty())
          continue;

        String rutaHijo = construirRutaCompleta(carpetaPadre.getPath(), segmento);

        try {
          CmisObject hijo = session.getObjectByPath(rutaHijo);
          if (hijo instanceof Folder) {
            carpetaPadre = (Folder) hijo;
          } else {
            throw new IllegalStateException("Un objeto con el nombre '" + segmento
                + "' en la ruta '" + carpetaPadre.getPath() + "' ya existe y no es una carpeta.");
          }
        } catch (CmisObjectNotFoundException e) {
          Map<String, Object> propiedades =
              Map.of(PropertyIds.OBJECT_TYPE_ID, "cmis:folder", PropertyIds.NAME, segmento);
          log.info("Creando carpeta recursiva '{}'", rutaHijo);
          carpetaPadre = carpetaPadre.createFolder(propiedades);
        }
      }
    } catch (Exception e) {
      log.error("Error creando carpetas recursivas para la ruta '{}'", rutaCompleta, e);
      throw new RuntimeException("Error al crear carpetas recursivas", e);
    } finally {
      session.clear();
    }
  }

  @Override
  public Optional<byte[]> descargarArchivoPorRuta(String rutaArchivo, AlfrescoConfigData config) {
    Session session = createSession(config);
    try {
      CmisObject objeto = session.getObjectByPath(rutaArchivo);
      if (objeto instanceof Document documento) {
        return Optional.of(extraerContenidoDocumento(documento));
      }
      return Optional.empty();
    } catch (CmisObjectNotFoundException e) {
      log.warn("No se encontró archivo en la ruta '{}'", rutaArchivo);
      return Optional.empty();
    } catch (Exception e) {
      log.error("Error al descargar archivo por ruta '{}'", rutaArchivo, e);
      throw new RuntimeException("Error al descargar archivo por ruta", e);
    } finally {
      session.clear();
    }
  }

  @Override
  public boolean existeCarpeta(String ruta, AlfrescoConfigData config) {
    Session session = createSession(config);
    try {
      CmisObject objeto = session.getObjectByPath(ruta);
      return objeto instanceof Folder;
    } catch (CmisObjectNotFoundException e) {
      return false;
    } finally {
      session.clear();
    }
  }

  @Override
  public boolean existeArchivo(String rutaArchivo, AlfrescoConfigData config) {
    Session session = createSession(config);
    try {
      CmisObject objeto = session.getObjectByPath(rutaArchivo);
      return objeto instanceof Document;
    } catch (CmisObjectNotFoundException e) {
      return false;
    } finally {
      session.clear();
    }
  }

  @Override
  public boolean eliminarArchivo(String rutaArchivo, AlfrescoConfigData config) {
    Session session = createSession(config);
    try {
      CmisObject objeto = session.getObjectByPath(rutaArchivo);
      objeto.delete(true); // true para borrar todas las versiones
      log.info("Archivo '{}' eliminado.", rutaArchivo);
      return true;
    } catch (CmisObjectNotFoundException e) {
      log.warn("No se encontró archivo para eliminar: '{}'", rutaArchivo);
      return false;
    } catch (Exception e) {
      log.error("Error eliminando archivo '{}'", rutaArchivo, e);
      throw new RuntimeException("Error al eliminar archivo", e);
    } finally {
      session.clear();
    }
  }

  @Override
  public List<String> obtenerContenidoCarpeta(String rutaCarpeta, AlfrescoConfigData config) {
    Session session = createSession(config);
    try {
      Folder carpeta = obtenerCarpeta(rutaCarpeta, session);
      List<String> contenido = new ArrayList<>();
      for (CmisObject hijo : carpeta.getChildren()) {
        contenido.add(hijo.getName());
      }
      return contenido;
    } catch (Exception e) {
      log.error("Error obteniendo contenido de la carpeta '{}'", rutaCarpeta, e);
      throw new RuntimeException("Error obteniendo contenido de carpeta", e);
    } finally {
      session.clear();
    }
  }

  @Override
  public String actualizarDocumento(Map<String, Object> propiedades, byte[] contenido,
      String nombreArchivo, String rutaDestino, String mimeType, AlfrescoConfigData config) {
    Session session = createSession(config);
    try {
      String rutaCompleta = construirRutaCompleta(rutaDestino, nombreArchivo);
      Document documento = (Document) session.getObjectByPath(rutaCompleta);

      boolean esVersionable =
          documento.getAllowableActions().getAllowableActions().contains(Action.CAN_CHECK_OUT);
      if (esVersionable) {
        return actualizarDocumentoVersionable(documento, contenido, nombreArchivo, mimeType,
            session);
      } else {
        try (InputStream inputStream = new ByteArrayInputStream(contenido)) {
          ContentStream contentStream = session.getObjectFactory()
              .createContentStream(nombreArchivo, contenido.length, mimeType, inputStream);
          documento.setContentStream(contentStream, true, true);
          log.info("Documento '{}' actualizado (sin versionado)", nombreArchivo);
          return documento.getId();
        }
      }
    } catch (Exception e) {
      log.error("Error actualizando documento '{}'", nombreArchivo, e);
      throw new RuntimeException("Error actualizando documento", e);
    } finally {
      session.clear();
    }
  }

  @Override
  public Optional<String> obtenerRutaDocumento(String uuidDocumento, AlfrescoConfigData config) {
    Session session = createSession(config);
    try {
      CmisObject objeto = session.getObject(uuidDocumento);
      if (objeto instanceof Document documento) {
        List<String> paths = documento.getPaths();
        return paths.isEmpty() ? Optional.empty() : Optional.of(paths.get(0));
      }
      return Optional.empty();
    } catch (CmisObjectNotFoundException e) {
      return Optional.empty();
    } finally {
      session.clear();
    }
  }

  @Override
  public Optional<Map<String, Object>> ejecutarConsulta(String consulta,
      AlfrescoConfigData config) {
    Session session = createSession(config);
    try {
      ItemIterable<QueryResult> resultados = session.query(consulta, false);
      Map<String, Object> propiedades = new HashMap<>();

      for (QueryResult resultado : resultados) {
        for (PropertyData<?> propiedad : resultado.getProperties()) {
          propiedades.put(propiedad.getQueryName(), propiedad.getFirstValue());
        }
        break;
      }
      return propiedades.isEmpty() ? Optional.empty() : Optional.of(propiedades);
    } catch (Exception e) {
      log.error("Error ejecutando consulta CMIS: {}", consulta, e);
      throw new RuntimeException("Error al ejecutar consulta CMIS", e);
    } finally {
      session.clear();
    }
  }

  // --- MÉTODOS PRIVADOS AUXILIARES ---

  private Session createSession(AlfrescoConfigData config) {
    String atomPubUrl =
        "4.2".equals(config.version()) ? "alfresco/api/-default-/public/cmis/versions/1.0/atom"
            : "alfresco/cmisatom";
    String baseUrl = String.format("http://%s:%s/%s", config.host(), config.puerto(), atomPubUrl);

    Map<String, String> parameter = new HashMap<>();
    parameter.put(SessionParameter.ATOMPUB_URL, baseUrl);
    parameter.put(SessionParameter.USER, config.usuario());
    parameter.put(SessionParameter.PASSWORD, config.clave());
    parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
    parameter.put(SessionParameter.CONNECT_TIMEOUT, "10000");
    parameter.put(SessionParameter.READ_TIMEOUT, "60000");

    SessionFactory factory = SessionFactoryImpl.newInstance();
    log.debug("Creando nueva sesión CMIS para {}", baseUrl);
    return factory.getRepositories(parameter).get(0).createSession();
  }

  private Folder obtenerCarpeta(String ruta, Session session) {
    try {
      CmisObject objeto = session.getObjectByPath(ruta);
      if (objeto instanceof Folder) {
        return (Folder) objeto;
      }
      throw new IllegalStateException("El objeto en la ruta '" + ruta + "' no es una carpeta.");
    } catch (CmisObjectNotFoundException e) {
      throw new IllegalStateException("No se encontró la carpeta en la ruta: " + ruta, e);
    }
  }

  private byte[] convertirInputStreamABytes(InputStream inputStream) throws IOException {
    return inputStream.readAllBytes();
  }

  private byte[] extraerContenidoDocumento(Document documento) throws IOException {
    ContentStream contentStream = documento.getContentStream();
    if (contentStream != null && contentStream.getStream() != null) {
      try (InputStream is = contentStream.getStream()) {
        byte[] contenido = convertirInputStreamABytes(is);
        log.info("Contenido extraído correctamente para: {}", documento.getName());
        return contenido;
      }
    } else {
      log.warn("El documento '{}' no tiene contenido", documento.getName());
      return new byte[0];
    }
  }

  private String construirRutaCompleta(String rutaCarpeta, String nombreArchivo) {
    if (rutaCarpeta == null || nombreArchivo == null)
      return "";
    if (rutaCarpeta.endsWith("/")) {
      return rutaCarpeta + nombreArchivo;
    }
    return rutaCarpeta + "/" + nombreArchivo;
  }

  private String actualizarDocumentoVersionable(Document documento, byte[] contenido,
      String nombreArchivo, String mimeType, Session session) {
    ObjectId idCheckOut = null;
    try {
      idCheckOut = documento.checkOut();
      Document documentoTrabajo = (Document) session.getObject(idCheckOut);

      try (InputStream inputStream = new ByteArrayInputStream(contenido)) {
        ContentStream contentStream = session.getObjectFactory().createContentStream(nombreArchivo,
            contenido.length, mimeType, inputStream);
        ObjectId idActualizado =
            documentoTrabajo.checkIn(false, null, contentStream, "Versión actualizada");
        log.info("Documento '{}' actualizado con nueva versión.", nombreArchivo);
        return idActualizado.getId();
      }
    } catch (Exception e) {
      if (idCheckOut != null) {
        try {
          Document docToCancel = (Document) session.getObject(idCheckOut);
          docToCancel.cancelCheckOut();
          log.warn("Se ha cancelado el check-out del documento '{}' debido a un error.",
              nombreArchivo);
        } catch (Exception cancelEx) {
          log.error("Error crítico al intentar cancelar el check-out del documento '{}'",
              nombreArchivo, cancelEx);
        }
      }
      log.error("Error en actualización versionable de '{}'", nombreArchivo, e);
      throw new RuntimeException("Error actualizando documento versionable", e);
    }
  }
  
}
