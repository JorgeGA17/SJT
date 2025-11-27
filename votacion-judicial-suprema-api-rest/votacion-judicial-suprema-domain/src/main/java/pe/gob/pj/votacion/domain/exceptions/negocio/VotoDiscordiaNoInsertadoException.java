package pe.gob.pj.votacion.domain.exceptions.negocio;

public class VotoDiscordiaNoInsertadoException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public VotoDiscordiaNoInsertadoException(String message) {
    super(message);
  }
}
