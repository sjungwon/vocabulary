package domhwangcha.voca.exception;

import lombok.Getter;

@Getter
public class HttpException extends RuntimeException{
    private int status = 500;

    public HttpException(String message) {
        super(message);
    }

    public HttpException(int status ,String message) {
        super(message);
        this.status = status;
    }
}
