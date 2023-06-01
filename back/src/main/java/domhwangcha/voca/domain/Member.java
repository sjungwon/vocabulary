package domhwangcha.voca.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String account;

    private String hashedPassword;

    @Builder
    private Member(Role role, String account, String hashedPassword) {
        this.role = role;
        this.account = account;
        this.hashedPassword = hashedPassword;
    }
}
