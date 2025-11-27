package pe.gob.pj.votacion.usecase.sijsuprema;


import java.io.InputStream;
import org.springframework.stereotype.Service;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.common.utils.ProjectUtils;
import pe.gob.pj.votacion.domain.common.utils.file.FTPAdapter;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.FTPUseCasePort;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FTPUseCaseAdapter implements FTPUseCasePort {

  FTPAdapter transferencia;

  @Override
  public byte[] cargarDocumentoFTP(String cuo, String ip, Integer puerto, String user, String clave,
      String ruta, String nombre) {

    byte[] documento = null;

    log.info(cuo + " Iniciando cargarDocumentoFTP : ");

    InputStream in = null;
    try {
      String rutaOrigen = ruta.concat("/").concat(nombre);
      in = transferencia.download(cuo, ip, puerto, user, clave, rutaOrigen);
      documento = ProjectUtils.inputStreamToBytes(in);
    } catch (Exception e) {
      log.error(cuo + " Error en la transferencia de archivos : ", e);
    }

    return documento;
  }
}
