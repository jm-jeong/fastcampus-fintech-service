package com.fastcampus.fintechservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.dto.UserDto;
import com.fastcampus.fintechservice.dto.request.RecommendationRequest;
import com.fastcampus.fintechservice.dto.response.RecommendationResponse;
import com.fastcampus.fintechservice.service.RecommendationService;
import com.fastcampus.fintechservice.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendation")
public class RecommendationController {
	private final RecommendationService recommendationService;
	private final UserService userService;

	@GetMapping
	public Api<RecommendationResponse> recommendation(RecommendationRequest recommendationRequest,
		@RequestParam(required = false, defaultValue = "5", value = "size") int size,
		Authentication authentication) {
		UserDto userDto = userService.loadUserByEmail(authentication.getName());

		return Api.OK(recommendationService.getRecommendationList(recommendationRequest, size, userDto));
	}
}
