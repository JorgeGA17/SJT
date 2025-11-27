package pe.gob.pj.votacion.domain.exceptions.negocio;

public class UsuarioNoProgramadoException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public UsuarioNoProgramadoException(String message) {
    super(message);
  }
}
