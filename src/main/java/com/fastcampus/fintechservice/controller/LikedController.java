package com.fastcampus.fintechservice.controller;

import java.util.List;

import com.fastcampus.fintechservice.db.finance.enums.FinProductType;
import com.fastcampus.fintechservice.dto.request.LikedRemoveRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fastcampus.fintechservice.common.api.Api;
import com.fastcampus.fintechservice.dto.request.LikedRequest;
import com.fastcampus.fintechservice.dto.response.LikedResponse;
import com.fastcampus.fintechservice.dto.response.MessageResponse;
import com.fastcampus.fintechservice.service.LikedService;
import com.fastcampus.fintechservice.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/liked")
public class LikedController {

	private final LikedService likedService;
	private final UserService userService;

	// 금융 상품 찜하기
	@PostMapping
	public Api<LikedResponse> registerLike(@RequestBody LikedRequest likedRequest,
		Authentication authentication) {
		return Api.OK(likedService.registerLike(likedRequest,
				userService.loadUserByEmail(authentication.getName()))
		);
	}

	// 내가 찜한 상품 목록 가져오기
	@GetMapping("/me")
	public Api<List<LikedResponse>> getLikeList(FinProductType finProductType, Authentication authentication) {
		return Api.OK(likedService.getLikedList(finProductType,
				userService.loadUserByEmail(authentication.getName()))
		);
	}

	// 찜한 상품 삭제
	@DeleteMapping
	public Api<MessageResponse> deleteLike(@RequestBody LikedRemoveRequest likedRemoveRequest,
										   Authentication authentication) {
		return Api.OK(
				likedService.removeLiked(likedRemoveRequest,userService.loadUserByEmail(authentication.getName()))
		);
	}
}
