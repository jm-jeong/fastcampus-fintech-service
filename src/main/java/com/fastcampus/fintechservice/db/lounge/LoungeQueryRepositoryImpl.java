package com.fastcampus.fintechservice.db.lounge;

import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.dto.response.LoungeResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Repository
public class LoungeQueryRepositoryImpl implements LoungeQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;
    QLounge lounge = QLounge.lounge;


    // 카테고리 분류
    @Override
    public Page<LoungeResponse> findAllByFinProductType(FinProductType finProductType, Pageable pageable) {
        long totalCount = jpaQueryFactory.selectFrom(lounge)
                .where(categoryEq(finProductType))
                .stream().count();

        List<Lounge> loungeResult = jpaQueryFactory.selectFrom(lounge)
                .where(categoryEq(finProductType))
                .orderBy(lounge.viewCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<LoungeResponse> responseDtoList;
        if (finProductType.equals(FinProductType.DEPOSIT)) {
            responseDtoList = loungeResult.stream()
                    .map(LoungeResponse::fromDepositList)
                    .toList();
        } else {
            responseDtoList = loungeResult.stream()
                    .map(LoungeResponse::fromSavingList)
                    .toList();
        }
        return new PageImpl<>(responseDtoList, pageable, totalCount);
    }
    // 전체 게시글 조회
    @Override
    public Page<LoungeResponse> findAllPosts(Pageable pageable) {
        long totalCount = jpaQueryFactory.selectFrom(lounge)
                .stream().count();

        List<Lounge> loungeResult = jpaQueryFactory.selectFrom(lounge)
                .orderBy(lounge.viewCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return getLoungeResponses(pageable, totalCount, loungeResult);
    }

    // 전체 게시글 조회 검색
    @Override
    public Page<LoungeResponse> searchPosts(String keyword, Pageable pageable) {
        long totalCount = jpaQueryFactory.selectFrom(lounge)
                .where(keywordEq(keyword))
                .stream().count();

        List<Lounge> loungeResult = jpaQueryFactory.selectFrom(lounge)
                .where(keywordEq(keyword))
                .orderBy(lounge.viewCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return getLoungeResponses(pageable, totalCount, loungeResult);
    }

    @NotNull
    private Page<LoungeResponse> getLoungeResponses(Pageable pageable, long totalCount, List<Lounge> loungeResult) {
        if (lounge.finProductType.equals(FinProductType.DEPOSIT.toString())) {
            List<LoungeResponse> responseDtoList = loungeResult.stream()
                    .map(LoungeResponse::fromDepositList)
                    .toList();
            return new PageImpl<>(responseDtoList, pageable, totalCount);
        }
        List<LoungeResponse> responseDtoList = loungeResult.stream()
                .map(LoungeResponse::fromSavingList)
                .collect(Collectors.toList());
        return new PageImpl<>(responseDtoList, pageable, totalCount);
    }


    private BooleanExpression categoryEq(FinProductType category) {
        if (category == null) {
            return null;
        }
        return lounge.finProductType.eq(category);
    }

    private BooleanExpression keywordEq(String keyword) {
        if(keyword == null) {
            return null;
        }else if (keyword == ""){
            return null;
        }
        return (lounge.title.contains(keyword));
        //.or(post.content.contains(keyword)
    }
}
