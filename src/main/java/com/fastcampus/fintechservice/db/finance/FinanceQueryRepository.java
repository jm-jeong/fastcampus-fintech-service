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
import org.springframework.data.domain.PageRequest;
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

        Pageable validPageable = validPageable(pageable, (int) totalCount);

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

        Pageable validPageable = validPageable(pageable, (int) totalCount);

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
        Pageable validPageable = validPageable(pageable, (int) totalCount);

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

        Pageable validPageable = validPageable(pageable, (int) totalCount);

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

    // 페이지 크게 입력하면 에러나는 오류 해결
    public Pageable validPageable(Pageable pageable, int totalCount) {
        // 유효한 페이지 번호 및 크기 검증
        int requestedPage = pageable.getPageNumber();
        int requestedSize = pageable.getPageSize();

        // 실제 데이터의 범위를 벗어나는 경우 기본값으로 대체
        int maxPage = (int) Math.ceil((double) totalCount / requestedSize);
        if (requestedPage > maxPage) {
            requestedPage = 0; // 첫 페이지로 대체
        }

        return PageRequest.of(requestedPage, requestedSize, pageable.getSort());
    }


}
