package domhwangcha.voca.service.dto.auth;

import domhwangcha.voca.domain.Member;
import domhwangcha.voca.domain.Role;
import lombok.Getter;

@Getter
public class SessionDto {
    private final Long id;
    private final Role role;
    private final ProfileDto profileDto;

    public SessionDto(Member member) {
        this.id = member.getId();
        this.role = member.getRole();
        this.profileDto = new ProfileDto(member);
    }
}
