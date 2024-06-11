package com.fastcampus.fintechservice.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoungeUpdateRequest {

    private String title;
    private String content;
}
