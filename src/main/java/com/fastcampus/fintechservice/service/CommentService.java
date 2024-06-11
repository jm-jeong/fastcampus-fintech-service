package com.fastcampus.fintechservice.service;


import com.fastcampus.fintechservice.common.error.LoungeErrorCode;
import com.fastcampus.fintechservice.common.exception.ApiException;
import com.fastcampus.fintechservice.db.lounge.Comment;
import com.fastcampus.fintechservice.db.lounge.CommentRepository;
import com.fastcampus.fintechservice.db.lounge.Lounge;
import com.fastcampus.fintechservice.db.lounge.LoungeRepository;
import com.fastcampus.fintechservice.db.user.UserAccount;
import com.fastcampus.fintechservice.dto.UserDto;
import com.fastcampus.fintechservice.dto.request.CommentEditRequest;
import com.fastcampus.fintechservice.dto.request.CommentRequest;
import com.fastcampus.fintechservice.dto.response.CommentResponse;
import com.fastcampus.fintechservice.dto.response.MessageResponse;
import com.fastcampus.fintechservice.dto.response.ReplyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final LoungeRepository loungeRepository;


    @Transactional
    public CommentResponse createComment(CommentRequest commentRequest, UserDto userDto) {
        // post 유효성 검사
        Lounge lounge = validatePost(commentRequest.getPostId());
        Comment comment = commentFromRequest(commentRequest, lounge, userDto.toEntity());
        commentRepository.save(comment);

        return CommentResponse.from(comment);
    }

    @Transactional
    public CommentResponse createReply(ReplyRequest replyRequest, UserDto userDto) {
        Comment parentComment = validateComment(replyRequest.getParentCommentId());
        Comment reply = replyFromRequest(replyRequest, parentComment, userDto.toEntity());
        commentRepository.save(reply);

        return CommentResponse.from(reply);
    }

    private Comment commentFromRequest(CommentRequest commentRequest,
                                       Lounge lounge, UserAccount user) {
        return Comment.builder()
                .user(user)
                .lounge(lounge)
                .content(commentRequest.getContent())
                .depth(0)
                .build();
    }

    private Comment replyFromRequest(ReplyRequest replyRequest, Comment parentComment, UserAccount user) {
        return Comment.builder()
                .user(user)
                .parentComment(parentComment)
                .content(replyRequest.getContent())
                .depth(parentComment.getDepth() + 1)
                .build();
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAllCommentsByPost(Long postId) {
        Lounge lounge = validatePost(postId);
        List<Comment> comments = commentRepository.findAllByCommentWithLounge(lounge);
        return convertToCommentResponses(comments);
    }

    private List<CommentResponse> convertToCommentResponses(List<Comment> comments) {
        return comments.stream()
                .filter(comment -> comment.getDepth() == 0)
                .map(this::buildCommentReplyResponse)
                .collect(Collectors.toList());
    }

    private CommentResponse buildCommentReplyResponse(Comment comment) {
        List<CommentResponse> replies = getChildComments(comment);
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .username(comment.getUser().getName())
                .createdDate(comment.getCreatedAt())
                .updatedDate(comment.getModifiedAt())
                .children(replies)
                .build();
    }

    private List<CommentResponse> getChildComments(Comment comment) {
        return comment.getChildren().stream()
                .map(this::buildCommentReplyResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponse updateComment(Long commentId, CommentEditRequest request, UserDto userDto) {
        // comment validate
        Comment comment = validateComment(commentId);
        // 게시글 유효성체크
        validatePost(comment.getLounge().getId());

        // user validate
        validateUser(comment, userDto.toEntity());

        comment.updateComment(request);

        return CommentResponse.from(comment);
    }

    @Transactional
    public MessageResponse deleteComment(Long commentId, UserDto userDto) {
        Comment comment = validateComment(commentId);
        validateUser(comment, userDto.toEntity());

        commentRepository.delete(comment);
        return new MessageResponse("Comment deleted successfully.");
    }

    public void validateUser(Comment comment, UserAccount user) {
        if (!comment.validateUser(user)) {
            throw new ApiException(LoungeErrorCode.COMMENT_NOT_OWNER, String.format("Comment is not %s",user.getEmail()));
        }
    }

    public Comment validateComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            return comment.get();
        }
        throw new ApiException(LoungeErrorCode.COMMENT_NOT_FOUND, String.format("Comment not found. commentId: %d", commentId));
    }

    public Lounge validatePost(Long postId) {
        Optional<Lounge> lounge = loungeRepository.findById(postId);
        if (lounge.isPresent()) {
            return lounge.get();
        }
        throw new ApiException(LoungeErrorCode.LOUNGE_POST_NOT_FOUND, String.format("Post not found. postId: %d", postId));
    }

}





