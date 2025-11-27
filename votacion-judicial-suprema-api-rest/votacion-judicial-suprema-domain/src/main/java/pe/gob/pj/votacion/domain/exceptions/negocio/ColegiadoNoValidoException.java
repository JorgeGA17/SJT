package pe.gob.pj.votacion.domain.exceptions.negocio;

public class ColegiadoNoValidoException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  public ColegiadoNoValidoException(String message) {
    super(message);
  }
}
