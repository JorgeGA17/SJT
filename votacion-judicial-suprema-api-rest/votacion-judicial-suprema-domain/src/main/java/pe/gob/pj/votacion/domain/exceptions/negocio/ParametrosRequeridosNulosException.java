package pe.gob.pj.votacion.domain.exceptions.negocio;

public class ParametrosRequeridosNulosException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public ParametrosRequeridosNulosException(String message) {
    super(message);
  }
}
