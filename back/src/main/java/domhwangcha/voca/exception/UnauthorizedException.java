package domhwangcha.voca.exception;

public class UnauthorizedException extends HttpException{
    public UnauthorizedException() {
        super(401, "로그인이 필요합니다");
    }

    public UnauthorizedException(String message) {
        super(401, message);
    }
}
