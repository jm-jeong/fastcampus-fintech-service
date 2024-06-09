package com.fastcampus.fintechservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.dto.request.RecommendationRequest;
import com.fastcampus.fintechservice.dto.response.RecommendationResponse;
import com.fastcampus.fintechservice.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendation")
public class RecommendationController {
	private final RecommendationService recommendationService;

	@GetMapping
	public Api<RecommendationResponse> recommendation(RecommendationRequest recommendationRequest,
		@RequestParam(required = false, defaultValue = "5", value = "size") int size) {

		return Api.OK(recommendationService.getRecommendationList(recommendationRequest, size));
	}
}
