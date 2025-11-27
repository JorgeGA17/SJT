package pe.gob.pj.votacion.infraestructure.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AlfrescoCmisClient {
  private Session session;

  public Session conectar(String host, int puerto, String usuario, String clave) {
    if (session != null) return session;

    try {
      String url = "http://" + host + ":" + puerto + "/alfresco/service/cmis";
      Map<String, String> params = new HashMap<>();
      params.put(SessionParameter.ATOMPUB_URL, url);
      params.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
      params.put(SessionParameter.USER, usuario);
      params.put(SessionParameter.PASSWORD, clave);

      SessionFactory factory = SessionFactoryImpl.newInstance();
      session = factory.getRepositories(params).get(0).createSession();
      log.info("✅ Conectado a Alfresco [{}] - Repositorio: {}", host, session.getRepositoryInfo().getName());

    } catch (Exception e) {
      log.error("❌ Error al conectar con Alfresco", e);
      throw new RuntimeException("Error al conectar con Alfresco", e);
    }
    return session;
  }

  public Session getSession() {
    if (session == null) {
      throw new IllegalStateException("No hay sesión CMIS activa. Llama primero a conectar().");
    }
    return session;
  }
}
