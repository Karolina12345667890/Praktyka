package uph.ii.SIMS;

import javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uph.ii.SIMS.DocumentModule.Dto.AccessDeniedException;
import uph.ii.SIMS.DocumentModule.Dto.AnkietaDuplicateException;
import uph.ii.SIMS.DocumentModule.Dto.CantModifyAcceptedDocumentException;
import uph.ii.SIMS.FileSendingModule.ResponseMessage;
import uph.ii.SIMS.UserModule.UserDuplicateEmailException;
import uph.ii.SIMS.UserModule.UserDuplicateLoginException;
import uph.ii.SIMS.UserModule.UserExistInGroupException;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String handleAccessDenied(AccessDeniedException e)
    {
        return e.getMessage();
    }

    @ExceptionHandler(AnkietaDuplicateException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String handleDuplicateSurvey(AnkietaDuplicateException e)
    {
        return e.getMessage();
    }

    @ExceptionHandler(UserExistInGroupException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String handleUserExistInGroup(UserExistInGroupException e)
    {
        return  e.getMessage();
    }

    @ExceptionHandler(CantModifyAcceptedDocumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleCantModifyAccepted(CantModifyAcceptedDocumentException e)
    {
        return e.getMessage();
    }

    @ExceptionHandler(UserDuplicateLoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserDuplicateLogin(UserDuplicateLoginException e)
    {
        String a = "{\"message\":\""+ e.getMessage()+ "\"}";
       // String a = e.getMessage();

        return a;
    }
    @ExceptionHandler(UserDuplicateEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserDuplicateEmail(UserDuplicateEmailException e)
    {
        String a = "{\"message\":\""+ e.getMessage()+ "\"}";
        //return e.getMessage();
        return a;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUsernameNotFound(UsernameNotFoundException e)
    {
        return e.getMessage();
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class, IllegalAccessException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleConfilict(RuntimeException e, WebRequest request)
    {
        String bodyOfResponse = "Illigal args, state or access";
        return bodyOfResponse;

    }

    @ExceptionHandler(value = {NullPointerException.class, NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String handleNotFound(RuntimeException e, WebRequest request)
    {
        String bodyOfResponse = "Null Pointer or Not Found";
        return bodyOfResponse;
    }

    @ExceptionHandler(Error.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleError(Error e, WebRequest request)
    {
        String bodyOfResponse = "Error";
        return bodyOfResponse;
    }
}
