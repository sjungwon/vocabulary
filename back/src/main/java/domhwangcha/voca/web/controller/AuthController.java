package domhwangcha.voca.web.controller;

import domhwangcha.voca.exception.BadRequestException;
import domhwangcha.voca.service.AuthService;
import domhwangcha.voca.service.dto.auth.LoginDto;
import domhwangcha.voca.service.dto.auth.ProfileDto;
import domhwangcha.voca.service.dto.auth.RegisterDto;
import domhwangcha.voca.service.dto.auth.SessionDto;
import domhwangcha.voca.web.auth.Authorization;
import domhwangcha.voca.web.auth.LoginMember;
import domhwangcha.voca.web.auth.SessionConst;
import domhwangcha.voca.web.auth.UserSession;
import domhwangcha.voca.web.dto.ApiResponse;
import domhwangcha.voca.web.dto.auth.LoginForm;
import domhwangcha.voca.web.dto.auth.RegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ApiResponse<ProfileDto> login(@RequestBody  LoginForm loginForm, HttpServletRequest httpServletRequest){
        LoginDto loginDto = new LoginDto(loginForm.getAccount(), loginForm.getPassword());

        SessionDto sessionDto = authService.login(loginDto);

        HttpSession session = httpServletRequest.getSession(true);

        session.setAttribute(SessionConst.USER_SESSION.name(),new UserSession(sessionDto.getId(), sessionDto.getRole()));

        return new ApiResponse<>(sessionDto.getProfileDto());
    }

    @PostMapping("session")
    @Authorization
    public ApiResponse<ProfileDto> sessionLogin(@LoginMember UserSession userSession){
        ProfileDto profileDto = this.authService.sessionLogin(userSession.getId());
        return new ApiResponse<>(profileDto);
    }

    @PostMapping("register")
    public ApiResponse<String> register(@RequestBody @Validated RegisterForm registerForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ObjectError objectError = bindingResult.getAllErrors().get(0);
            throw new BadRequestException(objectError.getDefaultMessage());
        }

        RegisterDto build = RegisterDto.builder()
                .account(registerForm.getAccount())
                .rawPassword(registerForm.getPassword())
                .build();
        authService.register(build);

        return new ApiResponse<>("ok");
    }

}
