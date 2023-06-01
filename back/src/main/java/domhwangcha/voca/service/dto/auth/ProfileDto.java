package domhwangcha.voca.service.dto.auth;

import domhwangcha.voca.domain.Member;
import lombok.Getter;

@Getter
public class ProfileDto {
    private String account;
    //todo - tier


    public ProfileDto(Member member) {
        this.account = member.getAccount();
    }
}
