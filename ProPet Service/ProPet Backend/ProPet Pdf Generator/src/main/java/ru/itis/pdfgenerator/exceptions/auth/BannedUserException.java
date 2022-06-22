package ru.itis.pdfgenerator.exceptions.auth;

public class BannedUserException extends AuthorizationException {

    public BannedUserException() {}

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
