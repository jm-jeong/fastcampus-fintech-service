package com.fastcampus.fintechservice.db.finance;

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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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

        List<Deposit> loungeResult = jpaQueryFactory.selectFrom(deposit)
                .orderBy(deposit.likedCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<FinanceListResponse> responseDtoList = loungeResult.stream()
                .map(FinanceListResponse::fromDepositProductList)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, pageable, totalCount);
    }


    // 적금상품 전체조회
    public Page<FinanceListResponse> findAllSaving(Pageable pageable) {
        long totalCount = jpaQueryFactory.selectFrom(saving)
                .stream().count();

        List<Saving> loungeResult = jpaQueryFactory.selectFrom(saving)
                .orderBy(saving.likedCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<FinanceListResponse> responseDtoList;
        responseDtoList = loungeResult.stream()
                .map(FinanceListResponse::fromSavingProductList)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, pageable, totalCount);
    }



    // 은행별 예금 조회
    public Page<FinanceListResponse> findAllDepositBankType(List<BankType> bankList, Pageable pageable) {

        BooleanExpression bankTypeFilter =
                deposit.bank.korCoNm.in(bankList.stream()
                        .map(BankType::getName).toList());

        long totalCount = jpaQueryFactory.selectFrom(deposit)
                .where(bankTypeFilter)
                .stream().count();

        List<Deposit> loungeResult = jpaQueryFactory.selectFrom(deposit)
                .where(bankTypeFilter)
                .orderBy(deposit.likedCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<FinanceListResponse> responseDtoList = loungeResult.stream()
                .map(FinanceListResponse::fromDepositProductList)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, pageable, totalCount);
    }

    // 은행별 적금 조회

    public Page<FinanceListResponse> findAllSavingBankType(List<BankType> bankList, Pageable pageable) {
        BooleanExpression bankTypeFilter =
                saving.bank.korCoNm.in(bankList.stream()
                        .map(BankType::getName).toList());

        long totalCount = jpaQueryFactory.selectFrom(saving)
                .where(bankTypeFilter)
                .stream().count();

        List<Saving> loungeResult = jpaQueryFactory.selectFrom(saving)
                .where(bankTypeFilter)
                .orderBy(saving.likedCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<FinanceListResponse> responseDtoList = loungeResult.stream()
                .map(FinanceListResponse::fromSavingProductList)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, pageable, totalCount);
    }


}
