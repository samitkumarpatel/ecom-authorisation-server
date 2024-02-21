package net.efullstack.ecomauthorisationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestEcomAuthorisationServerApplication {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
				.withInitScript("db/schema.sql");
	}

	public static void main(String[] args) {
		SpringApplication.from(EcomAuthorisationServerApplication::main).with(TestEcomAuthorisationServerApplication.class).run(args);
	}

}
