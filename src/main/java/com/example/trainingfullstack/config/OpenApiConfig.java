package com.example.trainingfullstack.config;


import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String BEARER_AUTH = "bearerAuth";

    @Bean
    public OpenAPI trainingFullStackOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Training Full Stack API")
                                .description(
                                        "REST API documentation for User and Task management"
                                )
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .name("API Development Team")
                                )
                )
                .components(
                        new Components().addSecuritySchemes(
                                BEARER_AUTH,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description(
                                                "Paste the JWT access token only. "
                                                        + "Swagger adds the Bearer prefix automatically."
                                        )
                        )
                );
    }
}
