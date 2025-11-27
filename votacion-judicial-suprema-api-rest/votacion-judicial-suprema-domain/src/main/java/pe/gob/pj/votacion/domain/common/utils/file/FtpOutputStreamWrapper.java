package pe.gob.pj.votacion.domain.common.utils.file;


import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.OutputStream;

public class FtpOutputStreamWrapper extends OutputStream {

  private final String cuo;
  private final FTPClient client;
  private final OutputStream delegate;
  private boolean closed = false;

  public FtpOutputStreamWrapper(String cuo, FTPClient client, OutputStream delegate) {
    this.cuo = cuo;
    this.client = client;
    this.delegate = delegate;
  }

  @Override
  public void write(int b) throws IOException {
    delegate.write(b);
  }

  @Override
  public void write(byte[] b) throws IOException {
    delegate.write(b);
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    delegate.write(b, off, len);
  }

  @Override
  public void flush() throws IOException {
    delegate.flush();
  }

  @Override
  public void close() throws IOException {
    if (closed) return;

    try {
      delegate.close();

      boolean completed = client.completePendingCommand();
      if (!completed) {
        throw new IOException("No se completó correctamente la transferencia al FTP/FTPS.");
      }
    } finally {
      try {
        client.logout();
      } catch (IOException e) {
        // opcional: log
      }
      try {
        client.disconnect();
      } catch (IOException e) {
        // opcional: log
      }
      closed = true;
    }
  }
}