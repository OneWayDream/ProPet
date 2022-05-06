package ru.itis.backend.exceptions;

public abstract class UserAuthorizationException extends RuntimeException{

    public UserAuthorizationException() {
        super();
    }

    public UserAuthorizationException(String message) {
        super(message);
    }

    public UserAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAuthorizationException(Throwable cause) {
        super(cause);
    }
}
