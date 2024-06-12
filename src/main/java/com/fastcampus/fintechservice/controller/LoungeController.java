package com.fastcampus.fintechservice.controller;

import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.dto.request.LoungeRequest;
import com.fastcampus.fintechservice.dto.request.LoungeUpdateRequest;
import com.fastcampus.fintechservice.dto.response.LoungeResponse;
import com.fastcampus.fintechservice.dto.response.MessageResponse;
import com.fastcampus.fintechservice.service.LoungeService;
import com.fastcampus.fintechservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lounge")
public class LoungeController {
    private final LoungeService loungeService;
    private final UserService userService;

    // 게시글 등록하기
    @PostMapping
    public Api<LoungeResponse> registerPost(
            @RequestBody LoungeRequest loungeRequest, Authentication authentication) throws IOException {


        return Api.OK(loungeService.registerPost(loungeRequest,
                userService.loadUserByEmail(authentication.getName())));

    }

    // 게시글 단일 조회

    @GetMapping("/{postId}")
    public Api<LoungeResponse> getPost(@PathVariable Long postId) throws IOException {

        return Api.OK(loungeService.getPost(postId));
    }



    // 카테고리 분류 전체 조회

    @GetMapping
    public Api<Page<LoungeResponse>> getAllPosts(@RequestParam FinProductType finProductType,
                                                 Pageable pageable) {
        return Api.OK(loungeService.getAllCategoryPosts(finProductType, pageable));
    }
    // 전체 조회
    @GetMapping("/all")
    public Api<Page<LoungeResponse>> getAllPosts(Pageable pageable) {
        return Api.OK(loungeService.getAllPosts(pageable));
    }

    // 전체 검색
    @GetMapping("/search")
    public Api<Page<LoungeResponse>> searchPosts(@RequestParam String keyword, Pageable pageable) {
        return Api.OK(loungeService.searchPosts(keyword, pageable));
    }

    // 게시글 업데이트
    @PutMapping("/{postId}")
    public Api<LoungeResponse> updatePost(@PathVariable Long postId, @RequestBody LoungeUpdateRequest loungeUpdateRequest) throws IOException {
        return Api.OK(loungeService.updatePost(postId, loungeUpdateRequest));
    }


    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public Api<MessageResponse> deletePost(@PathVariable Long postId) {
        return Api.OK(loungeService.deletePost(postId));
    }


}
