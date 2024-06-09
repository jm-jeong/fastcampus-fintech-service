package com.fastcampus.fintechservice.dto.request;

import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikedListRequest {
    private FinProductType finProductType;
}
