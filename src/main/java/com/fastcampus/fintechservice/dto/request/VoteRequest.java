package com.fastcampus.fintechservice.dto.request;

import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.db.lounge.Lounge;
import lombok.Getter;


@Getter
public class VoteRequest {

    private Long postId;
    private String voteProductId;
}
