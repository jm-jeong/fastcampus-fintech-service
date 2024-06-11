package com.fastcampus.fintechservice.dto.response;


import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.db.lounge.Lounge;
import com.fastcampus.fintechservice.dto.LoungeFinanceDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;



@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoungeResponse {

    private Long id;
    private String title;
    private String content;
    private String username;
    private int vote1;
    private int vote2;
    private int participateCount;
    private int viewCount;
    private FinProductResponseDto finProductResponseDto;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private FinProductType finProductType;
    private String finPrdtNm1;
    private String finPrdtNm2;



    public static LoungeResponse fromDeposit(Lounge lounge, LoungeFinanceDto dto1, LoungeFinanceDto dto2) {
        return LoungeResponse.builder()
                .id(lounge.getId())
                .title(lounge.getTitle())
                .username(lounge.getUser().getName())
                .content(lounge.getContent())
                .vote1(lounge.getVote1())
                .vote2(lounge.getVote2())
                .participateCount(lounge.getVote1() + lounge.getVote2())
                .createdDate(lounge.getCreatedAt())
                .finProductResponseDto(FinProductResponseDto.of(dto1, dto2))
                .updatedDate(lounge.getModifiedAt())
                .finProductType(lounge.getFinProductType())
                .build();
    }

    public static LoungeResponse fromSaving(Lounge lounge, LoungeFinanceDto dto1, LoungeFinanceDto dto2) {
        return LoungeResponse.builder()
                .id(lounge.getId())
                .title(lounge.getTitle())
                .content(lounge.getContent())
                .vote1(lounge.getVote1())
                .vote2(lounge.getVote2())
                .participateCount(lounge.getVote1() + lounge.getVote2())
                .username(lounge.getUser().getName())
                .createdDate(lounge.getCreatedAt())
                .finProductResponseDto(FinProductResponseDto.of(dto1, dto2))
                .updatedDate(lounge.getModifiedAt())
                .finProductType(lounge.getFinProductType())
                .build();
    }

    public static LoungeResponse fromDepositList (Lounge lounge) {
        return LoungeResponse.builder()
                .id(lounge.getId())
                .title(lounge.getTitle())
                .username(lounge.getUser().getName())
                .vote1(lounge.getVote1())
                .vote2(lounge.getVote2())
                .participateCount(lounge.getVote1() + lounge.getVote2())
                .createdDate(lounge.getCreatedAt())
                .viewCount(lounge.getViewCount())
                .updatedDate(lounge.getModifiedAt())
                .finProductType(lounge.getFinProductType())
                .finPrdtNm1(lounge.getFinancialProduct1Name())
                .finPrdtNm2(lounge.getFinancialProduct2Name())
                .build();
    }

    public static LoungeResponse fromSavingList(Lounge lounge) {
        return LoungeResponse.builder()
                .id(lounge.getId())
                .title(lounge.getTitle())
                .content(lounge.getContent())
                .username(lounge.getUser().getName())
                .vote1(lounge.getVote1())
                .vote2(lounge.getVote2())
                .participateCount(lounge.getVote1() + lounge.getVote2())
                .createdDate(lounge.getCreatedAt())
                .viewCount(lounge.getViewCount())
                .updatedDate(lounge.getModifiedAt())
                .finProductType(lounge.getFinProductType())
                .build();
    }











}
