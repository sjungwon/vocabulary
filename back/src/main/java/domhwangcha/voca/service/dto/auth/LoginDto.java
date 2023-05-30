package domhwangcha.voca.service.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDto {
    private final String account;
    private final String rawPassword;
}
