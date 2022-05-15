package ru.itis.backend.exceptions.handlers;

import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itis.backend.exceptions.*;

import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<?> handleRegistrationException(RegistrationException exception){
        if (exception instanceof LoginAlreadyInUseException){
            return ResponseEntity.status(HttpErrorStatus.EXISTED_LOGIN.value()).body("This login is already in use");
        }
        else if (exception instanceof MailAlreadyInUseException){
            return ResponseEntity.status(HttpErrorStatus.EXISTED_MAIL.value()).body("This mail is already in use");
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
        if (exception instanceof SitterInfoAlreadyExistsException){
            return ResponseEntity.status(HttpErrorStatus.ALREADY_CREATED_SITTER_INFO.value())
                    .body("This account already has a sitter info card");
        }
        else {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @ExceptionHandler(TokenAuthenticationException.class)
    public ResponseEntity<?> handleTokenAuthenticationException(TokenAuthenticationException exception){
        if (exception instanceof ExpiredJwtException){
            return ResponseEntity.status(HttpErrorStatus.EXPIRED_TOKEN.value()).body(
                    (exception.getMessage() == null) ? "The token is expired." : exception.getMessage()
            );
        }
        if (exception instanceof IncorrectJwtException){
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpErrorStatus.INCORRECT_TOKEN.value()).body(
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
        } else if (exception instanceof JwtUpdateException){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong.");
        }
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception){
        return ResponseEntity.status(HttpErrorStatus.ACCESS_DENIED.value()).body("Access denied");
    }

    @ExceptionHandler(SearchException.class)
    public ResponseEntity<?> handleSearchException(SearchException exception){
        if (exception instanceof IncorrectOrderException){
            return ResponseEntity.status(HttpErrorStatus.INCORRECT_SEARCH_ORDER.value()).build();
        }
        else if (exception instanceof IncorrectSortingVariableException){
            return ResponseEntity.status(HttpErrorStatus.INCORRECT_SORTING_VARIABLE.value()).build();
        }
        else if (exception instanceof IncorrectSearchVariableException){
            return ResponseEntity.status(HttpErrorStatus.INCORRECT_SEARCH_VARIABLE.value()).build();
        }
        else {
            return ResponseEntity.status(HttpErrorStatus.INCORRECT_SEARCH_SETTINGS.value()).build();
        }
    }

    @ExceptionHandler(ImageException.class)
    public ResponseEntity<?> handleImageException(ImageException exception){
        exception.printStackTrace();
        if (exception instanceof ImageLoadException){
            return ResponseEntity.status(HttpErrorStatus.IMAGE_LOAD_ERROR.value())
                    .body("Can't load an image");
        }
        else if (exception instanceof ImageStoreException){
            return ResponseEntity.status(HttpErrorStatus.IMAGE_STORE_ERROR.value())
                    .body("Can't save an image");
        }
        else if (exception instanceof IncorrectImageTypeException){
            return ResponseEntity.status(HttpErrorStatus.INCORRECT_IMAGE_TYPE.value())
                    .body("Incorrect image type");
        }
        else {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                           @NonNull HttpHeaders headers,
                                                                           @NonNull HttpStatus status,
                                                                           @NonNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (Objects.requireNonNull(error.getCodes())[0].equals("ValidPasswords.registrationForm")){
                errors.put("Passwords", error.getDefaultMessage());
            } else {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            }
        });
        return new ResponseEntity<>(errors, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> defaultExceptionHandler(Exception exception){
        exception.printStackTrace();
        return new ResponseEntity<>("HEEEEEEH?!?!? \n " +
                "(There is no reason for this exception, so I'm real'y teapot)", HttpStatus.I_AM_A_TEAPOT);
    }


}
