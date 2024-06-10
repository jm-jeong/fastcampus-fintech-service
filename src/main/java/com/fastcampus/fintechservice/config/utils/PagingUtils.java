package com.fastcampus.fintechservice.config.utils;

import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PagingUtils {


    public static Pageable validPageable(Pageable pageable, int totalCount) {
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
