package com.fastcampus.fintechservice.service;

import java.util.ArrayList;
import java.util.List;

import com.fastcampus.fintechservice.db.finance.FinanceQueryRepository;
import com.fastcampus.fintechservice.db.finance.FinanceSearchRepository;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.dto.request.FinanceListRequest;
import com.fastcampus.fintechservice.dto.response.FinanceListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.fintechservice.common.error.FinanceErrorCode;
import com.fastcampus.fintechservice.common.exception.ApiException;
import com.fastcampus.fintechservice.db.finance.DepositRepository;
import com.fastcampus.fintechservice.db.finance.SavingRepository;
import com.fastcampus.fintechservice.db.liked.LikedRepository;
import com.fastcampus.fintechservice.dto.FinanceDetailDto;
import com.fastcampus.fintechservice.dto.FinanceShowDto;
import com.fastcampus.fintechservice.dto.UserDto;
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
	private final LikedRepository likedRepository;
	private final FinanceQueryRepository financeQueryRepository;
	private final FinanceSearchRepository financeSearchRepository;

	//금융상품 상세
	public FinanceDetailResponse getFinanceDetail(FinanceDetailRequest financeDetailRequest, UserDto currentUser) {
		String financeType = financeDetailRequest.getType();
		String financeId = financeDetailRequest.getId();

		FinanceDetailDto financeDto = new FinanceDetailDto();//금융상품 상세
		List<FinanceShowDto> financeShowDtoList = new ArrayList<FinanceShowDto>();//함께보면 좋은 예금 상품 추천 리스트
		if (financeType.equals("DEPOSIT")) {
			financeDto = depositRepository.findById(financeId).map(FinanceDetailDto::fromDeposit)
				.orElseThrow(() -> new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND,
					String.format("Deposit 찾을 수 없음 Id : %s", financeId)));

			financeDto.setLiked(likedRepository.existsByIdAndDepositId(currentUser.id(), financeId));

			depositRepository.findTop5ByKorCoNm(financeDto.getKorCoNm()).stream()
				.map(FinanceShowDto::fromDeposit)
				.map(it -> it.setLiked(likedRepository.existsByIdAndDepositId(currentUser.id(), it.getFinanceId())))
				.forEach(financeShowDtoList::add);

		} else if (financeType.equals("SAVING")) {
			financeDto = savingRepository.findById(financeId).map(FinanceDetailDto::fromSaving)
				.orElseThrow(() -> new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND,
					String.format("Saving 찾을 수 없음 Id : %s", financeId)));

			financeDto.setLiked(likedRepository.existsByIdAndSavingId(currentUser.id(), financeId));

			savingRepository.findTop5ByKorCoNm(financeDto.getKorCoNm()).stream()
				.map(FinanceShowDto::fromSaving)
				.map(it -> it.setLiked(likedRepository.existsByIdAndSavingId(currentUser.id(), it.getFinanceId())))
				.forEach(financeShowDtoList::add);
		}

		return new FinanceDetailResponse(financeDto, financeShowDtoList);

	}


	@Transactional
	public Page<FinanceListResponse> getFinanceAll(FinProductType finProductType, Pageable pageable) {
		Page<FinanceListResponse> loungeList = null;
		if(finProductType.equals(FinProductType.DEPOSIT)) {
			loungeList = financeQueryRepository.findAllDeposit(pageable);
		}else if(finProductType.equals(FinProductType.SAVING)) {
			loungeList = financeQueryRepository.findAllSaving(pageable);
		}
		if(loungeList.isEmpty()) {
			throw new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND, "금융상품이 존재하지 않습니다.");
		}
		return loungeList;
	}

	//은행 선택 정렬
	@Transactional
	public Page<FinanceListResponse> getFinanceBankType(FinanceListRequest financeListRequest,Pageable pageable) {
		Page<FinanceListResponse> loungeList = null;
		if(financeListRequest.getFinProductType().equals(FinProductType.DEPOSIT)) {
			loungeList = financeQueryRepository.findAllDepositBankType(financeListRequest.getBankTypeList(), pageable);
		}else if(financeListRequest.getFinProductType().equals(FinProductType.SAVING)) {
			loungeList = financeQueryRepository.findAllSavingBankType(financeListRequest.getBankTypeList(), pageable);
		}
		if(loungeList.isEmpty()) {
			throw new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND, "금융상품이 존재하지 않습니다.");
		}
		return loungeList;
	}

	//금융상품 검색
	@Transactional
	public Page<FinanceListResponse> searchFinancial(FinProductType finProductType, String keyword, Pageable pageable) {
		Page<FinanceListResponse> financeListResponsePage = null;
		if(finProductType.equals(FinProductType.DEPOSIT)) {
			financeListResponsePage = financeSearchRepository.findAllDepositSearch(keyword, pageable);
		}else if(finProductType.equals(FinProductType.SAVING)) {
			financeListResponsePage = financeSearchRepository.findAllSavingSearch(keyword, pageable);
		}
		if(financeListResponsePage.isEmpty()) {
			throw new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND, "Deposit not found");
		}
		return financeListResponsePage;
	}
}
