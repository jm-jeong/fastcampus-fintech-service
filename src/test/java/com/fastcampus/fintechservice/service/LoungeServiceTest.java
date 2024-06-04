//package com.fastcampus.fintechservice.service;
//
//import com.fastcampus.fintechservice.db.lounge.Lounge;
//import com.fastcampus.fintechservice.db.lounge.LoungeRepository;
//import com.fastcampus.fintechservice.db.lounge.enums.FinancialType;
//import com.fastcampus.fintechservice.dto.request.LoungeRequestDto;
//import com.fastcampus.fintechservice.dto.response.LoungeResponse;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.slf4j.Logger;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class LoungeServiceTest {
//
//    @Mock
//    LoungeRepository loungeRepository;
//
//    @InjectMocks
//    LoungeService loungeService;
//
//
//
//    @Mock
//    Logger logger;
//
//    //final UserAccount user = mock(UserAccount.class);
//    final LoungeRequestDto loungeRequestDto = LoungeRequestDto.builder()
//            .title("Test Title")
//            .content("Test Content")
//            .financialType(FinancialType.FIXED_DEPOSIT)
//            .build();
//
//    final Lounge resultLounge = Lounge.builder()
//            .title(loungeRequestDto.getTitle())
//            .content(loungeRequestDto.getContent())
//            .financialType(FinancialType.FIXED_DEPOSIT)
//            .build();
//    @Test
//    @DisplayName("라운지 글 생성")
//    void registerPostTest() {
//        // Arrange
//
//
//        when(loungeRepository.save(any(Lounge.class))).thenReturn(resultLounge);
//
//        // Act
//        LoungeResponse result = loungeService.registerPost(loungeRequestDto);
//
//        // Assert
//        assertEquals(resultLounge.getTitle(), result.getTitle());
//        assertEquals(resultLounge.getContent(), result.getContent());
//        assertEquals(resultLounge.getFinancialType(), result.getFinancialType());
//
//        verify(loungeRepository, times(1)).save(any(Lounge.class));
//    }
//}