package com.fastcampus.fintechservice.service;

import com.fastcampus.fintechservice.common.error.ErrorCode;
import com.fastcampus.fintechservice.common.error.FinanceErrorCode;
import com.fastcampus.fintechservice.common.error.LikedErrorCode;
import com.fastcampus.fintechservice.common.exception.ApiException;
import com.fastcampus.fintechservice.db.finance.Deposit;
import com.fastcampus.fintechservice.db.finance.DepositRepository;
import com.fastcampus.fintechservice.db.finance.Saving;
import com.fastcampus.fintechservice.db.finance.SavingRepository;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.db.liked.Liked;
import com.fastcampus.fintechservice.db.liked.LikedRepository;
import com.fastcampus.fintechservice.dto.UserDto;
import com.fastcampus.fintechservice.dto.request.LikedListRequest;
import com.fastcampus.fintechservice.dto.request.LikedRemoveRequest;
import com.fastcampus.fintechservice.dto.request.LikedRequest;
import com.fastcampus.fintechservice.dto.response.LikedResponse;
import com.fastcampus.fintechservice.dto.response.MessageResponse;
import com.fastcampus.fintechservice.redis.RedisKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikedService {

    private final DepositRepository depositRepository;
    private final LikedRepository likedRepository;
    private final SavingRepository savingRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public LikedResponse registerLike(LikedRequest likedRequest, UserDto user) {
        Liked liked = null;
        // type이 예금인 경우
        if (likedRequest.getType() == FinProductType.DEPOSIT) {
            Deposit deposit = validateDeposit(likedRequest.getId());
            liked = likedRepository.findByUserAndDeposit(user.toEntity(), deposit);
            if (liked == null) {
                redisTemplate.opsForValue().increment(
                        RedisKey.DEPOSIT_LIKED_KEY.getKey() + deposit.getDepositId(), 1L);
                deposit.countLiked(true);
                liked = Liked.builder()
                        .deposit(deposit)
                        .user(user.toEntity())
                        .finProductType(FinProductType.DEPOSIT)
                        .build();
                likedRepository.save(liked);

                return LikedResponse.from(
                        deposit.getFinPrdtNm(),
                        redisTemplate.opsForValue().get(
                                RedisKey.DEPOSIT_LIKED_KEY.getKey() + deposit.getDepositId()),
                        FinProductType.DEPOSIT
                );
                // 이미 찜하기 한 경우
            } else {
                throw new ApiException(LikedErrorCode.ALREADY_LIKED_FINANCE_DEPOSIT,
                        String.format("id is %s", likedRequest.getId()));
            }


            // type이 적금인 경우
        } else if (likedRequest.getType() == FinProductType.SAVING) {
            Saving saving = validateSaving(likedRequest.getId());
            liked = likedRepository.findByUserAndSaving(user.toEntity(), saving);
            if (liked == null) {

                redisTemplate.opsForValue().increment(
                        RedisKey.SAVING_LIKED_KEY.getKey() + saving.getSavingId(), 1L);
                saving.countLiked(true);
                liked = Liked.builder()
                        .saving(saving)
                        .user(user.toEntity())
                        .finProductType(FinProductType.SAVING)
                        .build();
                likedRepository.save(liked);

                return LikedResponse.from(
                        saving.getFinPrdtNm(),
                        redisTemplate.opsForValue().get(
                                RedisKey.SAVING_LIKED_KEY.getKey() + saving.getSavingId()),
                        FinProductType.SAVING
                );
            } else {
                throw new ApiException(
                        LikedErrorCode.ALREADY_LIKED_FINANCE_SAVING,
                        String.format("id is %s", likedRequest.getId()));
            }
        } else {
            throw new ApiException(LikedErrorCode.NOT_FOUND_FINANCE_TYPE,
                    String.format("type is %s", likedRequest.getType()));
        }

    }

    @Transactional
    public MessageResponse removeLiked(LikedRemoveRequest request, UserDto user) {
        if (request.getFinProductType()== FinProductType.DEPOSIT) {
            for (String id : request.getIds()) {
                Deposit deposit = validateDeposit(id);
                Liked liked = likedRepository.findByUserAndDeposit(user.toEntity(), deposit);
                likedRepository.delete(liked);
                redisTemplate.opsForValue().decrement(
                        RedisKey.DEPOSIT_LIKED_KEY.getKey() + deposit.getDepositId(), 1L);
                deposit.countLiked(false);
            }
        } else if (request.getFinProductType() == FinProductType.SAVING) {
            for (String id : request.getIds()) {
                Saving saving = validateSaving(id);
                Liked liked = likedRepository.findByUserAndSaving(user.toEntity(), saving);
                likedRepository.delete(liked);
                redisTemplate.opsForValue().decrement(
                        RedisKey.SAVING_LIKED_KEY.getKey() + saving.getSavingId(), 1L);
                saving.countLiked(false);
            }
        } else {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }
        return new MessageResponse("찜 상품 삭제 완료");
    }


    @Transactional(readOnly = true)
    public List<LikedResponse> getLikedList(FinProductType finProductType,UserDto userDto) {
        List<Liked> likedList = likedRepository.findAllByUserAndFinProductType(userDto.toEntity(), finProductType);

        List<LikedResponse> likedResponses = new ArrayList<>();
        for (Liked liked : likedList) {
            try {
                likedResponses.add(LikedResponse.fromDetail(liked));
            } catch (IOException e) {
                // 예외 처리 로직 추가
                // 예를 들어, 로그 기록, 사용자에게 에러 메시지 전달 등
                e.printStackTrace();
            }
        }
        return likedResponses;
    }






    public Deposit validateDeposit(String depositId) {
        return depositRepository.findById(depositId)
                .orElseThrow(() -> new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND,
                        String.format("id is %s", depositId)));
    }

    public Saving validateSaving(String savingId) {
        return savingRepository.findById(savingId)
                .orElseThrow(() -> new ApiException(FinanceErrorCode.FINANCE_NOT_FOUND,
                        String.format("id is %s", savingId)));
    }

}
