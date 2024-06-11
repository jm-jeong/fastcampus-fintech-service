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
    VOTE_ALREADY_EXIST(400, 1404, "이미 투표한 게시글입니다."),
    LIKED_PRODUCT_DUPLICATE(400, 1404, "동일한 금융상품 입니다."),
    LOUNGE_SEARCH_RESULT_NOT_FOUND(400, 1404, "검색 결과가 없습니다."),
    COMMENT_NOT_FOUND(400, 1404, "댓글을 찾을 수 없음."),
    COMMENT_NOT_OWNER(400, 1404, "댓글 작성자가 아닙니다."),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
