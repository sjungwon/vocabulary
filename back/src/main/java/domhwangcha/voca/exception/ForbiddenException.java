package domhwangcha.voca.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends HttpException{

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, "권한이 없습니다.");
    }
}
