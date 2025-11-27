package pe.gob.pj.votacion.domain.port.properties;

import pe.gob.pj.votacion.domain.model.properties.AlfrescoConfigData;

public interface AlfrescoConfigurationPort {

  AlfrescoConfigData getJurisprudenciaConfig();

  AlfrescoConfigData getTribunalConfig();

}
