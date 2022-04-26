package ru.itis.backend.exceptions;

public abstract class JwtServerException extends RuntimeException{

    public JwtServerException() {
        super();
    }

    public JwtServerException(String message) {
        super(message);
    }

    public JwtServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtServerException(Throwable cause) {
        super(cause);
    }
}
