package pe.gob.pj.votacion.infraestructure.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPSClient;
import pe.gob.pj.votacion.domain.common.utils.file.FTPClientUtil;
import pe.gob.pj.votacion.domain.common.utils.file.FTPSClientUtil;
import pe.gob.pj.votacion.domain.common.utils.file.FtpInputStreamWrapper;
import pe.gob.pj.votacion.domain.common.utils.file.FtpOutputStreamWrapper;
import pe.gob.pj.votacion.domain.port.files.FtpPort;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

/**
 * 
 * @author oruizb
 *
 */
@Slf4j
@Component("ftpPort")
public class FtpAdapter implements FtpPort {

  FTPClient ftp = new FTPClient();

  @Override
  public void iniciarSesion(String cuo, String ip, Integer puerto, String usuario, String clave)
      throws Exception {
    // try {
    // Conectar al servidor FTP
    ftp.connect(ip, puerto);
    // Iniciar sesión en el servidor ftp
    ftp.login(usuario, clave);
    // } catch (SocketException ex) {
    // log.error("{} [{}|{}] Se produjo un error con la conexión al ftp.", cuo, ip, usuario);
    // } catch (Exception e) {
    // log.error("{} [{}|{}] Ocurrió un error inesperado al conectarse.", cuo, ip);
    // }
  }

  @Override
  public void finalizarSession(String cuo) throws Exception {
    // try {
    if (ftp.isConnected()) {
      ftp.logout();
      ftp.disconnect();
    }
    // } catch (IOException e) {
    // e.printStackTrace();
    // log.error("{} [{}|{}] Ocurrió un error inesperado al desconectarse del ftp.", cuo);
    // }
  }

  @Override
  public boolean uploadFileFTP(String cuo, String srcFtpPDF, InputStream inputStream, String desc)
      throws Exception {
    // Long tiempoInicial = System.currentTimeMillis();
    boolean result = false;
    // try {
    ftp.setFileType(FTP.BINARY_FILE_TYPE);
    ftp.enterLocalPassiveMode();
    int reply = ftp.getReplyCode();
    if (FTPReply.isPositiveCompletion(reply)) {
      // Crear directorio, en caso es necesario
      File fileFtp = new File(srcFtpPDF);
      String ftpDirPath = fileFtp.getParent();
      for (String dir : ftpDirPath.split("/")) {
        if (!dir.isEmpty()) {
          if (!ftp.changeWorkingDirectory(dir)) {
            if (!ftp.makeDirectory(dir)) {
              throw new IOException("No se puede crear el directorio '" + dir + "'.  error='"
                  + ftp.getReplyString() + "'");
            }
            if (!ftp.changeWorkingDirectory(dir)) {
              throw new IOException("No se puede cambiar al directorio recién creado '" + dir
                  + "'.  error='" + ftp.getReplyString() + "'");
            }
          }
        }
      }
      OutputStream outputStream = ftp.storeFileStream(srcFtpPDF);
      if (outputStream != null) {
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = inputStream.read(bytesIn)) != -1) {
          outputStream.write(bytesIn, 0, read);
        }
        inputStream.close();
        outputStream.close();

        result = ftp.completePendingCommand();
        log.info("{} Se guardo el archivo {} de manera correcta.", cuo, srcFtpPDF);
      } else {
        log.info(
            "{} No se pudo guardar el documento {}. La ubiación en donde se quiere guardar no se encontró",
            cuo, srcFtpPDF);
      }

    } else {
      log.info("{}  No se pudo validar la conexión al FTP, para subir el documento {}", cuo,
          srcFtpPDF);
      ftp.disconnect();
    }

    // } catch (IOException e) {
    // log.error("{} No se pudo subir el archivo {}", cuo, srcFtpPDF);
    // e.printStackTrace();
    // } catch (Exception ex) {
    // log.error("{} Ocurrió un error inesperado al subir el archivo {}", cuo, srcFtpPDF);
    // }
    return result;
  }

  @Override
  public byte[] downloadFileBytes(String cuo, String ruta) throws Exception {
    byte[] bytes = null;
    // try {
    ftp.enterLocalPassiveMode();
    ftp.setFileType(FTP.BINARY_FILE_TYPE);
    int reply = ftp.getReplyCode();
    if (FTPReply.isPositiveCompletion(reply)) {

      InputStream inputStream = ftp.retrieveFileStream(ruta);
      if (inputStream != null) {
        bytes = IOUtils.toByteArray(inputStream);
        inputStream.close();
        log.info("{} Se descargo el archivo {} de manera correcta.", cuo, ruta);
      } else {
        log.warn("{} El archivo {} no se encontró en la ruta indicada.", cuo, ruta);
      }
    } else {
      log.error("{}  No se pudo validar la conexión al FTP, para descargar el documento {}", cuo,
          ruta);
      ftp.disconnect();
    }
    // } catch (IOException e) {
    // log.error("{} No se pudo descargar el archivo {}. (IOException : {})", cuo, ruta, e);
    // } catch (Exception e) {
    // log.error("{} Ocurrió un error inesperado al descargar el archivo {}. (Exception : {})", cuo,
    // ruta, e);
    // }
    return bytes;
  }

  @Override
  public InputStream downloadFileStream(String cuo, String ruta) throws Exception {
    InputStream inputStream = null;
    // try {
    ftp.enterLocalPassiveMode();
    ftp.setFileType(FTP.BINARY_FILE_TYPE);
    int reply = ftp.getReplyCode();
    if (FTPReply.isPositiveCompletion(reply)) {
      inputStream = ftp.retrieveFileStream(ruta);
      if (inputStream != null) {
        log.info("{} Se obtuvo el flujo de datos del archivo {} de manera correcta.", cuo, ruta);
      } else {
        log.warn("{} El archivo {} no se encontró en la ruta indicada.", cuo, ruta);
      }
    } else {
      log.error("{}  No se pudo validar la conexión al FTP, para descargar el documento {}", cuo,
          ruta);
      ftp.disconnect();
    }
    // } catch (IOException e) {
    // log.error("{} No se pudo descargar el archivo {}. (IOException : {})", cuo, ruta, e);
    // } catch (Exception e) {
    // log.error("{} Ocurrió un error inesperado al descargar el archivo {}. (Exception : {})", cuo,
    // ruta, e);
    // }
    return inputStream;
  }



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
