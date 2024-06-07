package com.fastcampus.fintechservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.fintechservice.common.error.FinanceErrorCode;
import com.fastcampus.fintechservice.common.exception.ApiException;
import com.fastcampus.fintechservice.db.finance.DepositRepository;
import com.fastcampus.fintechservice.db.finance.SavingRepository;
import com.fastcampus.fintechservice.dto.FinanceDto;
import com.fastcampus.fintechservice.dto.request.FinanceCompareRequest;
import com.fastcampus.fintechservice.dto.response.FinanceCompareResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CompareService {
	private final DepositRepository depositRepository;
	private final SavingRepository savingRepository;

	public FinanceCompareResponse getFinanceData(FinanceCompareRequest financeCompareRequest) {
		String financeType = financeCompareRequest.getType();
		String financeId1 = financeCompareRequest.getId1();
		String financeId2 = financeCompareRequest.getId2();

		FinanceDto financeDto1 = new FinanceDto();
		FinanceDto financeDto2 = new FinanceDto();

		if (financeType.equals("DEPOSIT")) {
			financeDto1 = depositRepository.findById(financeId1).map(FinanceDto::fromDeposit)
				.orElseThrow(() -> new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND,
					String.format("Deposit 찾을 수 없음 Id : %s", financeId1)));
			financeDto2 = depositRepository.findById(financeId2).map(FinanceDto::fromDeposit)
				.orElseThrow(() -> new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND,
					String.format("Deposit 찾을 수 없음 Id : %s", financeId1)));
		} else if (financeType.equals("SAVING")) {
			financeDto1 = savingRepository.findById(financeId1).map(FinanceDto::fromSaving)
				.orElseThrow(() -> new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND,
					String.format("Saving 찾을 수 없음 Id : %s", financeId1)));
			financeDto2 = savingRepository.findById(financeId2).map(FinanceDto::fromSaving)
				.orElseThrow(() -> new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND,
					String.format("Saving 찾을 수 없음 Id : %s", financeId1)));
		}

		FinanceCompareResponse financeCompareResponse = new FinanceCompareResponse();
		financeCompareResponse.dtoListFromFinance(financeDto1, financeDto2);

		return financeCompareResponse;
	}
}
