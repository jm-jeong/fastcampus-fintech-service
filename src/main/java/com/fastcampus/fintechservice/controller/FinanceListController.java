package com.fastcampus.fintechservice.controller;


import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.dto.request.FinanceListRequest;
import com.fastcampus.fintechservice.dto.response.FinanceListResponse;
import com.fastcampus.fintechservice.service.FinanceService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/finances")
public class FinanceListController {

    private final FinanceService financeService;

    @GetMapping("/list")
    public Api<Page<FinanceListResponse>> getFinanceList(@RequestParam FinProductType finProductType,
                                                         Pageable pageable) {
        return Api.OK(financeService.getFinanceAll(finProductType, pageable));


    }
    @GetMapping("/list/bank")
    public Api<Page<FinanceListResponse>> getFinanceBankTypeList(@RequestBody FinanceListRequest financeListRequest,
                                                                 Pageable pageable) {
        return Api.OK(financeService.getFinanceBankType(financeListRequest, pageable));
    }
}
