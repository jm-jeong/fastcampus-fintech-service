package com.fastcampus.fintechservice.dto.response;


import com.fastcampus.fintechservice.db.lounge.Lounge;
import com.fastcampus.fintechservice.db.lounge.Vote;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteResponse {

    private Long voteId;
    private Long postId;
    private String voteProductId;
    private int vote1;
    private int vote2;
    private String financialProduct1;

    public static VoteResponse from(Lounge lounge, Vote vote) {
        return VoteResponse.builder()
                .voteId(vote.getId())
                .postId(lounge.getId())
                .vote1(lounge.getVote1())
                .vote2(lounge.getVote2())
                .voteProductId(vote.getVoteProductId())
                .build();
    }
}
