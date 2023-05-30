package domhwangcha.voca.service.dto.auth;


import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterDto {
    private final String account;
    private final String rawPassword;

    @Builder
    public RegisterDto(String account, String rawPassword) {
        this.account = account;
        this.rawPassword = rawPassword;
    }
}
