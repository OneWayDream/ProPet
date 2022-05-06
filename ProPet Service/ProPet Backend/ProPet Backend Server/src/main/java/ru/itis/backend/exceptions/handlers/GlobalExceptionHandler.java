package ru.itis.backend.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itis.backend.exceptions.*;

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

    @ExceptionHandler(TokenAuthenticationException.class)
    public ResponseEntity<?> handleTokenAuthenticationException(TokenAuthenticationException exception){
        if (exception instanceof ExpiredJwtException){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    (exception.getMessage() == null) ? "The token is expired." : exception.getMessage()
            );
        }
        if (exception instanceof IncorrectJwtException){
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    (exception.getMessage() == null) ? "This token is not supported" : exception.getMessage()
            );
        }
        else {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @ExceptionHandler(JwtAuthorizationFaultException.class)
    public ResponseEntity<?> handleJwtAuthorizationFaultException(JwtAuthorizationFaultException exception){
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong.");
    }

    @ExceptionHandler(JwtServerException.class)
    public ResponseEntity<?> handleJwtServerException(JwtServerException exception){
        exception.printStackTrace();
        if (exception instanceof JwtAuthorizationFaultException){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong.");
        }
        else if (exception instanceof JwtRegistrationException){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong.");
        }
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> defaultExceptionHandler(Exception exception){
        exception.printStackTrace();
        return new ResponseEntity<>("HEEEEEEH?!?!? \n " +
                "(There is no reason for this exception, so I'm real'y teapot)", HttpStatus.I_AM_A_TEAPOT);
    }


}
