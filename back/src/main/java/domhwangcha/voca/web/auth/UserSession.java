package domhwangcha.voca.web.auth;

import domhwangcha.voca.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSession {
    private final Long id;
    private final Role role;
}
