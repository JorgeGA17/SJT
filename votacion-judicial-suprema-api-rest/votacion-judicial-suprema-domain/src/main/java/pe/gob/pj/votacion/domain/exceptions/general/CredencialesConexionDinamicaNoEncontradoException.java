package pe.gob.pj.votacion.domain.exceptions.general;

public class CredencialesConexionDinamicaNoEncontradoException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CredencialesConexionDinamicaNoEncontradoException(String identificado) {
    super(identificado);
  }

}
