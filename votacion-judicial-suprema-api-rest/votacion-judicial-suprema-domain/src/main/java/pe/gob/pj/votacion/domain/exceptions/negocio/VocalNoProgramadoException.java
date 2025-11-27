package pe.gob.pj.votacion.domain.exceptions.negocio;

public class VocalNoProgramadoException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public VocalNoProgramadoException(String message) {
    super(message);
  }
}
