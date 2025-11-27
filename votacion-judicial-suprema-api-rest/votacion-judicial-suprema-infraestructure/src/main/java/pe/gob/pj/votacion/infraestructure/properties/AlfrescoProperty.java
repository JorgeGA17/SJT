package pe.gob.pj.votacion.infraestructure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "alfresco")
public record AlfrescoProperty(Jurisprudencia jurisprudencia,
    Tribunal tribunal
    ) {
  
  public record Jurisprudencia(@DefaultValue("localhost") String host,
      @DefaultValue("8080") Integer puerto,
      @DefaultValue("admin") String usuario,
      @DefaultValue("admin") String clave,
      @DefaultValue("/") String path,
      @DefaultValue("4.2") String version) {
    
    public String getFullUrl() {
        return "http://" + host + ":" + puerto + path;
    }
    
    public String getBaseUrl() {
        return "http://" + host + ":" + puerto;
    }
    
    public String getConnectionString() {
        return getBaseUrl() + " (v" + version + ")";
    }
    
    // Para logging o debugging
    public String getConnectionInfo() {
        return String.format("Alfresco[host=%s, puerto=%d, usuario=%s, version=%s]", 
                           host, puerto, usuario, version);
    }
    
  }
  
  public record Tribunal(@DefaultValue("localhost") String host,
      @DefaultValue("8080") Integer puerto,
      @DefaultValue("admin") String usuario,
      @DefaultValue("admin") String clave,
      @DefaultValue("/") String path,
      @DefaultValue("4.2") String version) {
    
    public String getFullUrl() {
        return "http://" + host + ":" + puerto + path;
    }
    
    public String getBaseUrl() {
        return "http://" + host + ":" + puerto;
    }
    
    public String getConnectionString() {
        return getBaseUrl() + " (v" + version + ")";
    }
    
    // Para logging o debugging
    public String getConnectionInfo() {
        return String.format("Alfresco[host=%s, puerto=%d, usuario=%s, version=%s]", 
                           host, puerto, usuario, version);
    }
  }
    
}