package ru.itis.backend.exceptions.handlers;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itis.backend.exceptions.pdf.*;
import ru.itis.backend.exceptions.comment.CommentAboutSitterException;
import ru.itis.backend.exceptions.comment.ExistedCommentException;
import ru.itis.backend.exceptions.comment.SelfCommentException;
import ru.itis.backend.exceptions.jwtserver.JwtAuthorizationFaultException;
import ru.itis.backend.exceptions.jwtserver.JwtServerException;
import ru.itis.backend.exceptions.persistence.*;
import ru.itis.backend.exceptions.registration.LoginAlreadyInUseException;
import ru.itis.backend.exceptions.registration.MailAlreadyInUseException;
import ru.itis.backend.exceptions.registration.RegistrationException;
import ru.itis.backend.exceptions.search.IncorrectOrderException;
import ru.itis.backend.exceptions.search.IncorrectSearchVariableException;
import ru.itis.backend.exceptions.search.IncorrectSortingVariableException;
import ru.itis.backend.exceptions.search.SearchException;
import ru.itis.backend.exceptions.token.ExpiredJwtException;
import ru.itis.backend.exceptions.token.IncorrectJwtException;
import ru.itis.backend.exceptions.token.TokenAuthenticationException;

import javax.persistence.PersistenceException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<?> handleRegistrationException(RegistrationException exception){
        if (exception instanceof LoginAlreadyInUseException){
            return ResponseEntity.status(HttpErrorStatus.EXISTING_LOGIN.value()).body("This login is already in use");
        }
        else if (exception instanceof MailAlreadyInUseException){
            return ResponseEntity.status(HttpErrorStatus.EXISTING_MAIL.value()).body("This mail is already in use");
        }
        else {
            log.error(getStackTraceString(exception));
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<?> handlePersistenceException(PersistenceException exception){
        if (exception instanceof EntityNotExistsException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This entity is not exists: "
                    +  ((exception.getMessage() == null) ? "" : exception.getMessage()));
        }
        if (exception instanceof EntityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This entity is not found: "
                    +  ((exception.getMessage() == null) ? "" : exception.getMessage()));
        }
        if (exception instanceof LinkNotExistsException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This link is not exists");
        }
        if (exception instanceof SitterInfoAlreadyExistsException){
            return ResponseEntity.status(HttpErrorStatus.ALREADY_CREATED_SITTER_INFO.value())
                    .body("This account already has a sitter info card");
        }
        if (exception instanceof TreatyInfoAlreadyExistsException){
            return ResponseEntity.status(HttpErrorStatus.ALREADY_CREATED_TREATY_INFO.value())
                    .body("This account already has a treaty info");
        }
        else {
            log.error(getStackTraceString(exception));
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
            log.error(getStackTraceString(exception));
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @ExceptionHandler(JwtAuthorizationFaultException.class)
    public ResponseEntity<?> handleJwtAuthorizationFaultException(JwtAuthorizationFaultException exception){
        log.error(getStackTraceString(exception));
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong.");
    }

    @ExceptionHandler(JwtServerException.class)
    public ResponseEntity<?> handleJwtServerException(JwtServerException exception){
        log.error(getStackTraceString(exception));
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception){
        log.warn(getStackTraceString(exception));
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


    @ExceptionHandler(CommentAboutSitterException.class)
    public ResponseEntity<?> handleCommentAboutSitterException(CommentAboutSitterException exception){
        if (exception instanceof SelfCommentException){
            return ResponseEntity.status(HttpErrorStatus.SELF_COMMENT.value())
                    .body("You can't add comment to your own sitter info!");
        }
        else if (exception instanceof ExistedCommentException){
            return ResponseEntity.status(HttpErrorStatus.EXISTED_COMMENT.value())
                    .body("You've already commented this sitter");
        }
        else {
            log.error(getStackTraceString(exception));
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @ExceptionHandler(PdfGenerationException.class)
    public ResponseEntity<?> handlePdfGenerationException(PdfGenerationException exception){
        if (exception instanceof UnconfirmedApplyException){
            return ResponseEntity.status(HttpErrorStatus.UNCONFIRMED_APPLY.value())
                    .body("Not all parties have yet confirmed the agreement");
        }
        else if (exception instanceof NoTreatyInfoException){
            return ResponseEntity.status(HttpErrorStatus.NO_TREATY_INFO.value())
                    .body("You haven't personal data here to confirm any treaties :c");
        }
        else if (exception instanceof EmptyProfileFieldException){
            return ResponseEntity.status(HttpErrorStatus.EMPTY_PROFILE_FIELD.value())
                    .body("You have to fill some profile fields before: " + exception.getMessage());
        }
        else if (exception instanceof SelfTreatyException){
            return ResponseEntity.status(HttpErrorStatus.SELF_TREATY.value())
                    .body("You can't make a pact with yourself -_-");
        }
        log.error(getStackTraceString(exception));
        return ResponseEntity.status(HttpErrorStatus.PDF_GENERATION_FAULT.value()).body("Something went wrong");
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
        log.error(getStackTraceString(exception));
        return new ResponseEntity<>("HEEEEEEH?!?!? \n " +
                "(There is no reason for this exception, so I'm real'y teapot)", HttpStatus.I_AM_A_TEAPOT);
    }

    protected String getStackTraceString(Exception exception){
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        try{
            sw.close();
        } catch (Exception ex){
            //ignore
        }
        return exceptionAsString;
    }

}
