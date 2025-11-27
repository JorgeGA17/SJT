package pe.gob.pj.votacion.domain.exceptions.negocio;

public class MovVotoJurispNoActualizadoException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  public MovVotoJurispNoActualizadoException(String message) {
    super(message);
  }
}
