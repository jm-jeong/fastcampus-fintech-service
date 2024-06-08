package com.fastcampus.fintechservice.service;

import com.fastcampus.fintechservice.common.error.LoungeErrorCode;
import com.fastcampus.fintechservice.common.exception.ApiException;
import com.fastcampus.fintechservice.db.lounge.Lounge;
import com.fastcampus.fintechservice.db.lounge.LoungeRepository;
import com.fastcampus.fintechservice.db.lounge.Vote;
import com.fastcampus.fintechservice.db.lounge.VoteRepository;
import com.fastcampus.fintechservice.db.user.UserAccount;
import com.fastcampus.fintechservice.dto.UserDto;
import com.fastcampus.fintechservice.dto.request.VoteRequest;
import com.fastcampus.fintechservice.dto.response.VoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class VoteService {

    private final LoungeRepository loungeRepository;
    private final VoteRepository voteRepository;


    @Transactional
    public VoteResponse registerVote(VoteRequest voteRequest, UserDto userDto) {
        // 유효성 검사
        Long postId = voteRequest.getPostId();
        UserAccount user = userDto.toEntity();
        Lounge lounge = loungeRepository.findById(postId)
                .orElseThrow(() -> new ApiException(LoungeErrorCode.LOUNGE_POST_NOT_FOUND, String.format("postId is %s", postId)));
        voteRepository.findByUserAndLounge(user, lounge)
                .ifPresent(vote -> {
                    throw new ApiException(LoungeErrorCode.VOTE_ALREADY_EXIST, String.format("postId is %s", postId));
                });
        Vote vote = Vote.builder()
                .user(user)
                .lounge(lounge)
                .voteProductId(voteRequest.getVoteProductId())
                .build();
        voteRepository.save(vote);
        // 라운지 테이블 업데이트
        if (lounge.getFinancialProduct1().equals(voteRequest.getVoteProductId())) {
            loungeRepository.increaseVote1(lounge);
        } else if (lounge.getFinancialProduct2().equals(voteRequest.getVoteProductId())) {
            loungeRepository.increaseVote2(lounge);
        }
        return VoteResponse.from(lounge, vote);
    }

    @Transactional(readOnly = true)
    public VoteResponse getVoteInfo(Long postId, Long voteId) {
        Lounge lounge = loungeRepository.findById(postId)
                .orElseThrow(() -> new ApiException(LoungeErrorCode.LOUNGE_POST_NOT_FOUND, String.format("postId is %s", postId)));
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new ApiException(LoungeErrorCode.VOTE_NOT_FOUND, String.format("voteId is %s", voteId)));
        return VoteResponse.from(lounge, vote);
    }

}