package com.fastcampus.fintechservice.db.liked;

import com.fastcampus.fintechservice.db.finance.Deposit;
import com.fastcampus.fintechservice.db.finance.Saving;
import com.fastcampus.fintechservice.db.user.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Liked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserAccount user;

    @ManyToOne
    @JoinColumn(name = "depositId")
    private Deposit deposit;

    @ManyToOne
    @JoinColumn(name = "savingId")
    private Saving saving;

    public boolean validateUser(UserAccount user) {
        return !this.user.equals(user);
    }
}
