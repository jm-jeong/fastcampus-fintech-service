package com.fastcampus.apiserver.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fastcampus.db.user.UserAccount;
import com.fastcampus.db.user.enums.UserRole;

public record UserDto(
	Long id,
	String name,
	String email,
	String password,
	UserRole role,
	LocalDateTime createdAt,
	String createdBy,
	LocalDateTime modifiedAt,
	String modifiedBy
) implements UserDetails {

	public static UserDto of(Long id, String name, String email, String password, UserRole role,
		LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
		return new UserDto(id, name, email, password, role, createdAt, createdBy, modifiedAt, modifiedBy);
	}

	public static UserDto from(UserAccount entity) {
		return new UserDto(
			entity.getId(),
			entity.getName(),
			entity.getEmail(),
			entity.getPassword(),
			entity.getUserRole(),
			entity.getCreatedAt(),
			entity.getCreatedBy(),
			entity.getModifiedAt(),
			entity.getModifiedBy()
		);
	}

	public UserAccount toEntity() {
		return UserAccount.builder()
			.id(id)
			.name(name)
			.email(email)
			.password(password)
			.userRole(role)
			.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.toString()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email; //email로 로그인
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
