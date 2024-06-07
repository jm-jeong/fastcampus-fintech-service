package com.fastcampus.fintechservice.db.lounge;


import com.fastcampus.fintechservice.db.lounge.enums.FinancialType;
import com.fastcampus.fintechservice.db.user.UserAccount;
import com.fastcampus.fintechservice.dto.request.LoungeRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Lounge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long id;
    @Column
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserAccount userAccount;

    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    private FinancialType financialType;

    @CreatedDate
    private String createdAt;
    @LastModifiedDate
    private String modifiedAt;


    public void loungeUpdate(LoungeRequestDto loungeRequestDto) {
        this.title = loungeRequestDto.getTitle();
        this.content = loungeRequestDto.getContent();
    }


}
