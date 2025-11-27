package pe.gob.pj.votacion.domain.exceptions.negocio;

public class CodigoSentidoNoExisteException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public CodigoSentidoNoExisteException(String message) {
    super(message);
  }
}
