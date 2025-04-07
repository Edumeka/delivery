package com.emeka.delivery.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Proyecto Lenguajes de Programacion (Delivery)")
                .version("1.0.0")
                .description("API para gestionar pedidos.\nEste proyecto fue desarrollado como parte de una colaboración grupal para la asignatura de Lenguajes de Programacion."));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("delivery")
            .pathsToMatch("/delivery/v1/**")  // Aquí ajustas la ruta de tus controladores
            .build();
    }
}
