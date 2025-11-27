package pe.gob.pj.votacion.domain.common.utils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Envuelve métodos de ayuda para aplicar DRY
 * 
 * @author oruizb
 * @version 1.0,07/02/2022
 */
@Slf4j
@UtilityClass
public class ProjectUtils {

  Random RANDOM = new Random();

  public boolean isNullOrEmpty(Object valor) {
    boolean flag = false;
    if (valor == null || (String.valueOf(valor)).trim().equalsIgnoreCase("")
        || (String.valueOf(valor)).trim().equalsIgnoreCase("null")) {
      flag = true;
    }
    return flag;
  }

  public String obtenerCodigoUnico() {
    Date fechaActual = new Date();
    SimpleDateFormat formato = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
    String strFechaActual = formato.format(fechaActual);
    int aleatorio = RANDOM.nextInt(999) + 1;
    StringBuilder cuo = new StringBuilder();
    cuo.append(strFechaActual).append(String.valueOf(aleatorio));
    return cuo.toString();
  }

  public static LocalDateTime inicioDelDia(String fecha, String formato) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
    return LocalDate.parse(fecha, formatter).atStartOfDay();
  }

  public static LocalDateTime finDelDia(String fecha, String formato) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
    return LocalDate.parse(fecha, formatter).atTime(LocalTime.MAX);
  }

  public static ZonedDateTime inicioDelDiaZonedDateTime(String fecha, String formato) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
    LocalDate localDate = LocalDate.parse(fecha, formatter);
    return localDate.atStartOfDay(ZoneId.systemDefault());
  }

  public static ZonedDateTime finDelDiaZonedDateTime(String fecha, String formato) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
    LocalDate localDate = LocalDate.parse(fecha, formatter);
    return localDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault());
  }

  public static LocalDateTime inicioDelDiaLocalDateTime(String fecha, String formato) {
    if (fecha == null || fecha.isBlank())
      return null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
    LocalDate localDate = LocalDate.parse(fecha, formatter);
    return localDate.atStartOfDay();
  }

  public static LocalDateTime finDelDiaLocalDateTime(String fecha, String formato) {
    if (fecha == null || fecha.isBlank())
      return null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
    LocalDate localDate = LocalDate.parse(fecha, formatter);
    return localDate.atTime(LocalTime.MAX);
  }

  public String convertZonedDateTimeToString(ZonedDateTime zonedDateTime, String formato) {
    if (zonedDateTime == null) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
    return zonedDateTime.format(formatter);
  }

  public String convertDateToString(Date fecha, String pattern) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    if (Objects.nonNull(fecha))
      return sdf.format(fecha);
    else
      return "";
  }

  public LocalDateTime convertStringToLocalDateTime(String fecha, String formato) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
    return LocalDateTime.parse(fecha, formatter);
  }

  public Date sumarRestarSegundos(Date fecha, int segundos) {
    Calendar c = Calendar.getInstance();
    c.setTime(fecha);
    c.add(Calendar.SECOND, segundos);
    return c.getTime();
  }

  public Date parseStringToDate(String fechaString, String format) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.of("es", "ES"));
    Date fechaDate = null;
    try {
      fechaDate = simpleDateFormat.parse(fechaString);
    } catch (ParseException e) {
      log.error(" Error : {}", e);
    }
    return fechaDate;
  }

  /**
   * Convierte un String de fecha/hora a un objeto ZonedDateTime, manejando fracciones de segundo
   * opcionales (de 1 a 3 dígitos).
   * <p>
   * Se asume que el String representa la hora local en "America/Lima".
   *
   * @param fechaString La fecha en formato de texto (ej. "01/10/2025 09:12:17.321").
   * @param formato El patrón base de la fecha, sin los milisegundos (ej. "dd/MM/yyyy HH:mm:ss").
   * @return Un objeto ZonedDateTime, o null si la entrada es inválida o el parseo falla.
   */
  public static ZonedDateTime parseStringToZonedDateTime(String fechaString, String formato) {
    if (fechaString == null || fechaString.trim().isEmpty() || formato == null
        || formato.trim().isEmpty()) {
      log.warn("La fecha o el formato son nulos o vacíos. No se puede convertir.");
      return null;
    }
    try {
      DateTimeFormatter formatter =
          new DateTimeFormatterBuilder()
              .appendPattern(formato)
              .optionalStart()
              .appendLiteral('.')
              .appendFraction(ChronoField.MILLI_OF_SECOND, 1, 3, false).optionalEnd()
              .toFormatter(Locale.of("es", "PE"));

      LocalDateTime localDateTime = LocalDateTime.parse(fechaString, formatter);

      ZoneId zonaLima = ZoneId.of("America/Lima");
      return localDateTime.atZone(zonaLima);

    } catch (DateTimeParseException e) {
      log.error("Error al parsear la fecha '{}' con el formato base '{}'.", fechaString, formato,
          e);
      return null;
    }
  }

  public static LocalDateTime parseStringToLocalDateTime(String fechaString, String formato) {
    if (fechaString == null || fechaString.trim().isEmpty()) {
      return null;
    }
    DateTimeFormatter formatter =
        new DateTimeFormatterBuilder().appendPattern(formato).optionalStart().appendLiteral('.')
            .appendFraction(ChronoField.MILLI_OF_SECOND, 1, 3, false).optionalEnd().toFormatter();
    try {
      return LocalDateTime.parse(fechaString, formatter);
    } catch (DateTimeParseException e) {
      log.error("Error al parsear la fecha '{}': {}", fechaString, e.getMessage());
      return null;
    }

  }
  
  public String zonedDateTimeToString(ZonedDateTime fecha, String format) {
    if (fecha == null) {
      return null;
    }
    return fecha.format(DateTimeFormatter.ofPattern(format));
  }

  public OffsetDateTime parseStringToOffsetDateTime(String fechaString, String format) {

    DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(format)
        .optionalStart().appendLiteral('.').appendFraction(ChronoField.MILLI_OF_SECOND, 1, 3, false)
        .optionalEnd().toFormatter(Locale.of("es", "ES"));

    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.of("es", "ES"));
    ZoneId zonaLima = ZoneId.of("America/Lima");

    try {
      if (Objects.nonNull(fechaString)) {
        LocalDateTime fechaLocal = LocalDateTime.parse(fechaString, formatter);
        return fechaLocal.atZone(zonaLima).toOffsetDateTime();
      }
    } catch (DateTimeParseException e) {
      log.error("Error al parsear la fecha: {}", e.getMessage());
    }

    return null;
  }

  /**
   * Convierte una excepción en una cadena de texto.
   *
   * @param e La excepción a convertir.
   * @return Una cadena que representa la excepción, incluyendo su stack trace. Si la excepción es
   *         null, devuelve un mensaje personalizado.
   */
  public String convertExceptionToString(Exception e) {
    if (e == null) {
      return "Se ha producido una excepcion personalizada.";
    }
    try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
      e.printStackTrace(pw);
      return sw.toString();
    } catch (IOException ioException) {
      // Si ocurre un error al escribir el stack trace, devolvemos un mensaje de error.
      return "Error al convertir la excepción a cadena: " + ioException.getMessage();
    }
  }

  public static byte[] inputStreamToBytes(InputStream in) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
    byte[] bytes = new byte[512];
    int readBytes;
    while ((readBytes = in.read(bytes)) > 0) {
      outputStream.write(bytes, 0, readBytes);
    }
    byte[] byteData = outputStream.toByteArray();
    outputStream.close();
    return byteData;
  }

  public static String extensionArchivo(String nomArchivo) {
    String arr[] = nomArchivo.split("\\.");
    return arr[arr.length - 1].toLowerCase();
  }


}
