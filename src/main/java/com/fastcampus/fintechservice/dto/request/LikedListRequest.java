package com.fastcampus.fintechservice.dto.request;

import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class LikedListRequest {
    private FinProductType finProductType;
}
