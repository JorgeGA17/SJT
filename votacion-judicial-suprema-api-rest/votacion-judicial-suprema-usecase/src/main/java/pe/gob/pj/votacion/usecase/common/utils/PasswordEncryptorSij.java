package pe.gob.pj.votacion.usecase.common.utils;

import java.util.Objects;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class PasswordEncryptorSij {
  /**
   * Encripta una contraseña usando el mismo algoritmo que usp_WsEncriptarPwd
   * 
   * @param password Cadena a encriptar (máximo 32 caracteres)
   * @return Contraseña encriptada
   * @throws IllegalArgumentException si la contraseña es nula o excede 32 caracteres
   */
  public static String encryptPassword(String password) {
    Objects.requireNonNull(password, "La contraseña no puede ser nula");

    if (password.length() > 32) {
      throw new IllegalArgumentException("La contraseña no puede exceder 32 caracteres");
    }

    StringBuilder encrypted = new StringBuilder();

    for (int i = 0; i < password.length(); i++) {
      char currentChar = password.charAt(i);
      int asciiValue = currentChar;
      int transformedValue = transformCharacter(asciiValue);
      encrypted.append((char) transformedValue);
    }

    return encrypted.toString();
  }

  private static int transformCharacter(int asciiValue) {
    // Dígitos 0-9
    if (asciiValue > 47 && asciiValue < 58) {
      return 64 + (asciiValue - 47);
    }
    // Letras A-K
    else if (asciiValue >= 65 && asciiValue <= 75) {
      return 47 + (asciiValue - 64);
    }
    // Letras L-V
    else if (asciiValue >= 76 && asciiValue <= 86) {
      return 47 + (asciiValue - 75);
    }
    // Letras W-Z
    else if (asciiValue >= 87 && asciiValue <= 90) {
      return 47 + (asciiValue - 86);
    }
    // Otros caracteres no se modifican
    return asciiValue;
  }
}
