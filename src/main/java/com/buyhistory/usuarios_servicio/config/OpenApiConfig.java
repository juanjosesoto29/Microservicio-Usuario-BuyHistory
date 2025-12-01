package com.buyhistory.usuarios_servicio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usuariosOpenAPI() {
        // Nombre del esquema de seguridad que usaremos en las anotaciones
        String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("BuyHistory - API de Usuarios y Autenticación")
                        .description("Microservicio de gestión de usuarios, roles y autenticación (JWT) de BuyHistory.")
                        .version("v1.0"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8082")
                                .description("Servidor local - Usuarios/Auth (8082)")
                ))
                // Declaramos el esquema de seguridad tipo Bearer JWT
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                // Requerimos por defecto el esquema en la API (puedes también ponerlo por endpoint)
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }
}
