package ru.itis.jwtserver.exceptions.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import ru.itis.jwtserver.exceptions.token.BannedTokenException;
import ru.itis.jwtserver.exceptions.token.ExpiredJwtException;
import ru.itis.jwtserver.exceptions.token.IncorrectJwtException;

@Getter
@Setter
public class ErrorResponse {

    protected String message;
    protected int status;

    public ErrorResponse(Exception ex){
        if (ex instanceof IncorrectJwtException){
            status = HttpErrorStatus.INCORRECT_TOKEN.value();
            message = (ex.getMessage() == null) ? "Incorrect JWT token" : ex.getMessage();
        } else if (ex instanceof ExpiredJwtException){
            status = HttpErrorStatus.EXPIRED_TOKEN.value();
            message = (ex.getMessage() == null) ? "Your token is expired" : ex.getMessage();
        } else if (ex instanceof BannedTokenException){
            status = HttpErrorStatus.BANNED_TOKEN.value();
            message = (ex.getMessage() == null) ? "This token is banned :c" : ex.getMessage();
        } else {
            status = HttpStatus.I_AM_A_TEAPOT.value();
        }
    }

}
