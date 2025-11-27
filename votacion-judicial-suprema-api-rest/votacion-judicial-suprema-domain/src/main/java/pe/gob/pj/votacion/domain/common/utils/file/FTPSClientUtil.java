package pe.gob.pj.votacion.domain.common.utils.file;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class FTPSClientUtil {


  public static FTPSClient connectToFTPS(String cuo, String server, int port, String user, String password) throws IOException {
    FTPSClient ftpsClient = new FTPSClient("TLS", false); // explícito
    ftpsClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

    ftpsClient.setConnectTimeout(20000);
    ftpsClient.setDataTimeout(20000);
    ftpsClient.setDefaultTimeout(20000);
    ftpsClient.setControlEncoding("UTF-8");

    ftpsClient.connect(server, port);
    log.info(cuo + " Conectado al FTPS: " + server + ":" + port);

    int replyCode = ftpsClient.getReplyCode();
    log.info(cuo + " Respuesta de FTPS tras conexión : " + replyCode);

    if (!FTPReply.isPositiveCompletion(replyCode)) {
      throw new IOException("No se pudo conectar al servidor FTPS: " + replyCode);
    }

    if (!ftpsClient.login(user, password)) {
      throw new IOException("No se pudo autenticar en el servidor FTPS");
    } else {
      ftpsClient.setFileType(FTP.BINARY_FILE_TYPE);
    }

    ftpsClient.execPBSZ(0);
    ftpsClient.execPROT("P");
    ftpsClient.enterLocalPassiveMode();

    log.info(cuo + " Autenticación FTPS exitosa");

    return ftpsClient;
  }
}