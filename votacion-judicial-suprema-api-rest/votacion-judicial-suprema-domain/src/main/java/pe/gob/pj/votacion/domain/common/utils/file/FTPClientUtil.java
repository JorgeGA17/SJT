package pe.gob.pj.votacion.domain.common.utils.file;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.Serializable;

@Slf4j
public  final class FTPClientUtil implements Serializable {
  private static final long serialVersionUID = 1L;

  public static FTPClient connectToFTP(String cuo, String ip, Integer puerto, String usuario, String clave) throws IOException {

    FTPClient ftps = new FTPClient();
    ftps.setConnectTimeout(60000);
    ftps.setDefaultTimeout(60000);
    ftps.setDataTimeout(60000);
    ftps.setControlEncoding("UTF-8");

    try {
      if(puerto>0) {
        ftps.connect(ip,puerto);
      }else {
        ftps.connect(ip);
      }
      log.info(cuo + " Conectado al FTP: " + ip + ":" + puerto);

      int reply = ftps.getReplyCode();
      log.info(cuo + " Respuesta de FTP tras conexión : " + reply);
      if (!FTPReply.isPositiveCompletion(reply)) {
        throw new IOException("No se pudo conectar al servidor FTP: código de respuesta " + reply);
      }

      if (!ftps.login(usuario, clave)) {
        //LOGGER.error(cuo.concat("Error al iniciar sesión en el servidor FTP: ip="+ip+" puerto="+puerto+" usuario="+usuario));
        throw new IOException("Error al iniciar sesión en el servidor FTP.");
        //return null;
      } else {
        ftps.setFileType(FTP.BINARY_FILE_TYPE); //
      }
      log.info(cuo + " Autenticación FTP exitosa");

      ftps.enterLocalPassiveMode();

      return ftps;

    } catch (IOException e) {
      if (ftps.isConnected()) {
        ftps.disconnect();
      }
      log.error(cuo.concat("Error al conectar con FTP ip="+ip+" puerto="+puerto+" usuario="+usuario+" Error= {}"),
          (e.getMessage() != null) ? e.getMessage() : "Sin mensaje específico", e);
      throw e;
    }
  }


  public static void closeFTPConnection(String cuo, FTPClient client) {
    if (client != null && client.isConnected()) {
      try {
        client.logout();
      } catch (IOException e) {
        log.error(cuo.concat("Error en logout de FTP: {}"),
            (e.getMessage() != null) ? e.getMessage() : "Sin mensaje específico", e);
      }
      try {
        client.disconnect();
      } catch (IOException e) {
        log.error(cuo.concat("Error en logout de FTP: {}"),
            (e.getMessage() != null) ? e.getMessage() : "Sin mensaje específico", e);
      }
    }
  }

}
