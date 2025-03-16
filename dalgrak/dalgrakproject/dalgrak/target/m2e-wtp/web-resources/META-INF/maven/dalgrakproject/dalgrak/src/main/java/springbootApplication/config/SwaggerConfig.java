package springbootApplication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Dalgurak API Documentation")
                        .description("This is the API documentation for the Dalgurak project.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Team Dalgurak")
                                .url("https://www.dalgurak.com")
                                .email("support@dalgurak.com")));
    }
}