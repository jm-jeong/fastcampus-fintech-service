package com.fastcampus.fintechservice.dto.request;

import com.fastcampus.fintechservice.db.finance.enums.BankType;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class FinanceListRequest {

    private FinProductType finProductType;
    private List<BankType> bankTypeList;
}
