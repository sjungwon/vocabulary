package domhwangcha.voca.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends RuntimeException{
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public HttpException() {
        super("서버에서 오류가 발생했습니다.");
    }

    public HttpException(HttpStatus httpStatus,String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
