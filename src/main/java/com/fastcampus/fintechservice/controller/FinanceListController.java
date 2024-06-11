package com.fastcampus.fintechservice.controller;


import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.dto.request.FinanceListRequest;
import com.fastcampus.fintechservice.dto.response.FinanceListResponse;
import com.fastcampus.fintechservice.service.FinanceService;
import com.fastcampus.fintechservice.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/finances")
public class FinanceListController {

    private final FinanceService financeService;
    private final UserService userService;

    @GetMapping("/list")
    public Api<Page<FinanceListResponse>> getFinanceList(@RequestParam FinProductType finProductType,
                                                         Authentication authentication, Pageable pageable) {
        return Api.OK(financeService.getFinanceAll(finProductType,
                userService.loadUserByEmail(authentication.getName()),pageable));


    }
    @GetMapping("/list/bank")
    public Api<Page<FinanceListResponse>> getFinanceBankTypeList(@RequestBody FinanceListRequest financeListRequest
                                                                 , Authentication authentication,Pageable pageable) {
        return Api.OK(financeService.getFinanceBankType(financeListRequest,
                userService.loadUserByEmail(authentication.getName()),pageable));
    }

    @GetMapping("/search")
    public Api<Page<FinanceListResponse>> searchDeposit(FinProductType finProductType,
                                                        Authentication authentication,String keyword, Pageable pageable) {

        return Api.OK(financeService.searchFinancial(finProductType,userService.loadUserByEmail(authentication.getName()),keyword, pageable));
    }
}
