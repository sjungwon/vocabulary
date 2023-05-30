package domhwangcha.voca.web.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginForm {
    private String account;
    private String password;
}
