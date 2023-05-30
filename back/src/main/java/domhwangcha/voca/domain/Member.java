package domhwangcha.voca.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private Role role;
    private String account;
    private String hashedPassword;

    @Builder
    private Member(Role role, String account, String hashedPassword) {
        this.role = role;
        this.account = account;
        this.hashedPassword = hashedPassword;
    }
}
