package com.fastcampus.fintechservice.controller;

import com.fastcampus.fintechservice.config.utils.UserDetailsImpl;
import com.fastcampus.fintechservice.dto.request.LoungeRequestDto;
import com.fastcampus.fintechservice.dto.response.LoungeResponseDto;
import com.fastcampus.fintechservice.service.LoungeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lounge")
public class LoungeController {
    private final LoungeService loungeService;

    @PostMapping
    public LoungeResponseDto registerPost(
            @RequestPart(value = "data") LoungeRequestDto loungeRequestDto) {

        return loungeService.registerPost(loungeRequestDto);
    }


    @GetMapping("/{postId}")
    public LoungeResponseDto getPost(@PathVariable Long postId) {
        return loungeService.getPost(postId);
    }

//    @GetMapping
//    public LoungeResponseDto getAllPost() {
//        return LoungeResponseDto.from(loungeService.getAllPost());
//    }

    @PutMapping("/{postId}")
    public LoungeResponseDto updatePost(@PathVariable Long postId, @RequestBody LoungeRequestDto loungeRequestDto) {
        return loungeService.updatePost(postId, loungeRequestDto);
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId) {
        return loungeService.deletePost(postId);
    }

}
