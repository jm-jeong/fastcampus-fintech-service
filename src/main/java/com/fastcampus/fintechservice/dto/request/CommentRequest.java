package com.fastcampus.fintechservice.dto.request;

import com.fastcampus.fintechservice.db.lounge.Comment;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    private Long postId;
    private String content;
    private Integer depth;

}