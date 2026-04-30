package com.project.restaurant_system.restaurant_system.support;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class PostgreSQLIntegrationTest {

    private static final PostgreSQLContainer<?> POSTGRES;

    static {
        if (!DockerClientFactory.instance().isDockerAvailable()) {
            throw new IllegalStateException("Docker nao esta disponivel para executar os testes de integracao com PostgreSQL.");
        }

        POSTGRES = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
                .withDatabaseName("restaurant_system_test")
                .withUsername("postgres")
                .withPassword("postgres");
        POSTGRES.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.datasource.driver-class-name", POSTGRES::getDriverClassName);
    }
}
