package com.fastcampus.fintechservice.dto.request;


import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import lombok.Getter;

import java.util.List;

@Getter
public class LikedRemoveRequest {

    private List<String> ids;
    private FinProductType finProductType;
}
