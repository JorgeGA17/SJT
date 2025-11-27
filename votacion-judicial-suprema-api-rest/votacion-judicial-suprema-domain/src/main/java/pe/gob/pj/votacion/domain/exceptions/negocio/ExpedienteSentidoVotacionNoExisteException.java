package pe.gob.pj.votacion.domain.exceptions.negocio;

public class ExpedienteSentidoVotacionNoExisteException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public ExpedienteSentidoVotacionNoExisteException(String message) {
    super(message);
  }
}
