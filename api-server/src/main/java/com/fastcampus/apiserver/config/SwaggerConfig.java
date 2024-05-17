package com.fastcampus.apiserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.components(new Components())
			.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info()
			.title("fintech API Document")
			.description("fintech 프로젝트의 API 문서입니다.")
			.version("1.0");
	}
}
