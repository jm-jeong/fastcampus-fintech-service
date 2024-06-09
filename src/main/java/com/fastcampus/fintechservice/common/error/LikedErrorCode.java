package com.fastcampus.fintechservice.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LikedErrorCode implements ErrorCodeIfs {

    NOT_FOUND_FIN_PRODUCT(400, 1404, "금융 상품을 찾을 수 없음."),

    ALREADY_LIKED_FINANCE_DEPOSIT(400, 1405, "이미 찜한 예금 상품입니다."),
    ALREADY_LIKED_FINANCE_SAVING(400, 1406, "이미 찜한 적금 상품입니다."),
    NOT_FOUND_FINANCE_TYPE(400, 1407, "존재하지 않는 상품 타입임");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
