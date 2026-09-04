package com.example.trainingfullstack.config;


import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
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
                );
    }
}
