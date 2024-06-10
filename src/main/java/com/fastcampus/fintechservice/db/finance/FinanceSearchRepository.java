package com.fastcampus.fintechservice.db.finance;

import com.fastcampus.fintechservice.config.utils.PagingUtils;
import com.fastcampus.fintechservice.dto.response.FinanceListResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class FinanceSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;
    QDeposit deposit = QDeposit.deposit;
    QSaving saving = QSaving.saving;


    public Page<FinanceListResponse> findAllDepositSearch(String keyword, Pageable pageable) {
        long totalCount = jpaQueryFactory.selectFrom(deposit)
                .where(depositKeywordEq(keyword))
                .stream().count();

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

    // 적금 상품 검색
    public Page<FinanceListResponse> findAllSavingSearch(String keyword, Pageable pageable) {
        long totalCount = jpaQueryFactory.selectFrom(saving)
                .where(savingKeywordEq(keyword))
                .stream().count();

        Pageable validPageable = PagingUtils.validPageable(pageable, (int) totalCount);

        List<Saving> loungeResult = jpaQueryFactory.selectFrom(saving)
                .where(savingKeywordEq(keyword))
                .orderBy(saving.likedCount.desc())
                .offset(validPageable.getOffset())
                .limit(validPageable.getPageSize())
                .fetch();

        List<FinanceListResponse> responseDtoList = loungeResult.stream()
                .map(FinanceListResponse::fromSavingProductList)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, validPageable, totalCount);
    }


    private BooleanExpression depositKeywordEq(String keyword) {
        if (keyword == null) {
            return null;
        } else if (keyword.isEmpty()) {
            return null;
        }
        return (deposit.finPrdtNm.contains(keyword));

    }

    private BooleanExpression savingKeywordEq(String keyword) {
        if (keyword == null) {
            return null;
        } else if (keyword.isEmpty()) {
            return null;
        }
        return (saving.finPrdtNm.contains(keyword));

    }
}
