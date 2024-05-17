package com.fastcampus.apiserver.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.apiserver.config.utils.JwtTokenUtils;
import com.fastcampus.apiserver.dto.UserDto;
import com.fastcampus.apiserver.dto.request.UserLoginRequest;
import com.fastcampus.apiserver.dto.request.UserRegisterRequest;
import com.fastcampus.apiserver.dto.response.UserResponse;
import com.fastcampus.common.error.UserErrorCode;
import com.fastcampus.common.exception.ApiException;
import com.fastcampus.db.user.UserAccount;
import com.fastcampus.db.user.UserRepository;
import com.fastcampus.db.user.enums.UserRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.token.expired-time-ms}")
	private Long expiredTimeMs;

	@Transactional
	public UserResponse join(UserRegisterRequest request) {

		userRepository.findByName(request.name()).ifPresent(it -> {
			throw new ApiException(UserErrorCode.DUPLICATED_USER_NAME, String.format("name is %s", request.name()));
		});

		UserAccount entity = UserAccount.builder()
			.name(request.name())
			.email(request.email())
			.password(passwordEncoder.encode(request.password()))
			.userRole(UserRole.USER)
			.build();
		UserAccount savedUserAccount = userRepository.save(entity);

		return UserResponse.of(savedUserAccount.getName(), savedUserAccount.getEmail());
	}

	public String login(UserLoginRequest userLoginRequest) {
		String email = userLoginRequest.getEmail();
		String password = userLoginRequest.getPassword();

		UserDto savedUser = loadUserByEmail(email);
		if (!passwordEncoder.matches(password, savedUser.getPassword())) {
			log.info("password : {} , savedUser password : {}", password, savedUser.getPassword());
			throw new ApiException(UserErrorCode.INVALID_PASSWORD);
		}
		return JwtTokenUtils.generateAccessToken(email, secretKey, expiredTimeMs);
	}

	public UserDto loadUserByEmail(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email).map(UserDto::from)
			.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, String.format("email is %s", email))
			);
	}

}
