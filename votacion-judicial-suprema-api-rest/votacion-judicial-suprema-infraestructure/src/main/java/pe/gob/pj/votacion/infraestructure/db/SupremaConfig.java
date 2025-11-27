package pe.gob.pj.votacion.infraestructure.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.votacion.infraestructure.properties.TimeoutProperty;

/**
 * 
 * Configuración de conexión,manejo de entidades y sus transacciones a base de datos CONDENAS
 * 
 * @author oruizb
 * @version 1.0,07/02/2022
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "pe.gob.pj.votacion.infraestructure.db.sijsuprema.repositories",
    entityManagerFactoryRef = "sijsupremaEntityManagerFactory",
    transactionManagerRef = "sijsupremaTransactionManager")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SupremaConfig {

  private static final String ASA_DIALECT = "org.hibernate.dialect.SybaseASADialect";
  private static final String ASE_DIALECT = "org.hibernate.dialect.SybaseDialect";
  private static final int ASA_BATCH_SIZE = 30;
  private static final int ASE_BATCH_SIZE = 50;

  TimeoutProperty timeoutProperty;

  @Bean(name = "sijsupremaDatasource")
  DataSource pjseguridadDataSource() throws NamingException {
    return (DataSource) new InitialContext()
        .lookup("java:jboss/datasources/votacionJudicialSupremaAPISuprema");
  }

  @Bean(name = "sijsupremaEntityManagerFactory")
  LocalContainerEntityManagerFactoryBean sijsupremaEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("sijsupremaDatasource") DataSource sijsupremaDatasource) {
    return builder.dataSource(sijsupremaDatasource)
        .packages("pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities")
        .persistenceUnit("sijsuprema").properties(determineSybaseProperties(sijsupremaDatasource))
        .build();
  }

  @Bean(name = "sijsupremaTransactionManager")
  PlatformTransactionManager sijsupremaTransactionManager(
      @Qualifier("sijsupremaEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager txManager = new JpaTransactionManager(entityManagerFactory);
    txManager.setDefaultTimeout(timeoutProperty.getDatabaseTransaction());
    return txManager;
  }

  @Bean(name = "sijsupremaJdbcTemplate")
  JdbcTemplate sijsupremaJdbcTemplate(
      @Qualifier("sijsupremaDatasource") DataSource sijsupremaDatasource) {
    return new JdbcTemplate(sijsupremaDatasource);
  }

  @Bean(name = "sijsupremaQDSL")
  JPAQueryFactory sijsupremaQDSL(
      @Qualifier("sijsupremaEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
    return new JPAQueryFactory(em);
  }

  private Map<String, Object> determineSybaseProperties(DataSource dataSource) {
    Map<String, Object> properties = new HashMap<>();
    boolean isASA = detectSybaseType(dataSource);

    // Configuración específica por tipo
    properties.put(AvailableSettings.DIALECT, isASA ? ASA_DIALECT : ASE_DIALECT);
    properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, isASA ? ASA_BATCH_SIZE : ASE_BATCH_SIZE);

    // Propiedades comunes
    addCommonSybaseProperties(properties);

    log.info("Configurando propiedades para Sybase {}", isASA ? "ASA" : "ASE");
    return properties;
  }

  private boolean detectSybaseType(DataSource dataSource) {
    try (Connection connection = dataSource.getConnection()) {
      DatabaseMetaData metaData = connection.getMetaData();
      String productName = metaData.getDatabaseProductName();
      String productVersion = metaData.getDatabaseProductVersion();

      boolean isASA = productName.contains("Adaptive Server Anywhere")
          || productName.contains("SQL Anywhere") || productName.contains("SAP SQL Anywhere");

      log.info("Detectado: {} versión {}", productName, productVersion);
      log.info("Tipo de Sybase detectado: {}", isASA ? "ASA (Anywhere)" : "ASE (Enterprise)");
      return isASA;
    } catch (SQLException e) {
      log.warn("No se pudo determinar el tipo de Sybase, usando ASE (Enterprise) por defecto", e);
      return false;
    }
  }

  private void addCommonSybaseProperties(Map<String, Object> properties) {
    properties.put("hibernate.show_sql", "false");
    properties.put("hibernate.format_sql", "true");
    properties.put("hibernate.query.substitutions", "true=1, false=0");
    properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
    properties.put("hibernate.connection.autocommit", "false");
    properties.put("hibernate.jdbc.use_scrollable_resultset", "false");
    properties.put("hibernate.transaction.jta.platform",
        "org.hibernate.service.jta.platform.internal.JBossAppServerJtaPlatform");

    // Optimización para Sybase
    // properties.put("hibernate.jdbc.time_zone", "America/Lima");
    properties.put("hibernate.jdbc.lob.non_contextual_creation", "true");
  }

}
