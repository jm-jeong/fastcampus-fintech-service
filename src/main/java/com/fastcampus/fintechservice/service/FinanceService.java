package com.fastcampus.fintechservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.fintechservice.common.error.FinanceErrorCode;
import com.fastcampus.fintechservice.common.exception.ApiException;
import com.fastcampus.fintechservice.db.finance.DepositRepository;
import com.fastcampus.fintechservice.db.finance.SavingRepository;
import com.fastcampus.fintechservice.dto.FinanceDetailDto;
import com.fastcampus.fintechservice.dto.FinanceShowDto;
import com.fastcampus.fintechservice.dto.request.FinanceDetailRequest;
import com.fastcampus.fintechservice.dto.response.FinanceDetailResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FinanceService {

	private final DepositRepository depositRepository;
	private final SavingRepository savingRepository;

	//금융상품 상세
	public FinanceDetailResponse getFinanceDetail(FinanceDetailRequest financeDetailRequest) {
		String financeType = financeDetailRequest.getFinanceType();
		String financeId = financeDetailRequest.getFinanceId();

		FinanceDetailDto financeDto = new FinanceDetailDto();//금융상품 상세
		List<FinanceShowDto> financeShowDtoList = new ArrayList<FinanceShowDto>();//함께보면 좋은 예금 상품 추천 리스트
		if (financeType.equals("deposit")) {
			financeDto = depositRepository.findById(financeId).map(FinanceDetailDto::fromDeposit)
				.orElseThrow(() -> new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND,
					String.format("Deposit 찾을 수 없음 Id : %s", financeId)));
			depositRepository.findTop5ByKorCoNm(financeDto.getKorCoNm()).stream()
				.map(FinanceShowDto::fromDeposit)
				.forEach(financeShowDtoList::add);
		} else if (financeType.equals("saving")) {
			financeDto = savingRepository.findById(financeId).map(FinanceDetailDto::fromSaving)
				.orElseThrow(() -> new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND,
					String.format("Saving 찾을 수 없음 Id : %s", financeId)));
			savingRepository.findTop5ByKorCoNm(financeDto.getKorCoNm()).stream()
				.map(FinanceShowDto::fromSaving)
				.forEach(financeShowDtoList::add);
		}

		return new FinanceDetailResponse(financeDto, financeShowDtoList);

	}
}
