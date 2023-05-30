package domhwangcha.voca.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends HttpException{
    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다");
    }
}
