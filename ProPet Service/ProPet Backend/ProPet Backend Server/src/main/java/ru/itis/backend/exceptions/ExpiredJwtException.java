package ru.itis.backend.exceptions;

public class ExpiredJwtException extends TokenAuthenticationException {
    public ExpiredJwtException() {
    }

    public ExpiredJwtException(String message) {
        super(message);
    }

    public ExpiredJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredJwtException(Throwable cause) {
        super(cause);
    }
}
