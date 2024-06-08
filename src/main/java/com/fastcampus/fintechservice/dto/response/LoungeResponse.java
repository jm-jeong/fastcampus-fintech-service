package com.fastcampus.fintechservice.dto.response;


import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.db.lounge.Lounge;
import com.fastcampus.fintechservice.dto.LoungeFinanceDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Builder
@Getter
public class LoungeResponse {

    private String title;
    private String content;
    private String username;
    private int viewCount;
    private FinProductResponseDto finProductResponseDto;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private FinProductType finProductType;


    public static LoungeResponse fromDeposit(Lounge lounge, LoungeFinanceDto dto1, LoungeFinanceDto dto2) {
        return LoungeResponse.builder()
                .title(lounge.getTitle())
                .username(lounge.getUser().getName())
                .content(lounge.getContent())
                .createdDate(lounge.getCreatedAt())
                .finProductResponseDto(FinProductResponseDto.of(dto1, dto2))
                .updatedDate(lounge.getModifiedAt())
                .finProductType(lounge.getFinProductType())
                .build();
    }

    public static LoungeResponse fromSaving(Lounge lounge, LoungeFinanceDto dto1, LoungeFinanceDto dto2) {
        return LoungeResponse.builder()
                .title(lounge.getTitle())
                .content(lounge.getContent())
                .username(lounge.getUser().getName())
                .createdDate(lounge.getCreatedAt())
                .finProductResponseDto(FinProductResponseDto.of(dto1, dto2))
                .updatedDate(lounge.getModifiedAt())
                .finProductType(lounge.getFinProductType())
                .build();
    }

    public static List<LoungeResponse> fromDepositList (List<Lounge> lounges, LoungeFinanceDto dto1, LoungeFinanceDto dto2) {
        return lounges.stream().map(lounge -> LoungeResponse.builder()
                .title(lounge.getTitle())
                .username(lounge.getUser().getName())
                .createdDate(lounge.getCreatedAt())
                .viewCount(lounge.getViewCount())
                .finProductResponseDto(FinProductResponseDto.of(dto1, dto2))
                .updatedDate(lounge.getModifiedAt())
                .finProductType(lounge.getFinProductType())
                .build()).collect(Collectors.toList());
    }








}
