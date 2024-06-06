package com.fastcampus.fintechservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.dto.request.FinanceDetailRequest;
import com.fastcampus.fintechservice.dto.response.FinanceDetailResponse;
import com.fastcampus.fintechservice.service.FinanceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/finances")
public class FinanceDetailController {
	private final FinanceService financeService;

	@PostMapping
	public Api<FinanceDetailResponse> getFinanceDetail(
		@Valid @RequestBody FinanceDetailRequest financeDetailRequest
	) {
		FinanceDetailResponse financeDetail = financeService.getFinanceDetail(financeDetailRequest);
		return Api.OK(financeDetail);
	}

}
