package ru.itis.backend.exceptions;

public class NotActivatedUserException extends UserAuthorizationException {
    public NotActivatedUserException() {
    }

    public NotActivatedUserException(String message) {
        super(message);
    }

    public NotActivatedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotActivatedUserException(Throwable cause) {
        super(cause);
    }
}
