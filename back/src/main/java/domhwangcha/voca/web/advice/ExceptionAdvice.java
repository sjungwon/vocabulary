package domhwangcha.voca.web.advice;

import domhwangcha.voca.exception.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice{

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleBasicError(){
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HttpStatus.INTERNAL_SERVER_ERROR.name());
//    }
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<String> handleHttpException(HttpException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
