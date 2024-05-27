package com.fastcampus.fintechservice.service;

import com.fastcampus.fintechservice.common.error.ErrorCode;
import com.fastcampus.fintechservice.common.exception.ApiException;
import com.fastcampus.fintechservice.db.lounge.Lounge;
import com.fastcampus.fintechservice.db.lounge.LoungeRepository;
import com.fastcampus.fintechservice.dto.request.LoungeRequestDto;
import com.fastcampus.fintechservice.dto.response.LoungeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoungeService {

    private final LoungeRepository loungeRepository;


    // 라운지 글 생성
    @Transactional
    public LoungeResponseDto registerPost(LoungeRequestDto loungeRequestDto) {

        return LoungeResponseDto.from(loungeRepository.save(requestFromDto(loungeRequestDto)));
    }

    // request builder
    private Lounge requestFromDto(LoungeRequestDto loungeRequestDto) {
        return Lounge.builder()
                .title(loungeRequestDto.getTitle())
                .content(loungeRequestDto.getContent())
                .financialType(loungeRequestDto.getFinancialType())
                .build();
    }

    // 라운지 글 가져오기

    @Transactional
    public LoungeResponseDto getPost(Long postId) {

        return LoungeResponseDto.from(validatePost(postId));
    }



    // 라운지 글 업데이트
    @Transactional
    public LoungeResponseDto updatePost (Long postId, LoungeRequestDto loungeRequestDto) {
        Lounge lounge = validatePost(postId);
        lounge.loungeUpdate(loungeRequestDto);
        return LoungeResponseDto.from(lounge);
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
