package pe.gob.pj.votacion.infraestructure.mappers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import pe.gob.pj.votacion.domain.common.enums.Formatos;
import pe.gob.pj.votacion.domain.common.utils.ProjectUtils;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FechaMapperHelper {

  @Named("stringToDate")
  public default Date stringToDate(String fechaString) {
    return ProjectUtils.parseStringToDate(fechaString,
        Formatos.FECHA_DD_MM_YYYY_HH_MM_SS_SSS.getFormato());
  }

  @Named("stringToOffsetDateTime")
  public default OffsetDateTime stringToOffsetDateTime(String fechaString) {
    return ProjectUtils.parseStringToOffsetDateTime(fechaString,
        Formatos.FECHA_DD_MM_YYYY_HH_MM_SS_SSS.getFormato());
  }

  @Named("stringToLocalDateTime")
  public default LocalDateTime stringToLocalDateTime(String fechaString) {
    return ProjectUtils.parseStringToLocalDateTime(fechaString,
        Formatos.FECHA_DD_MM_YYYY_HH_MM_SS_SSS.getFormato());
  }

  @Named("stringToZonedDateTime")
  public default ZonedDateTime stringZonedDateTime(String fechaString) {
    return ProjectUtils.parseStringToZonedDateTime(fechaString,
        Formatos.FECHA_YYYY_MM_DD_HH_MM_SS.getFormato());
  }

  @Named("zonedDateTimeToString")
  public default String zonedDateTimeToString(ZonedDateTime fecha) {
    return ProjectUtils.zonedDateTimeToString(fecha,
        Formatos.FECHA_YYYY_MM_DD_HH_MM_SS_SSS.getFormato());
  }

}
