package com.fastcampus.fintechservice.service;

import com.fastcampus.fintechservice.common.error.ErrorCode;
import com.fastcampus.fintechservice.common.exception.ApiException;
import com.fastcampus.fintechservice.db.finance.Deposit;
import com.fastcampus.fintechservice.db.finance.DepositRepository;
import com.fastcampus.fintechservice.db.finance.Saving;
import com.fastcampus.fintechservice.db.finance.SavingRepository;
import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.db.liked.Liked;
import com.fastcampus.fintechservice.db.liked.LikedRepository;
import com.fastcampus.fintechservice.db.user.UserAccount;
import com.fastcampus.fintechservice.dto.UserDto;
import com.fastcampus.fintechservice.dto.response.LikedResponse;
import com.fastcampus.fintechservice.dto.response.MessageResponse;
import com.fastcampus.fintechservice.redis.RedisKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public LikedResponse registerLike(String id, FinProductType type, UserDto user) {
        Liked liked = null;
        // type이 예금인 경우
        if (type == FinProductType.DEPOSIT) {
            Deposit deposit = validateDeposit(id);
            liked = likedRepository.findByUserAndDeposit(user.toEntity(), deposit);
            if (liked == null) {
                redisTemplate.opsForValue().increment(
                        RedisKey.DEPOSIT_LIKED_KEY.getKey() + deposit.getDepositId(), 1L);
                liked = Liked.builder()
                        .deposit(deposit)
                        .user(user.toEntity())
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
                likedRepository.delete(liked);
                redisTemplate.opsForValue().decrement(
                        RedisKey.DEPOSIT_LIKED_KEY.getKey() + deposit.getDepositId(), 1L);
            }
            // type이 적금인 경우
        } else if (type == FinProductType.SAVING) {
            Saving saving = validateSaving(id);
            liked = likedRepository.findByUserAndSaving(user.toEntity(), saving);
            if (liked == null) {
                redisTemplate.opsForValue().increment(
                        RedisKey.SAVING_LIKED_KEY.getKey() + saving.getSavingId(), 1L);
                liked = Liked.builder()
                        .saving(saving)
                        .user(user.toEntity())
                        .build();
                likedRepository.save(liked);

                return LikedResponse.from(
                        saving.getFinPrdtNm(),
                        redisTemplate.opsForValue().get(
                                RedisKey.SAVING_LIKED_KEY.getKey() + saving.getSavingId()),
                        FinProductType.SAVING
                );
            } else {
                likedRepository.delete(liked);
                redisTemplate.opsForValue().decrement(
                        RedisKey.SAVING_LIKED_KEY.getKey() + saving.getSavingId(), 1L);
            }
        } else {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        return LikedResponse.from(
                type == FinProductType.DEPOSIT
                        ? validateDeposit(id).getFinPrdtNm()
                        : validateSaving(id).getFinPrdtNm(),
                redisTemplate.opsForValue().get(
                        type == FinProductType.DEPOSIT
                                ? RedisKey.DEPOSIT_LIKED_KEY.getKey() + validateDeposit(id).getDepositId()
                                : RedisKey.SAVING_LIKED_KEY.getKey() + validateSaving(id).getSavingId()
                ),
                type
        );
    }

    @Transactional
    public MessageResponse removeLiked(String id, FinProductType type, UserDto user) {
        Liked liked = null;
        // type이 예금인 경우
        if (type == FinProductType.DEPOSIT) {
            Deposit deposit = validateDeposit(id);
            liked = likedRepository.findByUserAndDeposit(user.toEntity(), deposit);
            likedRepository.delete(liked);
            redisTemplate.opsForValue().decrement(
                    RedisKey.SAVING_LIKED_KEY.getKey() + deposit.getDepositId(), 1L);


        }else if (type == FinProductType.SAVING) {
            Saving saving = validateSaving(id);
            liked = likedRepository.findByUserAndSaving(user.toEntity(), saving);
            likedRepository.delete(liked);
            redisTemplate.opsForValue().decrement(
                    RedisKey.SAVING_LIKED_KEY.getKey() + saving.getSavingId(), 1L);
        } else {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }
        return new MessageResponse("찜 상품 삭제 완료");
    }


    @Transactional(readOnly = true)
    public List<LikedResponse> getLikedList(UserDto userDto) {
        List<Liked> likedList = likedRepository.findAllByUser(userDto.toEntity());
        return likedList.stream()
                .map(LikedResponse::fromDetail)
                .collect(Collectors.toList());
    }




    public Deposit validateDeposit(String depositId) {
        return depositRepository.findById(depositId)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));
    }

    public Saving validateSaving(String savingId) {
        return savingRepository.findById(savingId)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));
    }

}
