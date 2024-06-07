package com.fastcampus.fintechservice.db.lounge.enums;

import lombok.Getter;

@Getter
public enum FinancialType {
    FIXED_DEPOSIT("정기예금"),
    SAVINGS_ACCOUNT("적금");

    private final String description;

    FinancialType(String description) {
        this.description = description;
    }

}