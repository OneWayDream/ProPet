package ru.itis.imagesserver.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itis.imagesserver.exceptions.image.ImageException;
import ru.itis.imagesserver.exceptions.image.ImageLoadException;
import ru.itis.imagesserver.exceptions.image.ImageStoreException;
import ru.itis.imagesserver.exceptions.image.IncorrectImageTypeException;
import ru.itis.imagesserver.exceptions.token.ExpiredJwtException;
import ru.itis.imagesserver.exceptions.token.IncorrectJwtException;
import ru.itis.imagesserver.exceptions.token.TokenAuthenticationException;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception){
        log.warn(getStackTraceString(exception));
        return ResponseEntity.status(HttpErrorStatus.ACCESS_DENIED.value()).body("Access denied");
    }

    @ExceptionHandler(ImageException.class)
    public ResponseEntity<?> handleImageException(ImageException exception){
        log.error(getStackTraceString(exception));
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
