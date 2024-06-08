package com.fastcampus.fintechservice.controller;

import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.dto.PageDto;
import com.fastcampus.fintechservice.dto.request.LoungeRequest;
import com.fastcampus.fintechservice.dto.response.LoungeResponse;
import com.fastcampus.fintechservice.dto.response.MessageResponse;
import com.fastcampus.fintechservice.service.LoungeService;
import com.fastcampus.fintechservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lounge")
public class LoungeController {
    private final LoungeService loungeService;
    private final UserService userService;
    @PostMapping
    public Api<LoungeResponse> registerPost(
            @RequestBody LoungeRequest loungeRequest, Authentication authentication) throws IOException {


        return Api.OK(loungeService.registerPost(loungeRequest,
                userService.loadUserByEmail(authentication.getName())));

    }


    @GetMapping("/{postId}")
    public Api<LoungeResponse> getPost(@PathVariable Long postId) throws IOException {

        return Api.OK(loungeService.getPost(postId));
    }




    @GetMapping
    public Api<Page<LoungeResponse>> getAllPosts(@RequestBody PageDto pageDto) throws IOException {
        return Api.OK(loungeService.getAllPosts(pageDto));
    }

    @PutMapping("/{postId}")
    public Api<LoungeResponse> updatePost(@PathVariable Long postId, @RequestBody LoungeRequest loungeRequest) throws IOException {
        return Api.OK(loungeService.updatePost(postId, loungeRequest));
    }

    @DeleteMapping("/{postId}")
    public Api<MessageResponse> deletePost(@PathVariable Long postId) {
        return Api.OK(loungeService.deletePost(postId));
    }

}
