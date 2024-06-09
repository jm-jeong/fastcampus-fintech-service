package com.fastcampus.fintechservice.db.lounge;

import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.dto.response.LoungeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoungeQueryRepository {

    // 카테고리 분류 전체조회
    Page<LoungeResponse> findAllByFinProductType(FinProductType finProductType, Pageable pageable);
    // 전체 게시글 조회
    Page<LoungeResponse> findAllPosts(Pageable pageable);

    // 게시글 전체 검색
    Page<LoungeResponse> searchPosts(String keyword, Pageable pageable);
}
