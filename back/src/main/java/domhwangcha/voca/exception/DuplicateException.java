package domhwangcha.voca.exception;

import org.springframework.http.HttpStatus;

public class DuplicateException extends HttpException{

    public DuplicateException(String message) {
        super(HttpStatus.BAD_REQUEST,message);
    }
}
