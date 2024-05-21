package com.fastcampus.fintechservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {

		// JWT V1
		String jwt = "JWT";
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
		Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
			.name(jwt)
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT")
		);

		return new OpenAPI()
			.components(components)
			.addSecurityItem(securityRequirement)
			.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info()
			.title("fintech API Document")
			.description("fintech 프로젝트의 API 문서입니다.")
			.version("1.0");
	}
}
