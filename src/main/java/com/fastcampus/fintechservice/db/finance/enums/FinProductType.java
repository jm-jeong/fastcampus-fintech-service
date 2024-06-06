package com.fastcampus.fintechservice.db.finance.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor

public enum FinProductType {
    DEPOSIT("예금"),
    SAVING("적금");

    private final String name;
}
