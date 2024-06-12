package com.fastcampus.fintechservice.service;

import com.fastcampus.fintechservice.common.error.FinanceErrorCode;
import com.fastcampus.fintechservice.common.exception.ApiException;
import com.fastcampus.fintechservice.db.finance.FinanceQueryRepository;
import com.fastcampus.fintechservice.db.finance.FinanceSearchRepository;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.dto.UserDto;
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
    public Page<FinanceListResponse> searchFinancial(FinProductType finProductType,String keyword,
                                                     Pageable pageable) {
        Page<FinanceListResponse> financeListResponsePage = null;
        if(finProductType.equals(FinProductType.DEPOSIT)) {
            financeListResponsePage = financeSearchRepository.findAllDepositSearch(keyword, pageable);
        } else if(finProductType.equals(FinProductType.SAVING)) {
            financeListResponsePage = financeSearchRepository.findAllSavingSearch(keyword, pageable);
        }
        if(financeListResponsePage.isEmpty()) {
            throw new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND, String.format("keyword not found %s", keyword));
        }
        return financeListResponsePage;
    }
}
