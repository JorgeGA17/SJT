package pe.gob.pj.votacion.domain.exceptions.negocio;

public class PadreNoInsertadoException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public PadreNoInsertadoException(String message) {
    super(message);
  }
}
