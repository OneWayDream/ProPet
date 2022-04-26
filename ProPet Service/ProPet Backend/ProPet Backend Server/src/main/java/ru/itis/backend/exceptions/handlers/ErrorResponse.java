package ru.itis.backend.exceptions.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import ru.itis.backend.exceptions.ExpiredJwtException;
import ru.itis.backend.exceptions.IncorrectJwtException;

@Getter
@Setter
public class ErrorResponse {

    protected String message;
    protected HttpStatus status;

    public ErrorResponse(Exception ex){
        this.message = ex.getMessage();
        if ((ex instanceof IncorrectJwtException) || (ex instanceof ExpiredJwtException)){
            status = HttpStatus.FORBIDDEN;
        } else {
            status = HttpStatus.I_AM_A_TEAPOT;
        }
    }

}
