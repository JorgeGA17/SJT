package pe.gob.pj.votacion.domain.common.utils.file;

import org.apache.commons.net.ftp.FTPClient;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FtpInputStreamWrapper extends FilterInputStream {
  private final String cuo;
  private final FTPClient client;
  public FtpInputStreamWrapper(String cuo, FTPClient client, InputStream in) {
    super(in);
    this.client = client;
    this.cuo = cuo;
  }

  @Override
  public void close() throws IOException {
    super.close();
    client.completePendingCommand();
    FTPClientUtil.closeFTPConnection(cuo,client);
  }
}