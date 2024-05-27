package com.fastcampus.fintechservice.db.lounge.enums;

public enum FinancialType {
    SAVINGS_ACCOUNT("저축예금"),
    FIXED_DEPOSIT("정기예금"),
    INSURANCE("보험"),
    LOAN("대출"),
    CREDIT_CARD("신용카드"),
    MORTGAGE("주택담보대출");

    private final String description;

    FinancialType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}