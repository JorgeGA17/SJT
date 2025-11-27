package pe.gob.pj.votacion.domain.exceptions.negocio;

public class ExpedienteNoActualizadoException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public ExpedienteNoActualizadoException(String message) {
    super(message);
  }
}
