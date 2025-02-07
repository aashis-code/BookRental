package com.bookrental.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Book Rental")
                        .description("This is a book rental for the book management system.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Aashis Pandey")
                                .email("aashis@gmail.com")
                                .url("")))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth")) // Security for all endpoints
                .components(new Components()
                        .addSecuritySchemes("BearerAuth",
                                new SecurityScheme()
                                        .name("BearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
//                .servers(List.of(
//                        new Server().url("https://api.example.com/v1").description("Production Server"),
//                        new Server().url("https://api-staging.example.com/v1").description("Staging Server")
//                ))
//                ;
    }


}
