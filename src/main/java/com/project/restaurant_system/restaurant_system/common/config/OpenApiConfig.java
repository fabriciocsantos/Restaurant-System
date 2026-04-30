package com.project.restaurant_system.restaurant_system.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI restaurantSystemOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Restaurant System API")
                .version("v1")
                .description("API do sistema compartilhado de gestao de restaurantes."));
    }
}
