package pe.gob.pj.votacion.domain.exceptions.negocio;

public class ExpedienteNoInsertadoException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public ExpedienteNoInsertadoException(String message) {
    super(message);
  }
}
