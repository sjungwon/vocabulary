package domhwangcha.voca.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException{
    public NotFoundException() {
        super(HttpStatus.NOT_FOUND,"요청한 리소스를 찾을 수 없습니다.");
    }
}
