package ru.itis.jwtserver.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itis.jwtserver.exceptions.*;

import javax.persistence.PersistenceException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<?> handleAuthorizationException(AuthorizationException exception){
        if (exception instanceof IncorrectUserDataException){
            return ResponseEntity.status(HttpErrorStatus.INCORRECT_USER_DATA.value())
                    .body("Incorrect user data.");
        }
        else if (exception instanceof BannedUserException){
            return ResponseEntity.status(HttpErrorStatus.BANNED_USER.value())
                    .body("You got banned!");
        }
        else if (exception instanceof IncorrectJwtException){
            return ResponseEntity.status(HttpErrorStatus.INCORRECT_TOKEN.value())
                    .body("Incorrect JWT token.");
        }
        else if (exception instanceof BannedTokenException){
            return ResponseEntity.status(HttpErrorStatus.BANNED_TOKEN.value())
                    .body("This token is banned :c");
        }
        else if (exception instanceof ExpiredJwtException){
            return ResponseEntity.status(HttpErrorStatus.EXPIRED_TOKEN.value())
                    .body("Your token is expired");
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Something went wrong");
        }
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<?> handlePersistenceException(PersistenceException exception){
        if (exception instanceof EntityNotExistsException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    (exception.getMessage() == null) ? "This entity is not exists" : exception.getMessage()
            );
        }
        if (exception instanceof EntityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    (exception.getMessage() == null) ? "This entity is not found" : exception.getMessage()
            );
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Something went wrong");
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception){
        return ResponseEntity.status(HttpErrorStatus.ACCESS_DENIED.value()).body("Access denied");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> defaultExceptionHandler(Exception exception){
        exception.printStackTrace();
        return new ResponseEntity<>("HEEEEEEH?!?!? \n " +
                "(There is no reason for this exception, so I'm real'y teapot)", HttpStatus.I_AM_A_TEAPOT);
    }

}
