package com.fastcampus.fintechservice.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LikedErrorCode implements ErrorCodeIfs {

    NOT_FOUND_FIN_PRODUCT(400, 1404, "금융 상품을 찾을 수 없음."),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
