package pe.gob.pj.votacion.domain.port.usecase.sijsuprema;

public interface FTPUseCasePort {
  byte[] cargarDocumentoFTP(String cuo, String ip, Integer puerto, String user, String clave, String ruta, String nombre);
}
