package com.fastcampus.fintechservice.service;

import java.util.ArrayList;
import java.util.List;

import com.fastcampus.fintechservice.db.finance.FinanceQueryRepository;
import com.fastcampus.fintechservice.db.finance.FinanceSearchRepository;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.db.user.UserAccount;
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
	public Page<FinanceListResponse> getFinanceAll(FinProductType finProductType, UserDto userDto, Pageable pageable) {
		Page<FinanceListResponse> responsePage = null;
		if(finProductType.equals(FinProductType.DEPOSIT)) {
			responsePage = financeQueryRepository.findAllDeposit(pageable);
			for (FinanceListResponse response : responsePage.getContent()) {
				boolean isLiked = likeCheck(response, userDto.toEntity(), FinProductType.DEPOSIT);
				response.getFinanceDetailDto().setLiked(isLiked);
			}
		} else if(finProductType.equals(FinProductType.SAVING)) {
			responsePage = financeQueryRepository.findAllSaving(pageable);
			for (FinanceListResponse response : responsePage.getContent()) {
				boolean isLiked = likeCheck(response, userDto.toEntity(), FinProductType.SAVING);
				response.getFinanceDetailDto().setLiked(isLiked);
			}
		}
		if(responsePage.isEmpty()) {
			throw new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND, "금융상품이 존재하지 않습니다.");
		}
		return responsePage;
	}

	public boolean likeCheck(FinanceListResponse response, UserAccount user, FinProductType finProductType) {
		if (finProductType.equals(FinProductType.DEPOSIT)) {
			return likedRepository.existsByIdAndDepositId(user.getId(), response.getFinanceDetailDto().getFinanceId());
		} else {
			return likedRepository.existsByIdAndSavingId(user.getId(), response.getFinanceDetailDto().getFinanceId());
		}
	}


	//은행 선택 정렬
	@Transactional
	public Page<FinanceListResponse> getFinanceBankType(FinanceListRequest financeListRequest, UserDto userDto, Pageable pageable) {
		Page<FinanceListResponse> responsePage = null;
		if(financeListRequest.getFinProductType().equals(FinProductType.DEPOSIT)) {
			responsePage = financeQueryRepository.findAllDepositBankType(financeListRequest.getBankTypeList(), pageable);
			for (FinanceListResponse response : responsePage.getContent()) {
				boolean isLiked = likeCheck(response, userDto.toEntity(), FinProductType.DEPOSIT);
				response.getFinanceDetailDto().setLiked(isLiked);
			}
		} else if(financeListRequest.getFinProductType().equals(FinProductType.SAVING)) {
			responsePage = financeQueryRepository.findAllSavingBankType(financeListRequest.getBankTypeList(), pageable);
			for (FinanceListResponse response : responsePage.getContent()) {
				boolean isLiked = likeCheck(response, userDto.toEntity(), FinProductType.SAVING);
				response.getFinanceDetailDto().setLiked(isLiked);
			}
		}
		if(responsePage.isEmpty()) {
			throw new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND, "금융상품이 존재하지 않습니다.");
		}
		return responsePage;
	}

	@Transactional
	public Page<FinanceListResponse> searchFinancial(FinProductType finProductType, UserDto userDto,
													 String keyword, Pageable pageable) {
		Page<FinanceListResponse> financeListResponsePage = null;
		if(finProductType.equals(FinProductType.DEPOSIT)) {
			financeListResponsePage = financeSearchRepository.findAllDepositSearch(keyword, pageable);
			for (FinanceListResponse response : financeListResponsePage.getContent()) {
				boolean isLiked = likeCheck(response, userDto.toEntity(), FinProductType.DEPOSIT);
				response.getFinanceDetailDto().setLiked(isLiked);
			}
		} else if(finProductType.equals(FinProductType.SAVING)) {
			financeListResponsePage = financeSearchRepository.findAllSavingSearch(keyword, pageable);
			for (FinanceListResponse response : financeListResponsePage.getContent()) {
				boolean isLiked = likeCheck(response, userDto.toEntity(), FinProductType.SAVING);
				response.getFinanceDetailDto().setLiked(isLiked);
			}
		}
		if(financeListResponsePage.isEmpty()) {
			throw new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND, "Deposit not found");
		}
		return financeListResponsePage;
	}
}
