package com.fastcampus.apiserver.config.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fastcampus.apiserver.config.utils.JwtTokenUtils;
import com.fastcampus.apiserver.dto.UserDto;
import com.fastcampus.apiserver.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

	private final UserService userService;

	private final String secretKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		ServletException,
		IOException {
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (header == null || !header.startsWith("Bearer ")) {
			log.error("Authorization Header does not start with Bearer {}", request.getRequestURL());
			chain.doFilter(request, response);
			return;
		}

		try {
			final String token = header.split(" ")[1].trim();
			String userEmail = JwtTokenUtils.getUserEmail(token, secretKey);
			UserDto userDto = userService.loadUserByEmail(userEmail);

			if (!JwtTokenUtils.validate(token, userDto.getUsername(), secretKey)) {
				chain.doFilter(request, response);
				return;
			}
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDto, null,
				userDto.getAuthorities()
			);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (RuntimeException e) {
			chain.doFilter(request, response);
			return;
		}

		chain.doFilter(request, response);
	}
}
