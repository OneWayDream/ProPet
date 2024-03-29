package ru.itis.pdfgenerator.exceptions.auth;

public abstract class AuthorizationException extends RuntimeException {

    public AuthorizationException() {}

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException(Throwable cause) {
        super(cause);
    }
}
