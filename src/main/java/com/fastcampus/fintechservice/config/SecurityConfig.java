package com.fastcampus.fintechservice.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fastcampus.fintechservice.config.filter.JwtTokenFilter;
import com.fastcampus.fintechservice.exception.CustomAuthenticationEntryPoint;
import com.fastcampus.fintechservice.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private static final String[] PERMIT = new String[] {
		"/swagger-ui/**",
		"/v3/api-docs/**"
	};
	private final UserService userService;
	@Value("${jwt.secret-key}")
	private String secretKey;
	@Value("${frontend.server-url}")
	private String frontendServerUrl;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(httpSecuritySessionManagementConfigurer -> {
				httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			}).exceptionHandling((exceptionHandling) ->
				exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
			).addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.requestMatchers("/api/**").permitAll()
				.requestMatchers(PERMIT).permitAll()
				.anyRequest().authenticated()
			);

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
			.and().ignoring().requestMatchers(PERMIT);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		// CorsConfiguration configuration = new CorsConfiguration();
		// configuration.addAllowedOrigin("*");
		// configuration.addAllowedMethod("*");
		// configuration.addAllowedHeader("*");
		// configuration.setAllowedOrigins(Arrays.asList(frontendServerUrl, "*"));
		// configuration.setAllowedMethods(Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT"));
		// configuration.setAllowedHeaders(Collections.singletonList("*"));
		// configuration.setAllowCredentials(true);
		// UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// source.registerCorsConfiguration("/**", configuration);

		CorsConfiguration configuration = new CorsConfiguration();
		// configuration.setAllowCredentials(true);//TODO:토큰때문에 기능 켜야한다고 생각했는데, CORS해제 안되서 끔
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.addExposedHeader("*");
		configuration.setAllowCredentials(true);
		configuration.setAllowedOrigins(
			Arrays.asList(frontendServerUrl, "http://localhost:3000", "https://127.0.0.1:3000",
				"http://localhost:8080"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT"));
		configuration.setAllowedHeaders(Collections.singletonList("*"));
		configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

}
