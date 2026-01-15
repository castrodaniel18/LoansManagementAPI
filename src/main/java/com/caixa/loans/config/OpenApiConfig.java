package com.caixa.loans.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI loanApi() {
		return new OpenAPI().info(new Info().title("LoansManagement API")
				.description("API REST para la gestión de solicitudes de préstamo"));
	}
}
