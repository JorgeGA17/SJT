package pe.gob.pj.votacion.domain.common.utils.file;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Service
public class  FTPAdapter  {

  public InputStream download(String cuo, String ip, Integer puerto, String usuario, String clave, String ruta) throws IOException {
    log.info(cuo + " Iniciando downloadStream params: " + ip + " ,"+ puerto + " ,"+ usuario + " ,"+ clave + " ,"+ ruta);

    FTPClient client = FTPClientUtil.connectToFTP(cuo,ip,puerto,usuario,clave);
    InputStream in = client.retrieveFileStream(ruta);
    if (in == null){
      throw new IOException("No se pudo descargar archivo");
    } else {
      log.info(cuo + " Descarga completa de archivo");
    }

    return new FtpInputStreamWrapper(cuo,client, in);
  }

  public OutputStream uploadStreamFTP(String cuo, String ip, Integer puerto, String usuario, String clave, String ruta) throws IOException {
    log.info(cuo + " Iniciando uploadStream params: " + ip + " ,"+ puerto + " ,"+ usuario + " ,"+ clave + " ,"+ ruta);

    FTPClient client = FTPClientUtil.connectToFTP(cuo,ip,puerto,usuario,clave);
    OutputStream out = client.storeFileStream(ruta);
    if (out == null) {
      log.error(cuo + "No se pudo subir archivo al FTP. Código: " + client.getReplyCode());
      throw new IOException("No se pudo subir archivo al FTP");
    } else {
      log.info(cuo + " Subida completa de archivo");
    }

    return new FtpOutputStreamWrapper(cuo,client, out);
  }

  public OutputStream uploadStreamFTPS(String cuo, String ip, Integer puerto, String usuario, String clave, String ruta) throws IOException {
    log.info(cuo + " Iniciando uploadStream FTPS params: " + ip + " ," + puerto + " ," + usuario + " ," + clave + " ," + ruta);

    FTPSClient ftpsClient = FTPSClientUtil.connectToFTPS(cuo, ip, puerto, usuario, clave);
    OutputStream out = ftpsClient.storeFileStream(ruta);
    if (out == null) {
      log.error(cuo + "No se pudo subir archivo al FTPS. Código: " + ftpsClient.getReplyCode());
      throw new IOException("No se pudo subir archivo al FTPS");
    } else {
      log.info(cuo + " Subida completa de archivo");
    }
    return new FtpOutputStreamWrapper(cuo, ftpsClient, out);
  }

}