package com.fastcampus.db.user;

import java.util.Objects;

import com.fastcampus.db.AuditingFields;
import com.fastcampus.db.user.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_account",
	indexes = {
		@Index(columnList = "email"),
		@Index(columnList = "createdAt"),
		@Index(columnList = "createdBy")
	}
)
@Entity
public class UserAccount extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(nullable = false, length = 320, unique = true)
	private String email;

	@Column(nullable = false, length = 100)
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	@Builder
	public UserAccount(Long id, String name, String email, String password, UserRole userRole) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.userRole = userRole;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserAccount that))
			return false;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
