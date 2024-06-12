package com.fastcampus.fintechservice.dto.response;


import com.fastcampus.fintechservice.db.lounge.Comment;
import com.fastcampus.fintechservice.db.user.UserAccount;
import com.fastcampus.fintechservice.dto.UserDto;
import com.fastcampus.fintechservice.dto.request.CommentRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {
    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer depth;
    private List<CommentResponse> children = new ArrayList<>();


    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .username(comment.getUser().getName())
                .content(comment.getContent())
                .createdDate(comment.getCreatedAt())
                .updatedDate(comment.getModifiedAt())
                .depth(comment.getDepth())
                .build();

    }



}
