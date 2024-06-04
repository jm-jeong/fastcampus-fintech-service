package com.fastcampus.fintechservice.controller;

import com.fastcampus.fintechservice.dto.request.LoungeRequestDto;
import com.fastcampus.fintechservice.dto.response.LoungeResponse;
import com.fastcampus.fintechservice.service.LoungeService;
import com.fastcampus.fintechservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lounge")
public class LoungeController {
    private final LoungeService loungeService;
    private final UserService userService;
    @PostMapping
    public LoungeResponse registerPost(
            @RequestBody LoungeRequestDto loungeRequestDto, Authentication authentication) {


        return loungeService.registerPost(loungeRequestDto,
                userService.loadUserByEmail(authentication.getName()));

    }


    @GetMapping("/{postId}")
    public LoungeResponse getPost(@PathVariable Long postId) {
        return loungeService.getPost(postId);
    }

//    @GetMapping
//    public LoungeResponseDto getAllPost() {
//        return LoungeResponseDto.from(loungeService.getAllPost());
//    }

    @PutMapping("/{postId}")
    public LoungeResponse updatePost(@PathVariable Long postId, @RequestBody LoungeRequestDto loungeRequestDto) {
        return loungeService.updatePost(postId, loungeRequestDto);
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId) {
        return loungeService.deletePost(postId);
    }

}
