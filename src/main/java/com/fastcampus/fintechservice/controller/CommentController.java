package com.fastcampus.fintechservice.controller;

import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.dto.request.CommentEditRequest;
import com.fastcampus.fintechservice.dto.request.CommentRequest;
import com.fastcampus.fintechservice.dto.response.CommentResponse;
import com.fastcampus.fintechservice.dto.response.MessageResponse;
import com.fastcampus.fintechservice.dto.response.ReplyRequest;
import com.fastcampus.fintechservice.service.CommentService;
import com.fastcampus.fintechservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lounge/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping
    public Api<CommentResponse> registerComment(@RequestBody CommentRequest commentRequest,
                                                Authentication authentication) {

        return Api.OK(commentService.createComment(
                commentRequest, userService.loadUserByEmail(authentication.getName())));
    }

    @PostMapping("/{parentCommentId}/reply")
    public Api<CommentResponse> registerReply(@RequestBody ReplyRequest replyRequest,
                                              Authentication authentication) {

        return Api.OK(commentService.createReply(
                replyRequest, userService.loadUserByEmail(authentication.getName())));
    }

    @PutMapping("/{commentId}")
    public Api<CommentResponse> updateComment(@RequestBody CommentEditRequest commentEditRequest,
                                              Authentication authentication) {

        return Api.OK(commentService.updateComment(
                commentEditRequest, userService.loadUserByEmail(authentication.getName())));
    }

    @DeleteMapping("/{commentId}")
    public Api<MessageResponse> deleteComment(@PathVariable Long commentId, Authentication authentication) {

        return Api.OK(commentService.deleteComment(
                commentId, userService.loadUserByEmail(authentication.getName())));
    }

    @GetMapping("/{postId}")
    public Api<List<CommentResponse>> getAllComments(@PathVariable Long postId) {
        return Api.OK(commentService.getAllCommentsByPost(postId));
    }
}
