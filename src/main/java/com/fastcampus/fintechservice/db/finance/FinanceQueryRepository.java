package com.fastcampus.fintechservice.db.finance;

import com.fastcampus.fintechservice.config.utils.PagingUtils;
import com.fastcampus.fintechservice.db.finance.enums.BankType;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.db.lounge.Lounge;
import com.fastcampus.fintechservice.dto.response.FinanceListResponse;
import com.fastcampus.fintechservice.dto.response.LoungeResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Repository
public class FinanceQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;
    QDeposit deposit = QDeposit.deposit;
    QSaving saving = QSaving.saving;



    // 예금상품 전체조회
    public Page<FinanceListResponse> findAllDeposit(Pageable pageable) {
        long totalCount = jpaQueryFactory.selectFrom(deposit).stream().count();

        Pageable validPageable = PagingUtils.validPageable(pageable, (int) totalCount);

        List<Deposit> loungeResult = jpaQueryFactory.selectFrom(deposit)
                .orderBy(deposit.likedCount.desc())
                .offset(validPageable.getOffset())
                .limit(validPageable.getPageSize())
                .fetch();

        List<FinanceListResponse> responseDtoList = loungeResult.stream()
                .map(FinanceListResponse::fromDepositProductList)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, validPageable, totalCount);
    }


    // 적금상품 전체조회
    public Page<FinanceListResponse> findAllSaving(Pageable pageable) {
        long totalCount = jpaQueryFactory.selectFrom(saving)
                .stream().count();

        Pageable validPageable = PagingUtils.validPageable(pageable, (int) totalCount);

        List<Saving> loungeResult = jpaQueryFactory.selectFrom(saving)
                .orderBy(saving.likedCount.desc())
                .offset(validPageable.getOffset())
                .limit(validPageable.getPageSize())
                .fetch();

        List<FinanceListResponse> responseDtoList;
        responseDtoList = loungeResult.stream()
                .map(FinanceListResponse::fromSavingProductList)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, validPageable, totalCount);
    }




    // 은행별 예금 조회
    public Page<FinanceListResponse> findAllDepositBankType(List<BankType> bankList, Pageable pageable) {

        BooleanExpression bankTypeFilter =
                deposit.bank.korCoNm.in(bankList.stream()
                        .map(BankType::getName).toList());

        long totalCount = jpaQueryFactory.selectFrom(deposit)
                .where(bankTypeFilter)
                .stream().count();
        Pageable validPageable = PagingUtils.validPageable(pageable, (int) totalCount);

        List<Deposit> loungeResult = jpaQueryFactory.selectFrom(deposit)
                .where(bankTypeFilter)
                .orderBy(deposit.likedCount.desc())
                .offset(validPageable.getOffset())
                .limit(validPageable.getPageSize())
                .fetch();

        List<FinanceListResponse> responseDtoList = loungeResult.stream()
                .map(FinanceListResponse::fromDepositProductList)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, validPageable, totalCount);
    }

    // 은행별 적금 조회

    public Page<FinanceListResponse> findAllSavingBankType(List<BankType> bankList, Pageable pageable) {
        BooleanExpression bankTypeFilter =
                saving.bank.korCoNm.in(bankList.stream()
                        .map(BankType::getName).toList());

        long totalCount = jpaQueryFactory.selectFrom(saving)
                .where(bankTypeFilter)
                .stream().count();

        Pageable validPageable = PagingUtils.validPageable(pageable, (int) totalCount);

        List<Saving> loungeResult = jpaQueryFactory.selectFrom(saving)
                .where(bankTypeFilter)
                .orderBy(saving.likedCount.desc())
                .offset(validPageable.getOffset())
                .limit(validPageable.getPageSize())
                .fetch();

        List<FinanceListResponse> responseDtoList = loungeResult.stream()
                .map(FinanceListResponse::fromSavingProductList)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, validPageable, totalCount);
    }


    public Page<FinanceListResponse> findAllFinanceProducts(Pageable pageable) {
        long depositTotalCount = jpaQueryFactory.selectFrom(deposit).stream().count();
        long savingTotalCount = jpaQueryFactory.selectFrom(saving).stream().count();

        long totalCount = depositTotalCount + savingTotalCount;
        Pageable validPageable = PagingUtils.validPageable(pageable, (int) totalCount);

        // 예금 상품 조회
        List<Deposit> depositResult = jpaQueryFactory.selectFrom(deposit)
                .orderBy(deposit.likedCount.desc())
                .offset(validPageable.getOffset())
                .limit(validPageable.getPageSize())
                .fetch();

        // 적금 상품 조회
        List<Saving> savingResult = jpaQueryFactory.selectFrom(saving)
                .orderBy(saving.likedCount.desc())
                .offset(validPageable.getOffset())
                .limit(validPageable.getPageSize())
                .fetch();

        // 예금 및 적금 상품 결과를 하나의 리스트로 통합
        List<FinanceListResponse> responseDtoList = new ArrayList<>();
        responseDtoList.addAll(depositResult.stream()
                .map(FinanceListResponse::fromDepositProductList)
                .toList());
        responseDtoList.addAll(savingResult.stream()
                .map(FinanceListResponse::fromSavingProductList)
                .toList());

        // 통합된 결과를 페이징 처리하여 반환
        responseDtoList.sort(Comparator.comparing(FinanceListResponse::getLikeCount).reversed());
        return new PageImpl<>(responseDtoList, validPageable, totalCount);

    }


}
