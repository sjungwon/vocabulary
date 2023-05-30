package domhwangcha.voca.web.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
public class RegisterForm {
    @NotBlank(message = "필수 값입니다")
    @Size(min=4, max = 10, message = "아이디를 4자 ~ 10자 사이로 입력해주세요.")
    @Pattern(regexp = "^[a-z][a-z0-9]+$", message = "아이디는 영문자로 시작해야 하고, 영문자, 숫자만 입력 가능합니다.")
    private String account;

    @NotBlank(message = "필수 값입니다")
    @Size(min = 8, max = 20, message = "비밀번호를 8자 ~ 20자 사이로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "비밀번호는 영문자와 숫자를 각 1개 이상 포함해야합니다.")
    private String password;
}
