package ru.itis.backend.exceptions.token;

public class IncorrectJwtException extends TokenAuthenticationException {

    public IncorrectJwtException() {
    }

    public IncorrectJwtException(String message) {
        super(message);
    }

    public IncorrectJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectJwtException(Throwable cause) {
        super(cause);
    }

}
