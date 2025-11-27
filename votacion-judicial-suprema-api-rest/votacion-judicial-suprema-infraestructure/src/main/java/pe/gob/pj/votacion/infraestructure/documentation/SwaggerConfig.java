package pe.gob.pj.votacion.infraestructure.documentation;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import pe.gob.pj.votacion.infraestructure.common.enums.AplicativoInfo;

@Configuration
public class SwaggerConfig {

  @Bean
  OpenAPI apiInfo() {
    return new OpenAPI()
        .info(new Info().title("Votacion Judicial Suprema Api Rest")
            .version(AplicativoInfo.VERSION_ACTUAL.getNombre()))
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("bearerAuth",
            new SecurityScheme().name("bearerAuth").type(SecurityScheme.Type.HTTP).scheme("bearer")
                .bearerFormat("JWT")));
  }

  @Bean
  GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder().group("public")
        .pathsToMatch("/".concat(AplicativoInfo.CONEXTO.getNombre()).concat("/**")).build();
  }

}
