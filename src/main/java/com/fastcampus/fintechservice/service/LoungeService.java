package com.fastcampus.fintechservice.service;

import com.fastcampus.fintechservice.common.error.ErrorCode;
import com.fastcampus.fintechservice.common.exception.ApiException;
import com.fastcampus.fintechservice.db.lounge.Lounge;
import com.fastcampus.fintechservice.db.lounge.LoungeRepository;
import com.fastcampus.fintechservice.db.user.UserAccount;
import com.fastcampus.fintechservice.db.user.UserRepository;
import com.fastcampus.fintechservice.dto.UserDto;
import com.fastcampus.fintechservice.dto.request.LoungeRequestDto;
import com.fastcampus.fintechservice.dto.response.LoungeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoungeService {

    private final LoungeRepository loungeRepository;


    // 라운지 글 생성
    @Transactional
    public LoungeResponse registerPost(LoungeRequestDto loungeRequestDto, UserDto userDto) {

        return LoungeResponse.from(loungeRepository.save(requestFromDto(loungeRequestDto, userDto)));
    }

    // request builder
    private Lounge requestFromDto(LoungeRequestDto loungeRequestDto, UserDto userDto) {

        UserAccount userAccount = userDto.toEntity();
        return Lounge.builder()
                .userAccount(userAccount)
                .title(loungeRequestDto.getTitle())
                .content(loungeRequestDto.getContent())
                .financialType(loungeRequestDto.getFinancialType())
                .build();
    }

    // 라운지 글 가져오기

    @Transactional
    public LoungeResponse getPost(Long postId) {

        return LoungeResponse.from(validatePost(postId));
    }



    // 라운지 글 업데이트
    @Transactional
    public LoungeResponse updatePost (Long postId, LoungeRequestDto loungeRequestDto) {
        Lounge lounge = validatePost(postId);
        lounge.loungeUpdate(loungeRequestDto);
        return LoungeResponse.from(lounge);
    }


    // 라운지 글

    @Transactional
    public String deletePost(Long postId) {
        Lounge lounge = validatePost(postId);
        loungeRepository.delete(lounge);
        return "삭제완료";
    }


    // 라운지 글 유효성체크
    public Lounge validatePost(Long postId) {
        return loungeRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "Post not found"));
    }



}
