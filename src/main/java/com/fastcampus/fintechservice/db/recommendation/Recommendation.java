package com.fastcampus.fintechservice.db.recommendation;

import java.math.BigDecimal;

import com.fastcampus.fintechservice.db.user.UserAccount;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @Entity
// @Table(name = "Recommendation")
public class Recommendation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recommendation_id")
	private Long recommendationId;

	@Column(name = "age_group", nullable = false)
	private String ageGroup;//group19under, group2024, group2529, group3034, group3539, group4044, group4549, group50up

	@Column(name = "income_group", nullable = false)
	private String incomeGroup;//income100down, income100200, income200300, income300400, income400500, income500up

	@Column(name = "saving_amount", nullable = false)
	private BigDecimal savingAmount;

	@Column(name = "saving_goal", nullable = false)
	private Integer savingGoal;

	@Column(name = "saving_start", nullable = false)
	private Integer savingStart;

	@Column(name = "saving_end", nullable = false)
	private Integer savingEnd;

	@Column(name = "saving_type", nullable = false)
	private String savingType;//free, regular

	@ManyToOne
	@JoinColumn(name = "userId")
	private UserAccount user;

}
