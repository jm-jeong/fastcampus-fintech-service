package com.fastcampus.fintechservice.controller;

import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.dto.response.LikedResponse;
import com.fastcampus.fintechservice.dto.response.MessageResponse;
import com.fastcampus.fintechservice.service.LikedService;
import com.fastcampus.fintechservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/liked")
public class LikedController {

    private final LikedService likedService;
    private final UserService userService;


    // 금융 상품 찜하기
    @PostMapping
    public Api<LikedResponse> registerLike(@RequestParam Long id, @RequestParam FinProductType type,
                                           Authentication authentication) {
        return Api.OK(likedService.registerLike(
                id, type, userService.loadUserByEmail(authentication.getName()))
        );
    }


    // 내가 찜한 상품 목록 가져오기
    @PostMapping("/me")
    public Api<List<LikedResponse>> getLikeList(Authentication authentication) {
        return Api.OK(likedService.getLikedList(
                userService.loadUserByEmail(authentication.getName()))
        );
    }

    // 찜한 상품 삭제
    @DeleteMapping
    public Api<MessageResponse> deleteLike(@RequestParam Long id, @RequestParam FinProductType type,
                                                    Authentication authentication) {
        return Api.OK(likedService.removeLiked(
                id, type, userService.loadUserByEmail(authentication.getName()))
        );
    }
}
