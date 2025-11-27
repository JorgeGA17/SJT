package pe.gob.pj.votacion.domain.exceptions.negocio;

public class ExpedienteSentidoNoExisteException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public ExpedienteSentidoNoExisteException(String message) {
    super(message);
  }
}
