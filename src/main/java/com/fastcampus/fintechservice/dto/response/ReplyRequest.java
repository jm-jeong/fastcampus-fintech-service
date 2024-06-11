package com.fastcampus.fintechservice.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplyRequest {
    private Long parentCommentId;
    private String content;
}
