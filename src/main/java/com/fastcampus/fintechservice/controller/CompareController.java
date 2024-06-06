package com.fastcampus.fintechservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.dto.request.FinanceCompareRequest;
import com.fastcampus.fintechservice.dto.response.FinanceCompareResponse;
import com.fastcampus.fintechservice.service.CompareService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/compare")
public class CompareController {
	private final CompareService compareService;

	@PostMapping
	public Api<FinanceCompareResponse> compare(
		@Valid
		@RequestBody FinanceCompareRequest financeCompareRequest
	) {
		var response = compareService.getFinanceData(financeCompareRequest);
		return Api.OK(response);
	}

}
