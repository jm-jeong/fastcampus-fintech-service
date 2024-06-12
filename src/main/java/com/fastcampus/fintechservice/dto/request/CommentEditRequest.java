package com.fastcampus.fintechservice.dto.request;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentEditRequest {

    private Long commentId;
    private String content;
}
