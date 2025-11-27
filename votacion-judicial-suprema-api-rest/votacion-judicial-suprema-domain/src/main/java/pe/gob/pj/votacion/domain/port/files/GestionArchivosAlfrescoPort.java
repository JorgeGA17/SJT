package pe.gob.pj.votacion.domain.port.files;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import pe.gob.pj.votacion.domain.model.properties.AlfrescoConfigData;

/**
 * @author oruizb
 * @version 2.0, 26/05/2025
 */
public interface GestionArchivosAlfrescoPort {

  /**
   * DTO que representa un documento descargado, incluyendo su contenido y metadatos.
   */
  record AlfrescoDocument(String nombreArchivo, String contentType, long contentLength,
      InputStream inputStream) {
  }

  boolean verificarConexion(AlfrescoConfigData config);

  Optional<String> crearCarpeta(String rutaPadre, String nombreCarpeta, AlfrescoConfigData config);

  void crearCarpetasRecursivo(String rutaCompleta, AlfrescoConfigData config);

  boolean existeCarpeta(String ruta, AlfrescoConfigData config);

  String subirArchivo(Map<String, Object> propiedades, InputStream inputStream,
      String nombreArchivo, String rutaDestino, String mimeType, AlfrescoConfigData config);

  String subirArchivo(Map<String, Object> propiedades, byte[] contenido, String nombreArchivo,
      String rutaDestino, String mimeType, AlfrescoConfigData config);

  Optional<AlfrescoDocument> descargarDocumentoPorUuid(String uuidDocumento,
      AlfrescoConfigData config);

  Optional<byte[]> descargarArchivoPorRuta(String rutaArchivo, AlfrescoConfigData config);

  boolean existeArchivo(String rutaArchivo, AlfrescoConfigData config);

  boolean eliminarArchivo(String rutaArchivo, AlfrescoConfigData config);

  List<String> obtenerContenidoCarpeta(String rutaCarpeta, AlfrescoConfigData config);

  String actualizarDocumento(Map<String, Object> propiedades, byte[] contenido,
      String nombreArchivo, String rutaDestino, String mimeType, AlfrescoConfigData config);

  Optional<String> obtenerRutaDocumento(String uuidDocumento, AlfrescoConfigData config);

  Optional<Map<String, Object>> ejecutarConsulta(String consulta, AlfrescoConfigData config);
  
}
