package pe.gob.pj.votacion.usecase.sijsuprema;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.domain.common.enums.Flag;
import pe.gob.pj.votacion.domain.common.utils.ProjectUtils;
import pe.gob.pj.votacion.domain.exceptions.general.CaptchaException;
import pe.gob.pj.votacion.domain.exceptions.negocio.CredencialesSinCoincidenciaException;
import pe.gob.pj.votacion.domain.exceptions.negocio.UsuarioSinPerfilAsignadoException;
import pe.gob.pj.votacion.domain.model.auditoriageneral.PeticionServicios;
import pe.gob.pj.votacion.domain.model.sijsuprema.Usuario;
import pe.gob.pj.votacion.domain.model.sijsuprema.query.IniciarSesionQuery;
import pe.gob.pj.votacion.domain.port.client.google.GooglePort;
import pe.gob.pj.votacion.domain.port.persistence.sijsuprema.AccesoPersistenceReadPort;
import pe.gob.pj.votacion.domain.port.usecase.sijsuprema.IniciarSesionUseCasePort;
import pe.gob.pj.votacion.usecase.common.utils.PasswordEncryptorSij;

/**
 * 
 * @author oruizb
 * @version 1.0,31/01/2025
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class IniciarSesionUseCaseAdapter implements IniciarSesionUseCasePort {

  AccesoPersistenceReadPort accesoPersistencePort;
  GooglePort googlePort;
  DataSource dataSource;

  public IniciarSesionUseCaseAdapter(AccesoPersistenceReadPort accesoPersistencePort,
      GooglePort googlePort, @Qualifier("sijsupremaDatasource") DataSource dataSource) {
    this.accesoPersistencePort = accesoPersistencePort;
    this.googlePort = googlePort;
    this.dataSource = dataSource;
  }

  @Override
  @Transactional(transactionManager = "sijsupremaTransactionManager",
      propagation = Propagation.REQUIRES_NEW, readOnly = true,
      rollbackFor = {Exception.class, SQLException.class})
  public Usuario iniciarSesion(String cuo, IniciarSesionQuery iniciarSesionQuery,
      PeticionServicios peticion) {

    if (Flag.SI.getCodigo().equalsIgnoreCase(
        Optional.ofNullable(iniciarSesionQuery.aplicaCaptcha()).map(String::trim).orElse(null))
        && ProjectUtils.isNullOrEmpty(iniciarSesionQuery.tokenCaptcha())) {
      log.error(
          "{} Datos de validación captcha -> indicador de validación: {}, token captcha: {} y la ip de la petición {}",
          peticion.getCuo(), iniciarSesionQuery.aplicaCaptcha(), iniciarSesionQuery.tokenCaptcha(),
          peticion.getIp());
      throw new CaptchaException();
    }

    if (Flag.SI.getCodigo().equalsIgnoreCase(
        Optional.ofNullable(iniciarSesionQuery.aplicaCaptcha()).map(String::trim).orElse(null))
        && !googlePort.validarCaptcha(peticion.getCuo(), iniciarSesionQuery.tokenCaptcha(),
            peticion.getIp())) {
      log.error(
          "{} Datos de validación captcha -> indicador de validación: {}, token captcha: {} y la ip de la petición {}",
          peticion.getCuo(), iniciarSesionQuery.aplicaCaptcha(), iniciarSesionQuery.tokenCaptcha(),
          peticion.getIp());
      throw new CaptchaException();
    }

    var user = accesoPersistencePort.iniciarSesion(cuo, iniciarSesionQuery.usuario());
    if (Objects.isNull(user) || !validarCredenciales(cuo, iniciarSesionQuery.usuario(),
        PasswordEncryptorSij.encryptPassword(iniciarSesionQuery.clave()))) {
      log.error("{} Usuario {}", cuo, user);
      throw new CredencialesSinCoincidenciaException();
    }

    if (user.getPerfiles().isEmpty()) {
      throw new UsuarioSinPerfilAsignadoException();
    }

    return user;

  }

  private boolean validarCredenciales(String cuo, String usuario, String clave) {
    String jdbcUrl = obtenerJdbcUrlDesdeDataSource(cuo);
    return probarConexionConUsuario(cuo, jdbcUrl, usuario, clave);
  }

  private String obtenerJdbcUrlDesdeDataSource(String cuo) {
    try (Connection conn = dataSource.getConnection()) {
      return conn.getMetaData().getURL();
    } catch (SQLException e) {
      log.error("{} Error al obtener la URL desde el DataSource", cuo, e);
      throw new CredencialesSinCoincidenciaException();
    }
  }

  private boolean probarConexionConUsuario(String cuo, String jdbcUrl, String usuario,
      String clave) {
    try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, clave)) {
      return true;
    } catch (SQLException e) {
      log.warn("{} Falló la conexión a la url {} con las credenciales '{}/{}': {}", cuo, jdbcUrl,
          usuario, clave, e.getMessage());
      throw new CredencialesSinCoincidenciaException();
    }
  }

}
