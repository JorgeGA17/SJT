package pe.gob.pj.votacion.infraestructure.db;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Configuración de conexión,manejo de entidades y sus transacciones a base de datos TC
 *
 * @author aperalesb
 * @version 2.0,10/10/2025
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "pe.gob.pj.votacion.infraestructure.db.tribunal.repositories",
    entityManagerFactoryRef = "tribunalEntityManagerFactory",
    transactionManagerRef = "txManagerTribunal")
public class TribunalConfig {

  // CONEXION CON LA BASE DE DATOS TC
  @Bean(name = "cxTribunalDS")
  DataSource tribunalDataSource() throws NamingException {
    return (DataSource) new InitialContext()
        .lookup("java:jboss/datasources/votacionJudicialSupremaAPIJuris");
  }

  @Bean(name = "tribunalEntityManagerFactory")
  LocalContainerEntityManagerFactoryBean tribunalEntityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("cxTribunalDS") DataSource dataSource) {
    return builder.dataSource(dataSource)
        .packages("pe.gob.pj.votacion.infraestructure.db.tribunal.entities")
        .persistenceUnit("tribunal").properties(getHibernateProperties()).jta(true).build();
  }

  // Para usar transacciones
  @Bean(name = "txManagerTribunal")
  PlatformTransactionManager tribunalTransactionManager() {
    return new JtaTransactionManager();
  }

  // Para usar querydsl
  @Bean(name = "tribunalQDSL")
  JPAQueryFactory jpaQueryFactoryTribunal(
      @Qualifier("tribunalEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
    return new JPAQueryFactory(em);
  }

  private Map<String, Object> getHibernateProperties() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    properties.put("hibernate.show_sql", "false");
    properties.put("hibernate.format_sql", "true");
    properties.put("hibernate.connection.release_mode", "AFTER_TRANSACTION");
    properties.put("hibernate.type", "true");
    properties.put("hibernate.transaction.jta.platform",
        "org.hibernate.service.jta.platform.internal.JBossAppServerJtaPlatform");
    return properties;
  }
}
