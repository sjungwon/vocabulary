package domhwangcha.voca.web.advice;

import domhwangcha.voca.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice{

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleBasicError(Exception e){
        log.info("internal server error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HttpStatus.INTERNAL_SERVER_ERROR.name());
    }
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<String> handleHttpException(HttpException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
