package ru.itis.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAuthorizationException.class)
    public ResponseEntity<?> handleUserAuthorizationException(UserAuthorizationException exception){
        if (exception instanceof BannedUserException){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
        }
        else if (exception instanceof NotActivatedUserException){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
        }
        else if (exception instanceof IncorrectPasswordException){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
        }
        else {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<?> handleRegistrationException(RegistrationException exception){
        if (exception instanceof DifferentPasswordsException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        else if (exception instanceof LoginAlreadyInUseException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This login is already in use");
        }
        else if (exception instanceof MailAlreadyInUseException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This mail is already in use");
        }
        else {
            return ResponseEntity.badRequest().body("Something went wrong");
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
        }
        if (exception instanceof LinkNotExistsException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This link is not exists");
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
