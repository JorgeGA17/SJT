package pe.gob.pj.votacion.domain.exceptions.negocio;

public class VotoDiscordiaNoActualizadoException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public VotoDiscordiaNoActualizadoException(String message) {
    super(message);
  }
}
