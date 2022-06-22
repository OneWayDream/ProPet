package ru.itis.pdfgenerator.exceptions.token;

import ru.itis.pdfgenerator.exceptions.auth.AuthorizationException;

public class IncorrectJwtException extends AuthorizationException {
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
