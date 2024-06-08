package com.fastcampus.fintechservice.common.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoungeErrorCode implements ErrorCodeIfs{

    CLASS_NOT_FOUND(400, 1404, "클래스를 찾을 수 없음."),
    FIN_PRODUCT_NOT_FOUND(400, 1404, "금융 상품을 찾을 수 없음."),
    LOUNGE_POST_NOT_FOUND(400, 1404, "게시글을 찾을 수 없음."),
    DEPOSIT_NOT_FOUND(400, 1404, "예금 상품을 찾을 수 없음."),
    SAVING_NOT_FOUND(400, 1404, "적금 상품을 찾을 수 없음."),
    LIKED_PRODUCT_NOT_FOUND(400, 1404, "찜하기 리스트에서 동일한 상품을 찾을 수 없음."),
    VOTE_NOT_FOUND(400, 1404, "투표를 찾을 수 없음."),
    VOTE_ALREADY_EXIST(400, 1404, "이미 투표한 게시글입니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
