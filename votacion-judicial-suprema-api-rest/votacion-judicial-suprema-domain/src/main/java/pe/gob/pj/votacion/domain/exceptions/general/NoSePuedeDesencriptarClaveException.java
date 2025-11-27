package pe.gob.pj.votacion.domain.exceptions.general;

public class NoSePuedeDesencriptarClaveException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public NoSePuedeDesencriptarClaveException(String identificador) {
    super(identificador);
  }

}
