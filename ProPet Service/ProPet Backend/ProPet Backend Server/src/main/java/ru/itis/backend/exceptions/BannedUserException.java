package ru.itis.backend.exceptions;

public class BannedUserException extends UserAuthorizationException {

    public BannedUserException() {
    }

    public BannedUserException(String message) {
        super(message);
    }

    public BannedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public BannedUserException(Throwable cause) {
        super(cause);
    }

}
