package com.fastcampus.fintechservice.controller;


import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.dto.request.VoteRequest;
import com.fastcampus.fintechservice.dto.response.VoteResponse;
import com.fastcampus.fintechservice.service.UserService;
import com.fastcampus.fintechservice.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/vote")
@RestController
public class VoteController {
    private final VoteService voteService;
    private final UserService userService;


    @PostMapping
    public Api<VoteResponse> registerVote(@RequestBody VoteRequest voteRequest, Authentication authentication) {
        return Api.OK(voteService.registerVote(voteRequest,
                userService.loadUserByEmail(authentication.getName())));
    }

}
