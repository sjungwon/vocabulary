package domhwangcha.voca.exception;

import org.springframework.http.HttpStatus;

public class FormValidateException extends HttpException{
    public FormValidateException(String message) {
        super(HttpStatus.BAD_REQUEST,message);
    }
}
