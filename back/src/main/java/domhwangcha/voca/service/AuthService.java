package domhwangcha.voca.service;

import domhwangcha.voca.domain.Member;
import domhwangcha.voca.domain.Role;
import domhwangcha.voca.exception.DuplicateException;
import domhwangcha.voca.exception.NotFoundException;
import domhwangcha.voca.exception.UnauthorizedException;
import domhwangcha.voca.repository.MemberRepository;
import domhwangcha.voca.service.dto.auth.LoginDto;
import domhwangcha.voca.service.dto.auth.RegisterDto;
import domhwangcha.voca.service.dto.auth.SessionDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SessionDto login(LoginDto loginDto){
        Member member = this.memberRepository.findByAccount(loginDto.getAccount()).orElseThrow(NotFoundException::new);

        if(!passwordEncoder.matches(loginDto.getRawPassword(), member.getHashedPassword())){
            throw new NotFoundException();
        }

        return new SessionDto(member);
    }

    @Transactional
    public void register(RegisterDto registerDto){
        Optional<Member> byAccount = memberRepository.findByAccount(registerDto.getAccount());
        if(byAccount.isPresent()){
            throw new DuplicateException("중복된 아이디입니다.");
        }

        Member build = Member.builder()
                .account(registerDto.getAccount())
                .hashedPassword(passwordEncoder.encode(registerDto.getRawPassword()))
                .role(Role.USER)
                .build();

        memberRepository.save(build);
    }
}
