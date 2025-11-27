package pe.gob.pj.votacion.domain.exceptions.negocio;

public class PonenteNoValidoException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public PonenteNoValidoException(String message) {
    super(message);
  }
}
