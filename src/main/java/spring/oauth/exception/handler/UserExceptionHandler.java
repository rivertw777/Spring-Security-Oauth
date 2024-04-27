package spring.oauth.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import spring.oauth.exception.UserException;
import spring.oauth.exception.dto.CustomErrorResponse;

@ControllerAdvice
@RestController
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<CustomErrorResponse> handleMemberException(UserException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorResponse(e.getMessage()));
    }

}
