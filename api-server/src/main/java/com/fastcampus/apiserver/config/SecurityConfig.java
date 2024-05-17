package com.fastcampus.apiserver.config;

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

import com.fastcampus.apiserver.config.filter.JwtTokenFilter;
import com.fastcampus.apiserver.exception.CustomAuthenticationEntryPoint;
import com.fastcampus.apiserver.service.UserService;

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

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable);

		http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
			httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		});

		http.exceptionHandling((exceptionHandling) ->
			exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
		);

		http.addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class);

		http.authorizeHttpRequests(authorize -> authorize
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

}
