package com.fastcampus.fintechservice.dto.response;
import com.fastcampus.fintechservice.common.ImageConverter;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.db.liked.Liked;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import java.io.IOException;
import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikedResponse {
    private String name;
    private String count;
    private FinProductType finProductType;
    private LikedFinanceDto likedFinanceDto;
    private List<String> joinWayList;//가입방법

    public static LikedResponse from(
            String name,
            String count,
            FinProductType type) {
        return LikedResponse.builder()
                .name(name)
                .count(count)
                .finProductType(type)
                .build();
    }


    public static LikedResponse fromDetail(Liked liked) throws IOException {
        List<String> joins =
                liked.getDeposit() != null ?
                        List.of(liked.getDeposit().getJoinWay().split(",")) :
                        List.of(liked.getSaving().getJoinWay().split(","));

        return LikedResponse.builder()
                .name(getFinancialProductName(liked))
                .finProductType(getFinancialProductType(liked))
                .likedFinanceDto(LikedFinanceDto.builder()
                        .id(liked.getDeposit() != null ?
                                liked.getDeposit().getDepositId() :
                                liked.getSaving().getSavingId())
                        .finPrdtNm(getFinancialProductName(liked))
                        .korCoNm(liked.getDeposit() != null ?
                                        liked.getDeposit().getKorCoNm() :
                                        liked.getSaving().getKorCoNm())
                        .imageBase64(liked.getDeposit() != null ?
                                ImageConverter.encodeBase64(
                                        liked.getDeposit().getBank().getImageName()) :
                                ImageConverter.encodeBase64(
                                        liked.getSaving().getBank().getImageName()))
                        .intrRateShow(liked.getDeposit() != null ?
                                liked.getDeposit().getIntrRateShow() :
                                liked.getSaving().getIntrRateShow())
                        .intrRate2Show(liked.getDeposit() != null ?
                                liked.getDeposit().getIntrRate2Show() :
                                liked.getSaving().getIntrRate2Show())
                        .joinWayList(joins)
                        .build()).build();

    }


    private static String getFinancialProductName(Liked liked) {
        if (liked.getDeposit() != null) {
            return liked.getDeposit().getFinPrdtNm();
        } else {
            return liked.getSaving().getFinPrdtNm();
        }
    }

    private static FinProductType getFinancialProductType(Liked liked) {
        if (liked.getDeposit() != null) {
            return FinProductType.DEPOSIT;
        } else {
            return FinProductType.SAVING;
        }
    }
}
