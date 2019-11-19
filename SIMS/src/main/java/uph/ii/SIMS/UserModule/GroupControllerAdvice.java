package uph.ii.SIMS.UserModule;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uph.ii.SIMS.UserModule.Dto.GroupApplicationDuplicationException;

@ControllerAdvice
public class GroupControllerAdvice {
    
    @ExceptionHandler(value= {
        GroupApplicationDuplicationException.class,
    })
    protected ResponseEntity<String> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Użytkownik nie może aplikować wiele razy do jednej grupy";
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }
}
