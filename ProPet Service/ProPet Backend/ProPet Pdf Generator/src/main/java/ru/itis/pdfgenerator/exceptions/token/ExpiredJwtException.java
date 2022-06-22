package ru.itis.pdfgenerator.exceptions.token;

import ru.itis.pdfgenerator.exceptions.auth.AuthorizationException;

public class ExpiredJwtException extends AuthorizationException {
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
