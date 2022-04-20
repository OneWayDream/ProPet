package ru.itis.jwtserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<?> handleAuthorizationException(AuthorizationException exception){
        if (exception instanceof IncorrectUserDataException){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Incorrect user data.");
        }
        else if (exception instanceof BannedUserException){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("You got banned!");
        }
        else if (exception instanceof IncorrectJwtException){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Incorrect JWT token.");
        }
        else if (exception instanceof BannedTokenException){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("This token is banned :c");
        } else if (exception instanceof ExpiredJwtException){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Your token is expired");
        }
        else {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> defaultExceptionHandler(Exception exception){
        exception.printStackTrace();
        return new ResponseEntity<>("HEEEEEEH?!?!? \n " +
                "(There is no reason for this exception, so I'm real'y teapot)", HttpStatus.I_AM_A_TEAPOT);
    }

}
