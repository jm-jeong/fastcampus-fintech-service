package com.fastcampus.fintechservice.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fastcampus.fintechservice.common.error.UserErrorCode;
import com.fastcampus.fintechservice.common.exception.ApiException;
import com.fastcampus.fintechservice.dto.request.UserLoginRequest;
import com.fastcampus.fintechservice.dto.request.UserRegisterRequest;
import com.fastcampus.fintechservice.dto.response.UserResponse;
import com.fastcampus.fintechservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class UserApiControllerTest {

	@MockBean
	UserService userService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@WithAnonymousUser
	public void 회원가입() throws Exception {
		UserRegisterRequest userRegisterRequest = UserRegisterRequest.of("tester", "email@email.com", "password");

		when(userService.join(userRegisterRequest)).thenReturn(mock(UserResponse.class));

		mockMvc.perform(post("/api/v1/users/join")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userRegisterRequest)))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@WithAnonymousUser
	public void 회원가입시_같은_이메일로_회원가입하면_에러발생() throws Exception {
		String name = "nametest";
		String email = "email123@email.com";
		String password = "password12345";
		UserRegisterRequest userRegisterRequest = UserRegisterRequest.of(name, email, password);
		when(userService.join(userRegisterRequest)).thenThrow(new ApiException(UserErrorCode.DUPLICATED_USER_EMAIL));

		mockMvc.perform(post("/api/v1/users/join")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserRegisterRequest("nameaa", email, "password1234"))))
			.andDo(print())
			.andExpect(status().is(UserErrorCode.DUPLICATED_USER_EMAIL.getHttpStatusCode()));
	}

	@Test
	@WithAnonymousUser
	public void 로그인() throws Exception {
		String email = "email@email.com";
		String password = "password";
		UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);

		when(userService.login(userLoginRequest)).thenReturn("testToken");

		mockMvc.perform(post("/api/v1/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserLoginRequest(email, password))))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@WithAnonymousUser
	public void 로그인시_비밀번호가_다르면_에러발생() throws Exception {
		String email = "email@email.com";
		String password = "password12345";
		UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);

		when(userService.login(userLoginRequest)).thenThrow(new ApiException(UserErrorCode.INVALID_PASSWORD));

		mockMvc.perform(post("/api/v1/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserLoginRequest("a@email.com", "password!@#$%"))))
			.andDo(print())
			.andExpect(status().is(UserErrorCode.INVALID_PASSWORD.getHttpStatusCode()));
	}
}