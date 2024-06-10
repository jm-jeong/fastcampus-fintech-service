package com.fastcampus.fintechservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.dto.request.UserLoginRequest;
import com.fastcampus.fintechservice.dto.request.UserRegisterRequest;
import com.fastcampus.fintechservice.dto.response.UserEmailCheckResponse;
import com.fastcampus.fintechservice.dto.response.UserLoginResponse;
import com.fastcampus.fintechservice.dto.response.UserResponse;
import com.fastcampus.fintechservice.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserApiController {

	private final UserService userService;

	@CrossOrigin("*")
	@PostMapping("/join")
	public Api<UserResponse> register(
		@Valid
		@RequestBody UserRegisterRequest request
	) {
		var response = userService.join(request);
		return Api.OK(response);
	}

	@CrossOrigin("*")
	@PostMapping("/login")
	public Api<UserLoginResponse> login(
		@Valid
		@RequestBody UserLoginRequest request) {
		String token = userService.login(request);
		return Api.OK(new UserLoginResponse(token));
	}

	@CrossOrigin("*")
	@GetMapping("/check-email")
	public Api<UserEmailCheckResponse> checkEmail(@RequestParam String email) {
		UserEmailCheckResponse emailTaken = userService.isEmailTaken(email);
		return Api.OK(emailTaken, "isTaken: true(중복), isTaken: false(사용가능)");
	}

	@CrossOrigin("*")
	@GetMapping("/me")
	public Api<UserResponse> me(Authentication authentication) {
		return Api.OK(UserResponse.from(userService.loadUserByEmail(authentication.getName())));
	}

}