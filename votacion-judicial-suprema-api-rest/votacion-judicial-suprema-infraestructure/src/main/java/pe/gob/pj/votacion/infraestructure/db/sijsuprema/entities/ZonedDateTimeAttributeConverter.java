package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ZonedDateTimeAttributeConverter
    implements AttributeConverter<ZonedDateTime, Timestamp> {

  /**
   * Convierte el ZonedDateTime de tu entidad a un Timestamp para la base de datos. La clave es
   * convertirlo a LocalDateTime ANTES de pasarlo a Timestamp, para "arrancarle" la zona horaria y
   * preservar la hora de reloj.
   */
  @Override
  public Timestamp convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
    if (zonedDateTime == null) {
      return null;
    }
    return Timestamp.valueOf(zonedDateTime.toLocalDateTime());
  }

  /**
   * Convierte el Timestamp de la base de datos de vuelta a un ZonedDateTime para tu entidad.
   * Asumimos que la hora guardada en la BD es la hora local del servidor.
   */
  @Override
  public ZonedDateTime convertToEntityAttribute(Timestamp dbData) {
    if (dbData == null) {
      return null;
    }
    return dbData.toLocalDateTime().atZone(ZoneId.systemDefault());
  }
}
