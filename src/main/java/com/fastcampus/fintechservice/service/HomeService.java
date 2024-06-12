package com.fastcampus.fintechservice.service;

import com.fastcampus.fintechservice.common.error.FinanceErrorCode;
import com.fastcampus.fintechservice.common.exception.ApiException;
import com.fastcampus.fintechservice.db.finance.FinanceQueryRepository;
import com.fastcampus.fintechservice.db.finance.FinanceSearchRepository;
import com.fastcampus.fintechservice.dto.response.FinanceListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class HomeService {

    private final FinanceQueryRepository financeQueryRepository;
    private final FinanceSearchRepository financeSearchRepository;


    @Transactional
    public Page<FinanceListResponse> getAllFinanceProduct(Pageable pageable) {
        Page<FinanceListResponse> result =
                financeQueryRepository.findAllFinanceProducts(pageable);
        if(result.isEmpty()) {
            throw new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND);
        }
        return result;
    }


    @Transactional
    public Page<FinanceListResponse> getAllFinanceProductSearch(String keyword, Pageable pageable) {
        Page<FinanceListResponse> result =
                financeSearchRepository.searchAllFinanceProducts(keyword,pageable);
        if(result.isEmpty()) {
            throw new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND);
        }
        return result;
    }
}
