package pe.gob.pj.votacion.infraestructure.properties;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "instancia")
public record InstanciaProperty(Sala sala) {
  public record Sala(List<String> permitidas) {
    public boolean contiene(String codigo) {
      return permitidas != null && permitidas.contains(codigo);
    }
  }
}
