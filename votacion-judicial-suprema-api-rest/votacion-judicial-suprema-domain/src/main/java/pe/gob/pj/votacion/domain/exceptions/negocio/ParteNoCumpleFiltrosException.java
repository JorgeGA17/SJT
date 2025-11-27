package pe.gob.pj.votacion.domain.exceptions.negocio;

public class ParteNoCumpleFiltrosException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public ParteNoCumpleFiltrosException(String message) {
    super(message);
  }
}
