package domhwangcha.voca.exception;

public class ForbiddenException extends HttpException{

    public ForbiddenException() {
        super(403, "권한이 없습니다.");
    }

    public ForbiddenException(String message) {
        super(403, message);
    }
}
