package com.fastcampus.fintechservice.controller;


import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.dto.response.FinanceListResponse;
import com.fastcampus.fintechservice.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home")
public class HomeController {
    private final HomeService homeService;
    @GetMapping
    public Api<Page<FinanceListResponse>> getFinanceAll(Pageable pageable) {
        return Api.OK(homeService.getAllFinanceProduct(pageable));
    }

    @GetMapping("/search")
    public Api<Page<FinanceListResponse>> getFinanceSearch(String keyword, Pageable pageable) {
        return Api.OK(homeService.getAllFinanceProductSearch(keyword, pageable));
    }
}
