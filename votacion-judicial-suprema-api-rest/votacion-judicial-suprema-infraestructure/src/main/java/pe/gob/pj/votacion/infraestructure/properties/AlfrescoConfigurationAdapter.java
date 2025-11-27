package pe.gob.pj.votacion.infraestructure.properties;

import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.domain.model.properties.AlfrescoConfigData;
import pe.gob.pj.votacion.domain.port.properties.AlfrescoConfigurationPort;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AlfrescoConfigurationAdapter implements AlfrescoConfigurationPort {
  
  AlfrescoProperty alfrescoProperty;

  @Override
  public AlfrescoConfigData getJurisprudenciaConfig() {
      var jurisProp = alfrescoProperty.jurisprudencia();
      return new AlfrescoConfigData(
          jurisProp.host(),
          String.valueOf(jurisProp.puerto()),
          jurisProp.usuario(),
          jurisProp.clave(),
          jurisProp.path(),
          jurisProp.version()
      );
  }

  @Override
  public AlfrescoConfigData getTribunalConfig() {
      var tribunalProp = alfrescoProperty.tribunal();
      return new AlfrescoConfigData(
          tribunalProp.host(),
          String.valueOf(tribunalProp.puerto()),
          tribunalProp.usuario(),
          tribunalProp.clave(),
          tribunalProp.path(),
          tribunalProp.version()
      );
  }

}
